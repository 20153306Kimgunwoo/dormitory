package com.example.mokdorm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class NoticeText extends AppCompatActivity {

    TextView title_view, time_view, contents_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_text);

        title_view = findViewById(R.id.title);
        time_view = findViewById(R.id.time);
        contents_view = findViewById(R.id.contents);

        Intent intent = getIntent();
        String title = intent.getStringExtra("n_title");
        String date = intent.getStringExtra("n_date");

        Log.w("intent : ", title+date);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        String id = auto.getString("UserId", null);

        GetNoticeContents task = new GetNoticeContents();

        task.setProgressDialog(new ProgressDialog(NoticeText.this));
        try {
            String contents_str = task.execute("http://192.168.0.7/MokDorm/notice/notice_content.php",date).get();
            String content = jsonParserList(contents_str);
            contents_view.append(content);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        title_view.append(title);
        time_view.append(date);

    }

    public String jsonParserList(String pRecvServerPage) {

        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        try {
            //JSONObject json = new JSONObject(pRecvServerPage);
            //JSONArray jArr = json.getJSONArray("List");

            JSONArray jArr = new JSONArray(pRecvServerPage);
            JSONObject json = null;

            // 받아온 pRecvServerPage를 분석하는 부분
            String[] jsonName = {"n_contents"};
            String parseredData = null;
            json = jArr.getJSONObject(0);
            if(json != null) {
                parseredData = json.getString(jsonName[0]);
            }


            // 분해 된 데이터를 확인하기 위한 부분
            Log.w("noticeJson : ", parseredData);

            return parseredData;

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
            return null;
        }
    }
}