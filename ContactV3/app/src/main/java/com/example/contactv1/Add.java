package com.example.contactv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Add extends AppCompatActivity {

    private EditText edtName, edtPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edtName=findViewById(R.id.edt_name);
        edtPhone=findViewById(R.id.edt_phone);
    }
    public void onClickClose(View view){
        finish();
    }

    public void onClickDone(View view){
        Intent intent= new Intent(Add.this, MainActivity.class);
        Bundle bundle= new Bundle();
        bundle.putString("AName", edtName.getText().toString());
        bundle.putString("APhone",edtPhone.getText().toString());
        intent.putExtra("AddToMain",bundle);
        setResult(2,intent);
        finish();
    }
}
