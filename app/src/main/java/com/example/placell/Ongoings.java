package com.example.placell;

import android.widget.ImageView;
import android.widget.TextView;

public class Ongoings {
    int id, img;
    String company, cgpa, desc;

    public Ongoings(int img, String company, String cgpa, String desc, int id) {
        this.img = img;
        this.company = company;
        this.cgpa = cgpa;
        this.desc = desc;
        this.id = id;
    }

    public int getId() { return id; }

    public String getDesc() { return desc; }

    public int getImg() { return img; }

    public String getCompany() { return company; }

    public String getCgpa() { return cgpa; }
}
