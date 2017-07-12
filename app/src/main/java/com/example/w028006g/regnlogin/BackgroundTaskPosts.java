package com.example.w028006g.regnlogin;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTaskPosts extends AsyncTask<String,Void,String> {

    Context ctx;
    BackgroundTaskPosts(Context ctx)
    {
        this.ctx=ctx;
    }

    protected String doInBackground(String... params) {

        String reg_url="https://concussive-shirt.000webhostapp.com/add_post.php";
        String method=params[0];
        String text = "";
        if(method.equals("register"))
        {
            String email=params[1];
            String pid =params[2];
            try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();

                String data= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("tid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8");
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                bufferedWriter.write(data);
                bufferedWriter.flush();
                int statusCode = httpURLConnection.getResponseCode();
                if (statusCode == 200) {

                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null)
                        sb.append(line).append("\n");

                    text = sb.toString();
                    bufferedWriter.close();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e )
            {
                Log.d("doInBackground", e.getMessage());
            }
        }
        return text;
    }

}
