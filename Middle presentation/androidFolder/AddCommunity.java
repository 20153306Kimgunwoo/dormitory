package com.example.mokdorm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class AddCommunity extends AppCompatActivity {

    Button subBtn, canBtn;
    EditText title, contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_community);

        subBtn = findViewById(R.id.submit_btn);
        canBtn = findViewById(R.id.cancel_btn);
        title = findViewById(R.id.title);
        contents = findViewById(R.id.contents);

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(title.getText().toString().equals(""))
                    Toast.makeText(AddCommunity.this,"제목을 입력하지 않으셨습니다.",
                            Toast.LENGTH_SHORT).show();
                else{
                    if(contents.getText().toString().equals("")){
                        Toast.makeText(AddCommunity.this,"내용을 입력하지 않으셨습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        InsertText Insert_data = new InsertText(new ProgressDialog(AddCommunity.this));
                        String getData = null; // 이부분도 경수가 만든곳으로 바꾸기
                        try {
                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                            String id = auto.getString("st_num", null);

                            getData = Insert_data.execute("http://192.168.0.7/MokDorm/comu/comu_insert.php",id,title.getText().toString(),contents.getText().toString()).get();

                            if(getData.equals("successed")) {
                                Toast.makeText(AddCommunity.this,"정상적으로 작성되었습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(AddCommunity.this,"작성이 실패하였습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Intent intent = new Intent(AddCommunity.this, Community.class);
                startActivity(intent);
            }
        });

        canBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCommunity.this, Community.class);
                startActivity(intent);
            }
        });
    }
}