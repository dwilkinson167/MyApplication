package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SettingsEntity {

    @PrimaryKey
    @NonNull
    private  String email = "";

    @ColumnInfo(name = "reminder_time")
    private String time;

    @ColumnInfo(name = "max_distance")
    private String maxDist;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "account_visibility")
    private String acct_vis;

    @ColumnInfo(name = "age_range")
    private String ageRange;


    @NonNull
    public String getEmail() {
        return email;
    }

    public String getTime() {
        return time;
    }

    public String getMaxDist() {
        return maxDist;
    }

    public String getGender() {
        return gender;
    }

    public String getAcct_vis() {
        return acct_vis;
    }

    public String getAgeRange() {
        return ageRange;
    }


    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMaxDist(String maxDist) {
        this.maxDist = maxDist;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAcct_vis(String acct_vis) {
        this.acct_vis = acct_vis;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }
}
