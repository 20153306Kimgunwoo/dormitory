package com.example.mokdorm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

public class PointView extends AppCompatActivity {

    private Button btn_out;
    private TableLayout tableLayout;
    String[][] parseredData;
    private TextView plusPoint, minusPoint, totalPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_view);

        tableLayout = (TableLayout) findViewById(R.id.table);
        plusPoint = findViewById(R.id.plus);
        minusPoint = findViewById(R.id.minus);
        totalPoint = findViewById(R.id.total);
        int plus = 0, minus = 0, total = 0;

        try {
            GetGoOutList task = new GetGoOutList();
            task.setProgressDialog(new ProgressDialog(PointView.this));

            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
            String id = auto.getString("st_num", null);

            String textTitles = task.execute("http://192.168.0.7/MokDorm/point/point_print.php",id).get();
            Log.w("point", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            Log.w("point", "");
            Log.w("point", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

            if(textTitles != "") {
                jsonParserList(textTitles);

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

                for(int i=0; i < parseredData.length; i++){
                    if(parseredData[i][0].equals("상점")){
                        plus = plus + Integer.parseInt(parseredData[i][1]);
                    }
                    else{
                        minus = minus + Integer.parseInt(parseredData[i][1]);
                    }
                }
                total = plus - minus;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        plusPoint.setText(String.valueOf(plus)+"점");
        minusPoint.setText(String.valueOf(minus)+"점");
        totalPoint.setText(String.valueOf(total)+"점");
    }

    public String[][] jsonParserList(String pRecvServerPage) {

        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);
        try {
            //JSONObject json = new JSONObject(pRecvServerPage);
            //JSONArray jArr = json.getJSONArray("List");

            JSONArray jArr = new JSONArray(pRecvServerPage);
            JSONObject json;

            // 받아온 pRecvServerPage를 분석하는 부분
            String[] jsonName = {"p_kind", "p_point", "p_reason", "p_date"};
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