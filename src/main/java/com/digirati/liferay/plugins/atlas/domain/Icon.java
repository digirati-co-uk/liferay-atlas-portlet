package com.digirati.liferay.plugins.atlas.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Icon implements Serializable {

    private String url;
    private int anchorX;
    private int anchorY;

    public Icon(String url, int anchorX, int anchorY) {
        this.url = url;
        this.anchorX = anchorX;
        this.anchorY = anchorY;
    }

    public String getUrl() {
        return url;
    }

    public int getAnchorX() {
        return anchorX;
    }

    public int getAnchorY() {
        return anchorY;
    }
}
