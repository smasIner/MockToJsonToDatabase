package org.muratShaikhutdinov;

import java.util.UUID;

/**
 * Represents a geographic location with various attributes.
 */
public class Location {

    private final String UID;
    private String ownerId;
    private  String city;
    private  String street;
    private  String houseNumber;
    private  String roomNumber;
    private double longitude;
    private double latitude;
    private final Long creationTime;

    public Location(String ownerId, String city, String street, String houseNumber, String roomNumber, Double longitude, Double latitude){
        this.UID = UUID.randomUUID().toString();
        this.ownerId = ownerId;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.roomNumber = roomNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.creationTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Location{" +
                "UID='" + UID + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", creationTime=" + creationTime +
                '}';
    }

    public String getUID() {
        return UID;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
