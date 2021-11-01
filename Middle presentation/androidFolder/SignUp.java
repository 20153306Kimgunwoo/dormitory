package com.example.mokdorm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class SignUp extends AppCompatActivity {

    private Button btn_cancel, btn_duplicate_verification, btn_check;
    private EditText mEditTextID, mEditTextPassword,mEditTextPasswordCheck,mEditTextStNum ,mEditTextName,
            mEditTextIDNum1, mEditTextIDNum2, mEditTextPhNum;
    private Spinner mSpinnerMajor;
    private TextView mTextViewID, mTextViewNote, mTextViewStMajor;
    Boolean bool = false;
    private static String TAG = "phptest"; // 이부분 바꿔야 함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btn_cancel = findViewById(R.id.button_Cancle);
        btn_duplicate_verification = findViewById(R.id.button_Duplicate_verification);
        btn_check = findViewById(R.id.button_Check);

        mEditTextID = (EditText)findViewById(R.id.stID);
        mTextViewID = (TextView)findViewById(R.id.textview);
        mEditTextPassword = (EditText)findViewById(R.id.stPassword);
        mEditTextPasswordCheck = (EditText)findViewById(R.id.stPasswordCheck);
        mEditTextStNum = (EditText)findViewById(R.id.stNum);
        mEditTextName = (EditText)findViewById(R.id.stName);
        mEditTextIDNum1 = (EditText)findViewById(R.id.stIDNum1);
        mEditTextIDNum2 = (EditText)findViewById(R.id.stIDNum2);
        mEditTextPhNum = (EditText)findViewById(R.id.stPhoneNumber);
        mSpinnerMajor = findViewById(R.id.stMajorSpinner);
        mTextViewStMajor = (TextView) findViewById(R.id.stMajor);
        mTextViewNote =  (TextView)findViewById(R.id.textview2);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSpinnerMajor.setAdapter(adapter);

        mSpinnerMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTextViewStMajor.setText(mSpinnerMajor.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTextViewStMajor.setText("");
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });

        btn_duplicate_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getBooleanData task = new getBooleanData();
                String id = mEditTextID.getText().toString();

                task.setProgressDialog(new ProgressDialog(SignUp.this));
                try {
                    bool = task.execute("http://192.168.0.7/MokDorm/SignUp/check_id.php", id).get();
                    if(bool)
                        mTextViewID.setText("사용 가능한 아이디 입니다.");
                    else
                        mTextViewID.setText("사용 불가능한 아이디 입니다.");
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String st_num =mEditTextStNum.getText().toString();
                String st_name = mEditTextName.getText().toString();
                String st_id = mEditTextID.getText().toString();
                String st_password = mEditTextPassword.getText().toString();
                String st_passwordCheck = mEditTextPasswordCheck.getText().toString();
                String st_idNum1 = mEditTextIDNum1.getText().toString();
                String st_idNum2 = mEditTextIDNum2.getText().toString();
                String st_phoneNum = mEditTextPhNum.getText().toString();
                String st_major = mTextViewStMajor.getText().toString();

                Log.d(TAG, "2 ");

                if(bool) {
                    if(st_password.equals(st_passwordCheck)){
                        if(st_name.equals("") || st_id.equals("") || st_password.equals("") || st_phoneNum.equals("") || st_major.equals("")){
                            mTextViewNote.setText("모든 항목을 기입하지 않으셨습니다.");
                        }
                        else {
                            Insert_SignUp_Data Insert_data = new Insert_SignUp_Data(new ProgressDialog(SignUp.this));
                            String getData = null; // 이부분도 경수가 만든곳으로 바꾸기
                            try {
                                getData = Insert_data.execute("http://192.168.0.7/MokDorm/SignUp/SignUp.php",st_num,st_name,st_id,st_password,st_idNum1,st_idNum2,st_phoneNum,st_major).get();

                                if(getData.equals("successed")) {
                                    mTextViewNote.setText(getData);

                                    Intent intent = new Intent(SignUp.this, Login.class);
                                    startActivity(intent);
                                }
                                else
                                    mTextViewNote.setText(getData);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else{
                        mTextViewNote.setText("비밀번호와 비밀번호 확인란이 일치하지 않습니다.");
                    }
                }
                else{
                    mTextViewNote.setText("잘못된 아이디 입니다.");
                }
            }
        });
    }
}