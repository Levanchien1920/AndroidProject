package com.qhung.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    EditText mEdtName, mEdtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }
    public void onClickReturn(View v){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }
    public void onClickAdd(View v) {
        mEdtName = findViewById(R.id.edt_name);
        mEdtPhone = findViewById(R.id.edt_phone);
        Intent intent = new Intent();
        intent.putExtra("Name",mEdtName.getText().toString());
        intent.putExtra("Phone",mEdtPhone.getText().toString());
        if (mEdtName.getText().toString().isEmpty()||mEdtPhone.getText().toString().isEmpty())
            setResult(RESULT_CANCELED,intent);
        else setResult(RESULT_OK, intent);
        finish();
    }
}
