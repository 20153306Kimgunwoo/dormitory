package com.example.mokdorm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class QnA extends AppCompatActivity {

    ListView qna;
    private Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qna);

        qna = (ListView)findViewById(R.id.qna);

        List<String> data = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data);
        qna.setAdapter(adapter);

        data.add("인검 몇시인가요?");
        data.add("LAN이 안 돼요");
        data.add("3층 세탁기 고장났어요");
        data.add("야식공간 신청 어디서 해요?");
        adapter.notifyDataSetChanged();

        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QnA.this, AddQnA.class);
                startActivity(intent);
            }
        });
    }
}