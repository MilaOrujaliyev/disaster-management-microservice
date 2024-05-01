package org.afetankanet.disastermanagementmicroservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "earthquakes", uniqueConstraints = {@UniqueConstraint(columnNames = {"eventID"})}) // Earthquake tablosu için unique constraint tanımı
public class Earthquake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double rms;
    @Column(unique = true)
    private String eventID;
    private String location;
    private double latitude;
    private double longitude;
    private double depth;
    private String type;
    private double magnitude;
    private String country;
    private String province;
    private String district;
    private String neighborhood;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    private boolean isEventUpdate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdateDate; // Null olabilir


    public Long getId() {
        return id;
    }

    public double getRms() {
        return rms;
    }

    public String getEventID() {
        return eventID;
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDepth() {
        return depth;
    }

    public String getType() {
        return type;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isEventUpdate() {
        return isEventUpdate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setRms(double rms) {
        this.rms = rms;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setEventUpdate(boolean isEventUpdate) {
        this.isEventUpdate = isEventUpdate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
