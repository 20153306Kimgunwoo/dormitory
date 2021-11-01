package com.example.mokdorm;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertText extends AsyncTask<String, String, String> {
    ProgressDialog progressDialog;

    InsertText(ProgressDialog progressDialog){
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

        Log.d("InsertText", "3 " + result);
    }


    @Override
    protected String doInBackground(String... params) {

        String id = (String)params[1];
        String title = (String)params[2];
        String contents = (String)params[3];
        String serverURL = (String)params[0];
        String postParameters = "st_num=" + id + "&cs_title=" + title + "&cs_contents=" + contents;


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
            Log.d("InsertText", "1 " + responseStatusCode);

            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            else{
                inputStream = httpURLConnection.getErrorStream();
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }

            bufferedReader.close();

            Log.w("InsertText", "234 " + sb.toString());

            return sb.toString();

        } catch (Exception e) {

            Log.d("InsertText", "InsertData: Error ", e);

            return "ss";
        }
    }
}
