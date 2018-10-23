package com.ivanmorett.simpletodo.models;

import com.ivanmorett.simpletodo.constants.DateConstants;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ivanmorett on 6/26/18.
 */

public class Item {

    private String text;
    private Boolean state;
    private Date dueDate;

    public Item(String text){
        this.text = text;
        state = false;
        this.dueDate = null;
    }

    public Item(String text, String dueDate) throws ParseException{
        this.text = text;
        state = false;
        this.setDueDate(Calendar.getInstance().getTime());
    }

    public Item(){
        this("");
    }

    public void changeState(){
        this.state = !this.state;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDueDate(Date dueDate){ this.dueDate = dueDate; }

    public void setDueDate(String dueDate) throws ParseException
    {
        this.dueDate = DateConstants.SIMPLE_DATE_FORMAT.parse(dueDate);
    }

    public Boolean getState() {
        return state;
    }

    public String getText() {
        return text;
    }

    public Date getDueDate() { return dueDate; }

    public String toString(){
        return this.text + "," + DateConstants.SIMPLE_DATE_FORMAT.format(this.dueDate);
    }

    public boolean verify(){
        return !this.text.replaceAll("\\s","").equals("") && this.dueDate!=null;
    }
}
