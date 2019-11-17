package com.example.springbootpractice.model;

public class Stock {
    int stock_id;
    String status;
    int office_id;
    String qr_code;
    String umbrella_id;

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOffice_id() {
        return office_id;
    }

    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getUmbrella_id() {
        return umbrella_id;
    }

    public void setUmbrella_id(String umbrella_id) {
        this.umbrella_id = umbrella_id;
    }
}
