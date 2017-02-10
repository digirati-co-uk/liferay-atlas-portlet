package com.digirati.liferay.plugins.atlas.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Marker implements Serializable {

    private String text;
    private Icon icon;
    private double lat;
    private double lon;

    public Marker(String text, Icon icon, double lat, double lon) {
        this.text = text;
        this.icon = icon;
        this.lat = lat;
        this.lon = lon;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
