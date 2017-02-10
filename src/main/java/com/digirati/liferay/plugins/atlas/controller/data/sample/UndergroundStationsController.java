package com.digirati.liferay.plugins.atlas.controller.data.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digirati.liferay.plugins.atlas.domain.Icon;
import com.digirati.liferay.plugins.atlas.domain.Marker;

@Controller
public class UndergroundStationsController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/data/tflundergroundstations")
    public List<Marker> getUndergroundStations() throws IOException {

        Icon icon = new Icon("/atlas-portlet/images/subway.png", 16, 34);

        List<Marker> mapMarkers = new ArrayList<Marker>();
        InputStream inputStream = getClass().getResourceAsStream("/data/tfl-underground-stations.csv");
        for (CSVRecord record : CSVFormat.DEFAULT.parse(new InputStreamReader(inputStream))) {
            mapMarkers.add(new Marker(record.get(0), icon, Double.valueOf(record.get(1)), Double.valueOf(record.get(2))));
        }
        return mapMarkers;
    }
}
