package com.elmexicano.lsteamer.xlocation;

import java.io.Serializable;

/**
 * Created by lsteamer on 30/05/2017.
 */

public class LocationData implements Serializable {
    /**
     * I might not use them all, but I included all I could see myself using
     */
    private String locationID, title, address, postalCode,
            category, phoneNumber, formattedPhoneNumber,
            twitter, instagram, facebook, icon;
    private String[] imagesSuffix;
    private int distance;
    private float latitude, longitude;


    public LocationData(String title, String address, int distance){
        this.locationID="";
        this.title=title;
        this.address=address;
        this.distance =distance;
        this.latitude=0;
        this.longitude=0;
        this.postalCode="";
        this.category="";
        this.phoneNumber="";
        this.formattedPhoneNumber="";
        this.twitter="";
        this.instagram="";
        this.facebook="";
        this.icon =null;
        this.imagesSuffix=null;
    }


    public LocationData(String locationID, String title, String address, int distance, float latitude, float longitude, String postalCode,
                        String category, String phoneNumber, String formattedPhoneNumber, String twitter,
                        String instagram, String facebook, String icon){
        this.locationID=locationID;
        this.title=title;
        this.address=address;
        this.distance =distance;
        this.latitude=latitude;
        this.longitude=longitude;
        this.postalCode=postalCode;
        this.category=category;
        this.phoneNumber=phoneNumber;
        this.formattedPhoneNumber=formattedPhoneNumber;
        this.twitter=twitter;
        this.instagram=instagram;
        this.facebook=facebook;
        this.icon =icon;
        this.imagesSuffix=null;
    }

    protected void setImagesSuffix(String[] imagesSuffix){
        this.imagesSuffix=imagesSuffix;
    }

    public String getLocationID(){
        return locationID;
    }
    public String getTitle(){
        return title;
    }
    public String getAddress(){
        return address;
    }
    public int getDistance(){
        return distance;
    }
    public float getLatitude(){
        return latitude;
    }
    public float getLongitude(){
        return longitude;
    }
    public String getPostalCode(){
        return postalCode;
    }
    public String getCategory(){
        return category;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getFormattedPhoneNumber(){
        return formattedPhoneNumber;
    }
    public String getTwitter(){
        return twitter;
    }
    public String getFacebook(){
        return facebook;
    }
    public String getInstagram(){
        return instagram;
    }
    public String[] getImagesSuffix(){
        return imagesSuffix;
    }

    public String getIcon(){
        return icon;
    }

}
