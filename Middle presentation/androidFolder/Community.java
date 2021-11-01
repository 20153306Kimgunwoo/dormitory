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
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Community extends AppCompatActivity {

    public static final int pageTextCount = 10;
    ListView comm;
    String[][] parseredData;
    private Button btn_add;
    List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        comm = (ListView)findViewById(R.id.comm);

        data = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data);
        comm.setAdapter(adapter);

        GetAllTextCount task = new GetAllTextCount();
        String id = "a";

        task.setProgressDialog(new ProgressDialog(Community.this));
        try {
            int textCount = task.execute("http://192.168.0.7/MokDorm/comu/comu_num.php",id).get();

            textCount = (textCount / pageTextCount);

            GetTextTitle task2 = new GetTextTitle();
            task2.setProgressDialog(new ProgressDialog(Community.this));

            Log.d("숫자 몇 냈나",Integer.toString(textCount));

            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
            id = auto.getString("UserId", null);

            String textTitles = task2.execute("http://192.168.0.7/MokDorm/comu/comu_print.php",id,Integer.toString(0)).get();

            jsonParserList(textTitles);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();

        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Community.this, AddCommunity.class);
                startActivity(intent);
            }
        });

        comm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.w("pos = ", String.valueOf(position));
                Intent intent = new Intent(Community.this, CommunityText.class);
                intent.putExtra("st_num",parseredData[position][0]);
                intent.putExtra("cs_title",parseredData[position][1]);
                intent.putExtra("cs_date",parseredData[position][2]);
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
            String[] jsonName = {"st_num", "cs_title", "cs_date"};
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
                st = st + "제목 : " + parseredData[i][1]+ "\n";
                st = st + "학번 : " + parseredData[i][0] + "\n";
                st = st + "날짜 : " + parseredData[i][2];
                data.add(st);
            }

            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}