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

class getBooleanData extends AsyncTask<String, String, Boolean> {
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
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        progressDialog.dismiss();
        Log.d(TAG, "2 ");
    }


    @Override
    protected Boolean doInBackground(String... params) {

        String id = (String)params[1];

        String serverURL = (String)params[0];
        String postParameters = "st_Id=" + id;


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
            Log.d(TAG, "1");

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

            Log.d(TAG, "234 " + sb.toString());

            if(sb.toString().equals("successed"))
                return true;
            else
                return false;

        } catch (Exception e) {

            Log.d(TAG, "3 " , e);

            return false;
        }
    }
}