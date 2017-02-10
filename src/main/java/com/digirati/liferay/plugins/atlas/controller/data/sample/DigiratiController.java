package com.digirati.liferay.plugins.atlas.controller.data.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digirati.liferay.plugins.atlas.domain.Icon;
import com.digirati.liferay.plugins.atlas.domain.Marker;

@Controller
public class DigiratiController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/data/digirati")
    public List<Marker> getUndergroundStations() throws IOException {

        Icon icon = new Icon("/atlas-portlet/images/office-building.png", 16, 34);

        List<Marker> mapMarkers = new ArrayList<Marker>();
        mapMarkers.add(new Marker("<strong>Digirati - Glasgow</strong><br />70 Pacific Quay<br />Glasgow<br />G51 1DZ<br />T: 0845 643 4370<br />F: 0870 1207722<br /><a href=\"mailto:contact@digirati.com\">contact@digirati.com</a>", icon, 55.856162, -4.295647));
        mapMarkers.add(new Marker("<strong>Digirati - London</strong><br />Dunstan House<br />14A St Cross St<br />London<br />EC1N 8XA<br />T: 0845 643 4370<br />F: 0870 1207722<br /><a href=\"mailto:contact@digirati.com\">contact@digirati.com</a>", icon, 51.520956, -0.107365));
        return mapMarkers;
    }
}
