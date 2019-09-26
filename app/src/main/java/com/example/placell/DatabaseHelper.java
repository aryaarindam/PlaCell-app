package com.example.placell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "placell.dat";

    public DatabaseHelper(Context context) { super(context,DATABASE_NAME,null,1); SQLiteDatabase db = this.getWritableDatabase(); }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Profile(Pid integer primary key autoincrement, Name text not null, Password text not null, Year integer not null, Email text not null, Imageid integer not null, Company text, Story text, Package text, Isemp integer);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Newsfeed(Id integer primary key autoincrement, Heading text not null, JobTitle text, Subject text, Description text, Date text not null, Type text not null, Year text);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Ongoing(Id integer primary key autoincrement, Imageid integer not null, Company text, Cutoff text, Description text);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Previous(Id integer primary key autoincrement, Company text, Cutoff text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists Profile");
        sqLiteDatabase.execSQL("Drop table if exists Newsfeed");
        sqLiteDatabase.execSQL("Drop table if exists Ongoin");
        sqLiteDatabase.execSQL("Drop table if exists Previous");
        onCreate(sqLiteDatabase);
    }

    public boolean insertProfile(String name, String password, int year, String email, int imageId, String company, String story, String pack, int isemp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Password", password);
        contentValues.put("Year", year);
        contentValues.put("Email", email);
        contentValues.put("Imageid", imageId);
        contentValues.put("Company", company);
        contentValues.put("Story", story);
        contentValues.put("Package", pack);
        contentValues.put("Isemp", isemp);
        long result = db.insert("Profile", null, contentValues);
        return !(result == -1);
    }

    public boolean insertNewsfeed(String heading, String jobTitle, String subject, String description, String date, String type, String year) {
        SQLiteDatabase db = this.getWritableDatabase();;
        ContentValues contentValues = new ContentValues();
        contentValues.put("Heading", heading);
        contentValues.put("JobTitle", jobTitle);
        contentValues.put("Subject", subject);
        contentValues.put("Description", description);
        contentValues.put("Date", date);
        contentValues.put("Type", type);
        contentValues.put("Year", year);
        long result = db.insert("Newsfeed", null, contentValues);
        return !(result == -1);
    }

    public boolean insertOngoing(int imageId, String company, String cutoff, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Imageid", imageId);
        contentValues.put("Company", company);
        contentValues.put("Cutoff", cutoff);
        contentValues.put("Description", description);
        long result = db.insert("Ongoing", null, contentValues);
        return !(result == -1);
    }

    public boolean insertPrevious(String company, String cutoff) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Company", company);
        contentValues.put("Cutoff", cutoff);
        long result = db.insert("Previous", null, contentValues);
        return !(result == -1);
    }

    public void updateProfile(String email, String name, String password, int imageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update Profile Set Name = '"+name+"', Password = '"+password+"', Imageid = "+imageId+" where Email = '"+email+"'");
    }

    public void updateStory(String email, String company, String story, String pack){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update Profile Set Company='"+company+"', Story='"+story+"', Package='"+pack+"', Isemp=1 where Email = '"+email+"';");
    }

    public String checkUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        String users = "";
        Cursor c = db.rawQuery("Select Email from Profile", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            users += c.getString(c.getColumnIndex("Email"));
            c.moveToNext();
        }
        c.close();
        return users;
    }

    public String getName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select Name from Profile where Email = '"+email+"';", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("Name"));
    }

    public int getImagid(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select Imageid from Profile where Email = '"+email+"';", null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("Imageid"));
    }

    public String checkPass(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select Password from Profile where Email = '"+email+"';", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("Password"));
    }

    public Cursor viewProfile() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select Name, Year, Email, Imageid, Company, Story, Package, Pid from Profile where Isemp = 1;", null);
    }

    public Cursor viewProfile(int pid) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select Name, Year, Email, Imageid, Company, Story, Package from Profile where Pid = " + pid + ";", null);
    }

    public Cursor viewNewsfeed() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select Heading, JobTitle, Subject, Date, Type, Year, Description, Id from Newsfeed", null);
    }

    public Cursor viewNewsfeed(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select Heading, JobTitle, Subject, Date, Type, Year, Description, Id from Newsfeed where Id = " + id + ";", null);
    }

    public Cursor viewOngoing() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select Imageid, Company, Cutoff, Description, Id from Ongoing", null);
    }

    public Cursor viewOngoing(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select Imageid, Company, Cutoff, Description from Ongoing where Id = " + id + ";", null);
    }

    public Cursor viewPrevious() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select Company, Cutoff, Id from Previous", null);
    }

    public void deleteNewsfeed(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from Newsfeed where Id = " + id);
    }

    public void deleteOngoing(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from Ongoing where Id = " + id);
    }

    public void deletePrevious(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from Previous where Id = " + id);
    }

    public void deleteStory(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update Profile Set Company=null, Story=null, Package=null, Isemp=0 where Email = '"+email+"';");
    }
}
