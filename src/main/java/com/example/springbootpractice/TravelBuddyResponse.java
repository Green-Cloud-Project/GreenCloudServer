package com.example.springbootpractice;

public class TravelBuddyResponse<T extends Object> {
    int response;
    String error_msg;
    T data;
}
