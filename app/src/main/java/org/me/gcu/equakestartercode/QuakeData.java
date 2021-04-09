package org.me.gcu.equakestartercode;
//Aaron Amjid S1626987
import androidx.annotation.NonNull;

import java.util.Date;

public class QuakeData {
    /*
    QuakeData Class
    This is a model class for use in parsing the XML data.
     */

    private String title;
    private String description;
    private String link;
    private Date pubdate;
    private double geolat;
    private double geolong;
    private String category;
    private double magnitude;

    public QuakeData(){ //Default Quakedata object constructor with default values.
        this.title = "Default";
        this.description = "Default";
        this.link = "Default";
        this.pubdate = new Date();
        this.geolat = 0.0;
        this.geolong = 0.0;
        this.category="EQUK";
        this.magnitude= 0.0;
    }

    public QuakeData(String title, String description, String link, Date pubdate, double geolat, double geolong, String category, double magnitude){
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubdate = pubdate;
        this.geolat = geolat;
        this.geolong = geolong;
        this.category = category;
        this.magnitude = magnitude;
    }

 //Getters, Setters and toString method
    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLink() {
        return this.link;
    }

    public Date getPubDate() {
        return this.pubdate;
    }
    public double getGeoLat(){
        return this.geolat;
    }
    public double getMagnitude(){
        return this.magnitude;
    }
    public double getGeoLong(){
        return this.geolong;
    }
    public String getCategory() {
        return this.category;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String desc){
        this.description = desc;
    }
    public void setLink(String link){
        this.link = link;
    }
    public void setPubDate(Date date){
        this.pubdate = date;
    }
    public void setGeoLat(double geolat){
        this.geolat = geolat;
    }
    public void setMagnitude(double mag){
        this.magnitude = mag;
    }
    public void setGeoLong(double geolong){
        this.geolong = geolong;
    }
    public void setCategory(String category){
        this.category = category;
    }

    @NonNull
    @Override
    public String toString() {
       String s = this.getTitle() + ", Magnitude " + this.getMagnitude() + ", Geo Lat " + this.getGeoLat() + ", Geo Long " + this.getGeoLong() + "," +this.getPubDate() + ", Link" + this.getLink();
        return s;
    }
}


