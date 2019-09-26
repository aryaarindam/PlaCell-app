package com.example.placell;

public class Previouses {
    String company, avgPackage;
    int id;

    public Previouses(String company, String avgPackage,int id) {
        this.company = company;
        this.avgPackage = avgPackage;
        this.id = id;
    }

    public int getId() { return id; }

    public String getCompany() { return company; }

    public String getAvgPackage() { return avgPackage; }
}
