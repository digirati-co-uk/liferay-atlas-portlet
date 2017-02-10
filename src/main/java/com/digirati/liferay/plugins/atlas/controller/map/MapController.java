package com.digirati.liferay.plugins.atlas.controller.map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class MapController {

    @RenderMapping
    public String map() {
        return "map";
    }
}
