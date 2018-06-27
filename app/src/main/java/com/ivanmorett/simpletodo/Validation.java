package com.ivanmorett.simpletodo;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by ivanmorett on 6/26/18.
 */

final class Validation {
    private Validation(){}
    public static boolean validateInput(Context app, String text){
        if(text.isEmpty()){
            new AlertDialog.Builder(app).setTitle("Sorry!").setMessage("Sorry, we can't save an empty message").show();
            return false;
        }
        return true;
    }
}
