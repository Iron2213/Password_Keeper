package com.example.passwordkeeper;

public class Items {
    private long mID;
    private String mInsertionDate;
    private String mSite;
    private String mEmail;
    private String mPassword;

    public Items(long id, String date, String site, String email, String password) {
        this.mID = id;
        this.mInsertionDate = date;
        this.mSite = site;
        this.mEmail = email;
        this.mPassword = password;
    }

    public Items(String date, String site, String email, String password) {
        this.mInsertionDate = date;
        this.mSite = site;
        this.mEmail = email;
        this.mPassword = password;
    }

    public void setID(long id) {
        this.mID = id;
    }

    public void setInsertionDate(String date) {
        this.mInsertionDate = date;
    }

    public void setSite(String site) {
        this.mSite = site;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public long getID() {
        return this.mID;
    }

    public String getInsertionDate() {
        return this.mInsertionDate;
    }

    public String getSite() {
        return this.mSite;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public String getPassword() {
        return this.mPassword;
    }
}