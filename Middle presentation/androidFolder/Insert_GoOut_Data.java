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

class Insert_GoOut_Data extends AsyncTask<String, String, String> {
    ProgressDialog progressDialog;
    private static final String TAG = "phptest"; // 이부분 바꿔야 함

    Insert_GoOut_Data(ProgressDialog progressDialog){
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

        Log.d(TAG, "3 " + result);
    }


    @Override
    protected String doInBackground(String... params) {

        String st_num = (String)params[1];
        String o_reason = (String)params[2];
        String o_date = (String)params[3];
        String o_kind = (String)params[4];
        String serverURL = (String)params[0];
        String postParameters = "st_num=" + st_num + "&o_reason=" + o_reason + "&o_date=" + o_date + "&o_kind=" + o_kind;

        Log.d(TAG, "st_num =  " + st_num);

        try {
            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();


            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();


            int responseStatusCode = httpURLConnection.getResponseCode();
            Log.d(TAG, "1 " + responseStatusCode);

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

            Log.d(TAG, "goOutInsert : " + sb.toString());

            return sb.toString();

        } catch (Exception e) {

            Log.d(TAG, "InsertData: Error ", e);

            return "ss";
        }
    }
}