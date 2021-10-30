package com.example.se;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Notice extends AppCompatActivity {

    ListView notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);

        notice = (ListView)findViewById(R.id.notice);

        List<String> data = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data);
        notice.setAdapter(adapter);

        data.add("입사를 환영합니다");
        data.add("청소 깨끗이 해주세요");
        adapter.notifyDataSetChanged();

    }
}