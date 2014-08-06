package com.LocallyGrownStudios.liquidcommunications.Helpers;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

public class LiquidContactsBean {

    private String name;
    private String phoneNo;
    private String lastText;
    private Uri contactID;
    private Bitmap contactPhoto;
    private Drawable defaultPhoto;

    public Uri IDGet(){ return contactID;}

    public void IDSet(Uri contactID){ this.contactID = contactID; }

    public String Nameget() {
        return name;
    }

    public void Nameset(String name) {
        this.name = name;
    }

    public Bitmap ConctactPhotoGet() {
        return contactPhoto;
    }

    public void ContactPhotoSet(Bitmap contactPhoto) {
        this.contactPhoto = contactPhoto;
    }

    public String PhoneNoget() {
        return phoneNo;
    }

    public void PhoneNoset(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Drawable DefaultPhotoGet() {
        return defaultPhoto;
    }

    public void DefaultPhotoSet(Drawable defaultPhoto) {
        this.defaultPhoto = defaultPhoto;
    }

    public String LastTextGet() {
        return lastText;
    }

    public void LastTextSet(String lastText) {
        this.lastText = lastText;
    }

}




