package com.example.mokdorm;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GetAllTextCount extends AsyncTask<String, String, Integer> {
    ProgressDialog progressDialog;
    private static String TAG = "phptest"; // 이부분 바꿔야 함

    public void setProgressDialog(ProgressDialog progressDialog){
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setTitle("잠시 대기");
        progressDialog.show();
    }


    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        progressDialog.dismiss();
        Log.d(TAG, "5 ");
    }


    @Override
    protected Integer doInBackground(String... params) {

        String id = (String)params[1];

        String serverURL = (String)params[0];
        String postParameters = "id=" + id;

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
            Log.d(TAG, "4");

            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            else{
                inputStream = httpURLConnection.getErrorStream();
            }

            Scanner scan = new Scanner(inputStream, "UTF-8");

            Integer textCount = scan.nextInt();

            Log.d(TAG, "6 " + textCount);

            return textCount;

        } catch (Exception e) {
            Log.d(TAG, "7 " , e);
            return -1;
        }
    }
}
