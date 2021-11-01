package com.example.mokdorm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Notice extends AppCompatActivity {

    public static final int pageTextCount = 10;
    ListView notice;
    List<String> data;
    String[][] parseredData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);

        notice = (ListView)findViewById(R.id.notice);

        data = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data);
        notice.setAdapter(adapter);

        GetAllTextCount task = new GetAllTextCount();

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        String id = auto.getString("UserId", null);

        task.setProgressDialog(new ProgressDialog(Notice.this));
        try {
            int textCount = task.execute("http://192.168.0.7/MokDorm/notice/notice_num.php",id).get();

            textCount = (textCount / pageTextCount);

            GetTextTitle task2 = new GetTextTitle();
            task2.setProgressDialog(new ProgressDialog(Notice.this));

            Log.d("숫자 몇 냈나",Integer.toString(textCount));



            String textTitles = task2.execute("http://192.168.0.7/MokDorm/notice/notice_print.php",id,Integer.toString(0)).get();

            jsonParserList(textTitles);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();

        notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.w("pos = ", String.valueOf(position));
                Intent intent = new Intent(Notice.this, NoticeText.class);
                intent.putExtra("n_title",parseredData[position][0]);
                intent.putExtra("n_date",parseredData[position][1]);
                startActivity(intent);
                finish();
            }
        });
    }

    public String[][] jsonParserList(String pRecvServerPage) {

        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        try {
            //JSONObject json = new JSONObject(pRecvServerPage);
            //JSONArray jArr = json.getJSONArray("List");

            JSONArray jArr = new JSONArray(pRecvServerPage);
            JSONObject json;

            // 받아온 pRecvServerPage를 분석하는 부분
            String[] jsonName = {"n_title", "n_date"};
            parseredData = new String[jArr.length()][jsonName.length];

            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                if(json != null) {
                    for(int j = 0; j < jsonName.length; j++) {
                        parseredData[i][j] = json.getString(jsonName[j]);
                    }
                }
            }

            // 분해 된 데이터를 확인하기 위한 부분
            for(int i=0; i<parseredData.length; i++){
                String st = "";
                st = st + "제목 : " + parseredData[i][0]  + "\n";
                st = st + "날짜 : " + parseredData[i][1];
                data.add(st);
            }

            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}