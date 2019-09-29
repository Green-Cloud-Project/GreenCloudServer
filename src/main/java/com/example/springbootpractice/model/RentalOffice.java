package com.example.springbootpractice.model;

public class RentalOffice {
    String office_id;
    String office_name;
    String office_location;
    String lat;
    String lon;
    String umbrella_count;

    public String getOffice_id() {
        return office_id;
    }

    public void setOffice_id(String office_id) {
        this.office_id = office_id;
    }

    public String getOffice_name() {
        return office_name;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public String getOffice_location() {
        return office_location;
    }

    public void setOffice_location(String office_location) {
        this.office_location = office_location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getUmbrella_count() {
        return umbrella_count;
    }

    public void setUmbrella_count(String umbrella_count) {
        this.umbrella_count = umbrella_count;
    }
}
