package com.example.se;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindId extends AppCompatActivity {

    private Button btn_find_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_id);

        btn_find_id = findViewById(R.id.btn_find_id);
        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //학번 받아서 아이디 돌려줌
            }
        });
    }
}