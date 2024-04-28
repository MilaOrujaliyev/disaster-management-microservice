package org.afetankanet.disastermanagementmicroservice.model;

import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

public class Channel {
    private List<Item> items;

    @XmlElement(name = "item")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
