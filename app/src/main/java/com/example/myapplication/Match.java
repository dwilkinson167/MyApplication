package com.example.myapplication;

public class Match {

    private String imageUrl;
    private String lat;
    private boolean liked;
    private String longitude;
    private String name;
    private String uid;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLat() {
        return lat;
    }

    public boolean getIsLiked() {
        return liked;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
