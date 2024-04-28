package org.afetankanet.disastermanagementmicroservice.model;

import jakarta.xml.bind.annotation.XmlAttribute;


public class MediaContent {
    private String url;
    private String type;
    private int width;
    private int height;

    @XmlAttribute(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute(name = "width")
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @XmlAttribute(name = "height")
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
