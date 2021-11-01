package com.example.mokdorm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class GoingOutView extends AppCompatActivity {

    private Button btn_out;
    private TableLayout tableLayout;
    String[][] parseredData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.going_out_view);

        tableLayout = (TableLayout) findViewById(R.id.table);


        try {
            GetGoOutList task = new GetGoOutList();
            task.setProgressDialog(new ProgressDialog(GoingOutView.this));

            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
            String id = auto.getString("st_num", null);

            String textTitles = task.execute("http://192.168.0.7/MokDorm/goOut/goOut_print.php",id).get();

            jsonParserList(textTitles);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i=0; i<parseredData.length; i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            for(int j=0; j<parseredData[0].length; j++) {
                TextView textView = new TextView(this);
                textView.setText(parseredData[i][j]);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(15);
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }





        btn_out = findViewById(R.id.btn_out);
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GoingOutView.this, GoingOut.class);
                startActivity(intent);
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
            String[] jsonName = {"o_num", "o_reason", "o_date", "o_kind"};
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

            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}