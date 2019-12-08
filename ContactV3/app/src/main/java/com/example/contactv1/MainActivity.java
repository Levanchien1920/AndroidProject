package com.example.contactv1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ListView lvContact;
    private ArrayList<Contact> arrContact, copy, copy1;
    private CustomAdapter customAdapter;
    private MyDatabase db = new MyDatabase(this);
    private Context context;
    private EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrContact = new ArrayList<>();
        arrContact = db.getAllContact();
        lvContact = findViewById(R.id.lv_contact);
        searchView=(EditText)findViewById(R.id.search_view);

        customAdapter = new CustomAdapter(this, R.layout.row_listview, arrContact);
        lvContact.setAdapter(customAdapter);
        context = this;
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, Edit.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name", arrContact.get(i).getName());
                bundle.putString("Phone", arrContact.get(i).getPhone());
                bundle.putInt("position", i);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        sapxepten1(arrContact);
        copy = arrContact;
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrContact = copy;
                customAdapter=new CustomAdapter(context,R.layout.row_listview,arrContact);
                lvContact.setAdapter(customAdapter);

                int textLength=charSequence.length();
                ArrayList<Contact> tempContactArray=new ArrayList<>();
                for(Contact contact: arrContact){
                    if(textLength<=contact.getName().length()){
                        if(contact.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            tempContactArray.add(contact);
                        }
                    }
                }
                arrContact = tempContactArray;
                customAdapter=new CustomAdapter(context,R.layout.row_listview,arrContact);
                lvContact.setAdapter(customAdapter);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.sxt1:
                sapxepten1(arrContact);
                break;
            case R.id.sxt2:
                sapxepten2(arrContact);
                break;
            case R.id.sxho1:
                sapxepho1(arrContact);
                break;
            case R.id.sxho2:
                sapxepho2(arrContact);
                break;
        }
        customAdapter = new CustomAdapter(this, R.layout.row_listview, arrContact);
        lvContact.setAdapter(customAdapter);
        return super.onOptionsItemSelected(item);
    }
    public void onClickAdd(View v) {
        Intent intent = new Intent(MainActivity.this, Add.class);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 1) {
            Bundle bundle = data.getBundleExtra("EditToMain");
            int position = bundle.getInt("EPosition");
            String name = bundle.getString("EName");
            String phone = bundle.getString("EPhone");
            Contact contactEdit = new Contact(name, phone);
            db.editContact(contactEdit, arrContact.get(position).getPhone());
            arrContact.get(position).setName(name);
            arrContact.get(position).setPhone(phone);
            customAdapter.notifyDataSetChanged();

        }
        if (resultCode == 2 && requestCode == 2) {
            Bundle bundle = data.getBundleExtra("AddToMain");
            if (bundle.getString("AName").length() != 0 && bundle.getString("APhone").length() != 0) {
                Contact contactAdd = new Contact(bundle.getString("AName"), bundle.getString("APhone"));
                arrContact.add(contactAdd);
                db.addContact(contactAdd);
                customAdapter.notifyDataSetChanged();
            }
        }

        if (resultCode == 3 && requestCode == 1) {
            Bundle bundle = data.getBundleExtra("EditToMain");
            db.deleteContact(arrContact.get(bundle.getInt("EPosition")));
            arrContact.remove(arrContact.get(bundle.getInt("EPosition")));
            customAdapter.notifyDataSetChanged();
        }
    }
    public void sapxepten1(ArrayList<Contact> sx){
        Collections.sort(sx, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                String str1[] = contact1.getName().split("[ ]");
                String str2[] = contact2.getName().split("[ ]");
                return str1[str1.length-1].compareTo(str2[str2.length-1]);
            }
        });
    }
    public void sapxepten2(ArrayList<Contact> sx){
        Collections.sort(sx, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                String str1[] = contact1.getName().split("[ ]");
                String str2[] = contact2.getName().split("[ ]");
                return str2[str2.length-1].compareTo(str1[str1.length-1]);
            }
        });
    }
    public void sapxepho1(ArrayList<Contact> sx){
        Collections.sort(sx, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                return contact1.getName().compareTo(contact2.getName());
            }
        });
    }
    public void sapxepho2(ArrayList<Contact> sx){
        Collections.sort(sx, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                return contact2.getName().compareTo(contact1.getName());
            }
        });
    }
     
}

