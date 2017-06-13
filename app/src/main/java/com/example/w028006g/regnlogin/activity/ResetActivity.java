package com.example.w028006g.regnlogin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.w028006g.regnlogin.R;

/**
 * Created by w028006g on 08/06/2017.
 */
public class ResetActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        WebView myWebView = (WebView) findViewById(R.id.webviewreset);
        myWebView.loadUrl("https://concussive-shirt.000webhostapp.com/reset/index.php");
    }


}
