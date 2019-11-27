package com.qhung.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "Contact_Manger";
    public MyDatabase(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        String script = "Create table contact(id Interger Primary Key, name Text, phone Text)";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",contact.getName());
        values.put("phone",contact.getPhone());
        db.insert("contact",null,values);
    }
    public  void editCotact(Contact contact, String oldPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",contact.getName());
        values.put("phone",contact.getPhone());
        db.update("contact",values,"phone=?",new String[] {oldPhone} );
    }
    public ArrayList<Contact> getAllContact(){
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        String script = "Select * from Contact";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script,null);
        while(cursor.moveToNext()){
            Contact contact = new Contact();
            contact.setId(cursor.getInt(0));
            contact.setName(cursor.getString(1));
            contact.setPhone(cursor.getString(2));
            contact.setAvatar(R.drawable.ic_launcher_avatar);
            contacts.add(contact);
        }
        return contacts;
    }
    public void deleteContact(String oldPhone){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("contact","phone=?",new String[]{oldPhone});
        db.close();
    }
}
