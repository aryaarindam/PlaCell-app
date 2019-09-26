package com.example.placell;

import java.util.Date;

public class Newsfeeds {
    String heading, jobTitle, desc, story, type, year, date;
    int id;



    public Newsfeeds(String heading, String jobTitle, String story, String date, String type, String year, String desc, int id) {
        this.heading = heading;
        this.jobTitle = jobTitle;
        this.desc = desc;
        this.story = story;
        this.date = date;
        this.type = type;
        this.year = year;
        this.id = id;
    }

    public int getId() { return id; }

    public String getHeading() { return heading; }

    public String getJobTitle() { return jobTitle; }

    public String getStory() { return story; }

    public String getDate() { return date; }

    public String getDesc() { return desc; }

    public String getType() { return type; }

    public String getYear() { return year; }
}
