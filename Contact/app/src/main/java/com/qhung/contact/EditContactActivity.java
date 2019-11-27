package com.qhung.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class EditContactActivity extends AppCompatActivity {

    EditText medtName,medtPhone;
    ImageView imgView;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String phone = intent.getStringExtra("Phone");
        int avatar = intent.getIntExtra("Avatar",R.drawable.ic_launcher_avatar);
        i = intent.getIntExtra("Position",0);
        medtName = findViewById(R.id.edt_name);
        medtPhone = findViewById(R.id.edt_phone);
        imgView = findViewById(R.id.img_view);
        medtName.setText(name);
        medtPhone.setText(phone);
        imgView.setImageResource(avatar);
    }

    public void onClickYes(View v) {
        medtName = findViewById(R.id.edt_name);
        medtPhone = findViewById(R.id.edt_phone);
        Intent intent = new Intent();
        intent.putExtra("Name",medtName.getText().toString());
        intent.putExtra("Phone",medtPhone.getText().toString());
        intent.putExtra("Position",i);
        if (medtName.getText().toString().isEmpty()||medtPhone.getText().toString().isEmpty())
            setResult(RESULT_CANCELED,intent);
        else setResult(RESULT_OK, intent);
        finish();
    }

    public void onClickNo(View v){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    public void onClickDel(View v){
        Intent intent = new Intent();
        intent.putExtra("Phone",medtPhone.getText().toString());
        intent.putExtra("Position",i);
        setResult(3,intent);
        finish();
    }
}
