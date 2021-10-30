package com.example.se;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    private ImageView img;
    private Button btn_out;
    private Button btn_out_vi;
    private Button btn_point;
    private Button btn_comm;
    private Button btn_notice;
    private Button btn_qna;
    private TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btn_out = findViewById(R.id.btn_out);
        btn_out_vi = findViewById(R.id.btn_out_vi);
        btn_point = findViewById(R.id.btn_point);
        btn_comm = findViewById(R.id.btn_comm);
        btn_notice = findViewById(R.id.btn_notice);
        btn_qna = findViewById(R.id.btn_qna);

        Intent intent = getIntent();
        String str = intent.getStringExtra("id");
        id = findViewById(R.id.id);
        id.setText("환영합니다" + str);

        img = (ImageView)findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"hello", Toast.LENGTH_SHORT).show();
            }
        });

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, GoingOut.class);
                startActivity(intent);
            }
        });

        btn_out_vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, GoingOutView.class);
                startActivity(intent);
            }
        });

        btn_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, PointView.class);
                startActivity(intent);
            }
        });

        btn_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, Community.class);
                startActivity(intent);
            }
        });

        btn_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, Notice.class);
                startActivity(intent);
            }
        });

        btn_qna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, QnA.class);
                startActivity(intent);
            }
        });
    }
}