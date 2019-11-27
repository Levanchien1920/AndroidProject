package com.qhung.contact;

import android.media.Image;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.jar.Attributes;

public class Contact implements Serializable {
    String Name;
    String Phone;
    int Avatar;
    int Id;

    public Contact(){}

    public Contact(String name, String phone,int avatar,int id) {
        Name = name;
        Phone = phone;
        Avatar = avatar;
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getAvatar() {
        return Avatar;
    }

    public void setAvatar(int avatar) {
        Avatar = avatar;
    }
}
