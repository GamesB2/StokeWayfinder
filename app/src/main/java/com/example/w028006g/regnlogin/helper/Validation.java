package com.example.w028006g.regnlogin.helper;

import android.widget.EditText;

/**
 * Created by w028006g on 09/06/2017.
 */
public class Validation {

    public boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}
