package org.afetankanet.disastermanagementmicroservice.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rss")
public class RssFeed {
    private Channel channel;

    @XmlElement(name = "channel")
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

