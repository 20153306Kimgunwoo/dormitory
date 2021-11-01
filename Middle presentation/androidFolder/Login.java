package com.example.mokdorm;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    private long mLastClickTime = 0;

    private static String IP_ADDRESS = "172.16.19.213";
    private static String TAG = "phptest"; // 이부분 바꿔야 함

    private EditText mEditTextID, mEditTextPassword;
    private TextView textView_check;
    private CheckBox checkBox;

    private Button btn_login, btn_signUp;

    String loginId, loginPwd;
    boolean userAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        mEditTextID = (EditText) findViewById(R.id.editText_UserID);
        mEditTextPassword = (EditText) findViewById(R.id.editText_UserPassword);
        btn_login = (Button) findViewById(R.id.btn_login);
        textView_check = (TextView) findViewById(R.id.textView_check);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        loginId = auto.getString("UserId", null);
        loginPwd = auto.getString("UserPwd", null);
        userAutoLogin = auto.getBoolean("UserAutoLogin", false);

        if (userAutoLogin != false) {
            try {
                InsertData task = new InsertData(new ProgressDialog(Login.this));
                String check = task.execute("http://192.168.0.7/MokDorm/login/login.php", loginId, loginPwd).get();
                if (check.equals("correct")) {
                    //getUserInfo(loginId);
                    Toast.makeText(Login.this, loginId + "님 자동로그인 입니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Main.class);
                    startActivity(intent);
                    finish();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                String id = mEditTextID.getText().toString();
                String password = mEditTextPassword.getText().toString();

                InsertData task = new InsertData(new ProgressDialog(Login.this));
                try {
                    String check = task.execute("http://192.168.0.7/MokDorm/login/login.php", id, password).get(); // 이부분도 경수가 만든곳으로 바꾸기
                    if (id.length() == 0) {
                        textView_check.setText("잘못된 입력입니다");
                    } else if (password.length() == 0) {
                        textView_check.setText("잘못된 입력입니다");
                    } else if (check.equals("correct")) {
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.putString("UserId", id);
                        autoLogin.putString("UserPwd", password);
                        if (checkBox.isChecked()) {
                            autoLogin.putBoolean("UserAutoLogin", true);
                        }
                        else{
                            autoLogin.putBoolean("UserAutoLogin", false);
                        }
                        autoLogin.commit();
                        getUserInfo(id);
                        Intent intent = new Intent(Login.this, Main.class);
                        startActivity(intent);
                    } else if (check.equals("incorrect")) {
                        textView_check.setText("다시 입력해주세요");
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getUserInfo(String id) {

        getInfoData task = new getInfoData(new ProgressDialog(Login.this));
        try {
            String info = task.execute("http://192.168.0.7/MokDorm/login/loginDataSend.php", id).get();
            Log.w("info : ",info);
            JSONArray jArr = new JSONArray(info);
            JSONObject json;

            String[] jsonName = {"st_name", "st_num", "st_idNum1", "st_idNum2", "st_phoneNum", "st_major"};
            String[] parseredData = new String[jsonName.length];

            json = jArr.getJSONObject(0);
            if (json != null) {
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[j] = json.getString(jsonName[j]);
                }
            }

            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
            SharedPreferences.Editor autoLogin = auto.edit();
            autoLogin.putString("st_name", parseredData[0]);
            autoLogin.putString("st_num", parseredData[1]);
            autoLogin.putString("st_idNum1", parseredData[2]);
            autoLogin.putString("st_idNum2", parseredData[3]);
            autoLogin.putString("st_phoneNum", parseredData[4]);
            autoLogin.putString("st_major", parseredData[5]);
            Log.w("studentInfo : ", parseredData[0]+parseredData[1]+parseredData[2]+parseredData[3]+parseredData[4]+parseredData[5]);
            autoLogin.commit();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}