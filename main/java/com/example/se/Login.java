package com.example.se;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    private EditText text_id;
    EditText text_pw;
    Button btn_login;
    Button btn_add;
    Button btn_id;
    Button btn_pw;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        text_id = findViewById(R.id.text_id);
        btn_login = findViewById(R.id.btn_login);
        text_pw = findViewById(R.id.text_pw);
        btn_add = findViewById(R.id.btn_add);
        btn_id = findViewById(R.id.btn_id);
        btn_pw = findViewById(R.id.btn_pw);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str = text_id.getText().toString();
                Intent intent = new Intent(Login.this, Main.class);
                intent.putExtra("id", str);
                startActivity(intent);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, Add.class);
                startActivity(intent);
            }
        });

        btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Login.this, FindId.class);
                startActivity(intent);
            }
        });

        btn_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Login.this, FindPw.class);
                startActivity(intent);
            }
        });

    }
}