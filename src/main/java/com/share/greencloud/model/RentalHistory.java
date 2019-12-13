package com.share.greencloud.model;

public class RentalHistory {
    int history_id;
    String rental_start_time;
    String rental_return_time;
    int office_id;
    String umbrella_id;
    int user_id;
    String rental_status;

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public String getRental_start_time() {
        return rental_start_time;
    }

    public void setRental_start_time(String rental_start_time) {
        this.rental_start_time = rental_start_time;
    }

    public String getRental_return_time() {
        return rental_return_time;
    }

    public void setRental_return_time(String rental_return_time) {
        this.rental_return_time = rental_return_time;
    }

    public int getOffice_id() {
        return office_id;
    }

    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    public String getUmbrella_id() {
        return umbrella_id;
    }

    public void setUmbrella_id(String umbrella_id) {
        this.umbrella_id = umbrella_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRental_status() {
        return rental_status;
    }

    public void setRental_status(String rental_status) {
        this.rental_status = rental_status;
    }
}
