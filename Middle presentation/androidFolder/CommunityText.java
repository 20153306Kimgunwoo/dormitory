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

public class CommunityText extends AppCompatActivity {

    TextView title_view, id_view, time_view, contents_view;
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_text);

        title_view = findViewById(R.id.title);
        id_view = findViewById(R.id.id);
        time_view = findViewById(R.id.time);
        contents_view = findViewById(R.id.contents);

        Intent intent = getIntent();
        String num = intent.getStringExtra("st_num");
        String title = intent.getStringExtra("cs_title");
        String date = intent.getStringExtra("cs_date");

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        id = auto.getString("UserId", null);

        Log.w("intent : ", num+title+date);

        GetContentsText task = new GetContentsText();

        task.setProgressDialog(new ProgressDialog(CommunityText.this));
        try {
            String contents_str = task.execute("http://192.168.0.7/MokDorm/comu/comu_content.php",num,date).get();
            String content = jsonParserList(contents_str);
            contents_view.append(content);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        title_view.append(title);
        id_view.append(num);
        time_view.append(date);
    }

    public String jsonParserList(String pRecvServerPage) {

        Log.i("???????????? ?????? ?????? ?????? : ", pRecvServerPage);
        try {
            //JSONObject json = new JSONObject(pRecvServerPage);
            //JSONArray jArr = json.getJSONArray("List");

            JSONArray jArr = new JSONArray(pRecvServerPage);
            JSONObject json = null;

            // ????????? pRecvServerPage??? ???????????? ??????
            String[] jsonName = {"cs_contents"};
            String parseredData = null;
            json = jArr.getJSONObject(0);
            if(json != null) {
                parseredData = json.getString(jsonName[0]);
            }


            // ?????? ??? ???????????? ???????????? ?????? ??????
            Log.w("comuJson : ", parseredData);

            return parseredData;

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
            return null;
        }
    }
}