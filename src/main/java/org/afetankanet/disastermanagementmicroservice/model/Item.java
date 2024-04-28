package org.afetankanet.disastermanagementmicroservice.model;

import jakarta.xml.bind.annotation.XmlElement;

public class Item {
    private String title;
    private String link;
    private String guid;
    private String pubDate;
    private String description;
    private MediaContent mediaContent;

    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @XmlElement(name = "guid")
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @XmlElement(name = "pubDate")
    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "content", namespace = "http://search.yahoo.com/mrss/")
    public MediaContent getMediaContent() {
        return mediaContent;
    }

    public void setMediaContent(MediaContent mediaContent) {
        this.mediaContent = mediaContent;
    }
}
