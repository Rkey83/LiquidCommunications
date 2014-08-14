package com.LocallyGrownStudios.liquidcommunications.Helpers;

import android.graphics.Bitmap;


public class SmsMmsSentBean {

    private String smsMessage, smsDate, smsNumber;
    private int ID, smsThreadID, smsType;
    Bitmap smsContactPhoto, mmsImage;


    public String getSmsNumber() { return smsNumber; }

    public void setSmsNumber(String smsNumber) { this.smsNumber = smsNumber;}

    public Bitmap getSmsContactPhoto() {
        return smsContactPhoto;
    }

    public void setSmsContactPhoto(Bitmap smsContactPhoto) { this.smsContactPhoto = smsContactPhoto;}

    public Bitmap getMmsImage() {
        return mmsImage;
    }

    public void setMmsImage(Bitmap mmsImage) { this.mmsImage = mmsImage;}

    public String getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(String smsMessage) { this.smsMessage = smsMessage;}

    public String getSmsDate() { return smsDate; }

    public void setSmsDate(String smsDate) {this.smsDate = smsDate;}

    public int getID() { return ID;}

    public void setID(int ID) {this.ID = ID;}

    public int getSmsThreadID() {return smsThreadID;}

    public void setSmsThreadID(int smsThreadID) {this.smsThreadID = smsThreadID;}

    public int setSmsType(){return smsType;}

    public void getSmsType(int smsType) {this.smsType = smsType;}
}


