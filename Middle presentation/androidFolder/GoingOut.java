package com.example.mokdorm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

public class GoingOut extends AppCompatActivity {

    private Button btn_out;
    private Spinner mSpinnerMajor;
    private EditText date1, date2, date3, contents;
    private String o_kind, o_reason, o_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.going_out);

        btn_out = findViewById(R.id.btn_out);
        date1 = findViewById(R.id.g_date1);
        date2 = findViewById(R.id.g_date2);
        date3 = findViewById(R.id.g_date3);

        contents = findViewById(R.id.g_contents);

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date1.getText().toString().equals("") || date2.getText().toString().equals("") || date3.getText().toString().equals(""))
                    Toast.makeText(GoingOut.this," 날짜를 입력하지 않으셨습니다.",
                            Toast.LENGTH_SHORT).show();
                else{
                    o_date = date1.getText().toString()+"-"+date2.getText().toString()+"-"+date3.getText().toString();
                    if(contents.getText().toString().equals("")){
                        Toast.makeText(GoingOut.this,"사유를 입력하지 않으셨습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        o_reason = contents.getText().toString();
                        Insert_GoOut_Data Insert_data = new Insert_GoOut_Data(new ProgressDialog(GoingOut.this));
                        String getData = null;
                        try {
                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                            String id = auto.getString("st_num", null);

                            getData = Insert_data.execute("http://192.168.0.7/MokDorm/goOut/goOut_insert.php",id,o_reason,o_date,o_kind).get();

                            Log.w("goOutInsert : ", getData);

                            if(getData.equals("successed")) {
                                Toast.makeText(GoingOut.this,"정상적으로 작성되었습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(GoingOut.this,"작성이 실패하였습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Intent intent = new Intent(GoingOut.this, GoingOutView.class);
                startActivity(intent);
            }
        });

        mSpinnerMajor = findViewById(R.id.g_kind);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.goOutKind, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSpinnerMajor.setAdapter(adapter);

        mSpinnerMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                    o_kind = "외출";
                else
                    o_kind = "외박";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                o_kind = "외출";
            }
        });

    }
}