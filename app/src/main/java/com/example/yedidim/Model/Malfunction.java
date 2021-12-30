package com.example.yedidim.Model;


import android.provider.ContactsContract;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Malfunction {
    @PrimaryKey
    @NonNull
    private int malfunctionID;
    private String problem;
    private String notes;
    private User user=new User();
    //private String UserId;
    private ImageView image; // כשנוסיף העלאת תמונה אולי נשנה את הסוג
    private double longitude;
    private double latitude;

    public Malfunction(){
    }
    public Malfunction(int ID, String problem, String notes, User u, ImageView iv, double longitude, double latitude){
        this.malfunctionID= ID;
        this.problem=problem;
        this.notes=notes;
        this.user=u;
        this.image=iv;
        this.longitude=longitude;
        this.latitude=latitude;
    }
//     need to add set user or userId
//    public void setUserId(String userId) {this.userId = userId;}
    // need to add getters for all members

    //getters
    public void setMalfunctionID(int malfunctionID) {this.malfunctionID = malfunctionID;}
    public void setProblem(String p){
        this.problem = p;
    }
    public void setNotes(String n){
        this.notes = n;
    }
    public void setImage(ImageView iv){
        this.image = iv;
    }
    public void setLongitude(double lo) {this.longitude = lo;}
    public void setLatitude(double la) {this.latitude = la;}

    // setters
//    public String getUserId() {return userId;}   check!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1111111
    public String getProblem() {return problem;}
    public String getNotes() {return notes;}
    public int getMalfunctionID() {return malfunctionID;}
    public ImageView getImage() {return image;}
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
