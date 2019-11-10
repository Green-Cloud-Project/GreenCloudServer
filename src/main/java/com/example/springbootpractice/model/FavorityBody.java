package com.example.springbootpractice.model;

import com.example.springbootpractice.User;

import java.util.ArrayList;

public class FavorityBody {
    private ArrayList<RentalOffice> rentalOffices;

    public ArrayList<RentalOffice> getRentalOffices() {
        return rentalOffices;
    }

    public void setRentalOffices(ArrayList<RentalOffice> rentalOffices) {
        this.rentalOffices = rentalOffices;
    }
}
