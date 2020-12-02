package com.example.ffbfapp.model;

import java.util.HashMap;

public class Address {
    private String street;
    private String city;
    private String county;
    private String postcode;
    private String contactNo;
    private String emailAddress;

    public Address() {
    }

    public Address(String street1, String city, String county, String postcode, String contactNo, String emailAddress) {
        this.street = street1;
        this.city = city;
        this.county = county;
        this.postcode = postcode;
        this.contactNo = contactNo;
        this.emailAddress = emailAddress;
    }


    // Create static methods for parsing an Address instance to a HashMap and the other way around
    public static HashMap<String,String> addressToHashMap(Address addressClass){
        HashMap<String,String> addressHashMap = new HashMap<String, String>();
        addressHashMap.put("street", addressClass.getStreet());
        addressHashMap.put("city", addressClass.getCity());
        addressHashMap.put("county", addressClass.getCounty());
        addressHashMap.put("postcode", addressClass.getPostcode());
        addressHashMap.put("contactNo", addressClass.getContactNo());
        addressHashMap.put("emailAddress", addressClass.getEmailAddress());
        return addressHashMap;
    }

    public static Address hashMapToAddress(HashMap<String,String> addressHashMap){
        Address address = new Address();

        address.setStreet(addressHashMap.get("street"));
        address.setCity(addressHashMap.get("city"));
        address.setCounty(addressHashMap.get("county"));
        address.setPostcode(addressHashMap.get("postcode"));
        address.setContactNo(addressHashMap.get("contactNo"));
        address.setEmailAddress(addressHashMap.get("emailAddress"));
        return address;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
