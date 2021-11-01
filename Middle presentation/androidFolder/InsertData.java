package com.example.mokdorm;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class InsertData extends AsyncTask<String, String, String> {
    ProgressDialog progressDialog;
    private static final String TAG = "phptest"; // 이부분 바꿔야 함

    InsertData(ProgressDialog progressDialog){
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("기다려주세요");
        progressDialog.show();
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        progressDialog.dismiss();

        //    String a = result.toString();
        //    mTextViewResult.setText(a);

        //if(result == 1)
        //    mTextViewResult.setText("true");
        //else
        //    mTextViewResult.setText("false");
        Log.w(TAG, "3 " + result);
    }


    @Override
    protected String doInBackground(String... params) {

        String st_Id = (String)params[1];
        String st_password = (String)params[2];
        String serverURL = (String)params[0];
        String postParameters = "st_Id=" + st_Id + "&st_password=" + st_password;

        try {
            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();


            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();


            int responseStatusCode = httpURLConnection.getResponseCode();
            Log.w(TAG, "1 " + responseStatusCode);

            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            else{
                inputStream = httpURLConnection.getErrorStream();
            }

            Scanner scan = new Scanner(inputStream, "UTF-8");

            String bool = scan.next();

            Log.w(TAG, "2 " + bool);

            return bool;

        } catch (Exception e) {

            Log.w(TAG, "InsertData: Error ", e);

            return "ss";
        }
    }
}