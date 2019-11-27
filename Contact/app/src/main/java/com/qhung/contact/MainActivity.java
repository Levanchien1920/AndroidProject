package com.qhung.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ArrayList<Contact> contacts;
    MyDatabase db;
    ListView lvContact;
    CustomAdapter customAdaper;
    SearchView searchView;
    private static final int REQ_CODE_ADD = 1;
    private static final int REQ_CODE_EDIT = 2;
    Contact contact;
    int vt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvContact = (ListView) findViewById(R.id.lv_Contact);
        searchView = (SearchView) findViewById(R.id.search_view);
//        Contact contact1 = new Contact("Nguyễn Văn A","0988 933 xxx", R.drawable.ic_launcher_avatar,0);
//        Contact contact2 = new Contact("Nguyễn Văn B","01667 585 545", R.drawable.ic_launcher_avatar,1);
//        contacts.add(contact1);
//        contacts.add(contact2);
        db = new MyDatabase(this);
        contacts = db.getAllContact();
        customAdaper = new CustomAdapter(this, R.layout.row_listview, contacts);
        lvContact.setAdapter(customAdaper);
        searchView.setOnQueryTextListener(this);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                intent.putExtra("Name", contacts.get(i).Name);
                intent.putExtra("Phone", contacts.get(i).Phone);
                intent.putExtra("Avatar", contacts.get(i).Avatar);
                intent.putExtra("Position", i);
                startActivityForResult(intent, REQ_CODE_EDIT);
            }
        });
        sapxep();
    }

    public void myOnClick1(View v) {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivityForResult(intent, REQ_CODE_ADD);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQ_CODE_ADD && resultCode == RESULT_OK) {
            String name = intent.getStringExtra("Name");
            String phone = intent.getStringExtra("Phone");
            Contact contact = new Contact(name, phone, R.drawable.ic_launcher_avatar, contacts.size());
            contacts.add(contact);
            db.addContact(contact);
            sapxep();
            customAdaper.notifyDataSetChanged();
        }
        if (requestCode == REQ_CODE_EDIT && resultCode == RESULT_OK) {
            String name = intent.getStringExtra("Name");
            String phone = intent.getStringExtra("Phone");
            int i = intent.getIntExtra("Position", 0);
            String oldPhone = contacts.get(i).getPhone();
            contacts.get(i).setName(name);
            contacts.get(i).setPhone(phone);
            db.editCotact(contacts.get(i),oldPhone);
            sapxep();
            customAdaper.notifyDataSetChanged();
        }
        if (requestCode==REQ_CODE_EDIT && resultCode == 3){
            String oldPhone=intent.getStringExtra("Phone");
            int i = intent.getIntExtra("Position", 0);
            contacts.remove(i);
            db.deleteContact(oldPhone);
            sapxep();
            customAdaper.notifyDataSetChanged();
        }
    }
    public void sapxep(){
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                return contact1.getName().compareTo(contact2.getName());
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        customAdaper.getFilter().filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
