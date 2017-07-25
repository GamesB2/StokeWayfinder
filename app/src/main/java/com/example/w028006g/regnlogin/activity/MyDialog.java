package com.example.w028006g.regnlogin.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by w028006g on 25/07/2017.
 */

public class MyDialog extends Activity {
    static MyDialog dbA;
    //final AlertDialog alertDialog = new AlertDialog.Builder(MyDialog.this).create();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        dbA = this;

        alertDialog.setTitle("Downloading");
        alertDialog.setMessage("Downloading Required Data\nPlease Wait...");
        alertDialog.setIcon(android.support.design.R.drawable.navigation_empty_icon);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void hide()
    {
       // alertDialog.hide();
    }



    public static MyDialog getInstance(){
        return   dbA;
    }


}