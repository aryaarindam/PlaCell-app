package com.example.placell;

public class Stories {
    private String Name;
    private String Email;
    private int Photo;
    private String Pack;
    private String Company;
    private String Story;
    private int Year;
    private int id;

    public Stories(String name, int year, String email, int photo, String pack, String company, String story, int id) {
        Name = name;
        Email = email;
        Photo = photo;
        Pack = pack;
        Company = company;
        Story = story;
        Year = year;
        this.id = id;
    }

    public String getName() { return Name; }

    public String getEmail() { return Email; }

    public int getPhoto() { return Photo; }

    public String getPack() { return Pack; }

    public String getCompany() { return Company; }

    public String getStory() { return Story; }

    public int getYear() { return Year; }

    public int getId() { return id; }
}
