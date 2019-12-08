package com.example.contactv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Edit extends AppCompatActivity {
    private EditText edtName, edtPhone;
    private int position;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        edtName=findViewById(R.id.edt_name);
        edtPhone=findViewById(R.id.edt_phone);
        Bundle bundle= getIntent().getExtras();
        edtName.setText(bundle.getString("Name"));
        edtPhone.setText(bundle.getString("Phone"));
        position=bundle.getInt("position");
    }

    public void onClickClose(View view) {
        finish();
    }

    public void onClickDone(View view){
        Intent intent= getIntent();
        Bundle bundle= new Bundle();
        bundle.putString("EName",edtName.getText().toString());
        bundle.putString("EPhone",edtPhone.getText().toString());
        bundle.putInt("EPosition",position);
        intent.putExtra("EditToMain",bundle);
        setResult(1,intent);
        finish();
    }

    public void onClickDelete(View view){
        Intent intent= new Intent();
        Bundle bundle= new Bundle();
        bundle.putInt("EPosition",position);
        intent.putExtra("EditToMain",bundle);
        setResult(3,intent);
        finish();
    }

}
