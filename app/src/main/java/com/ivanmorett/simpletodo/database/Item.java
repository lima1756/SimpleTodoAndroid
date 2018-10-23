package com.ivanmorett.simpletodo.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.ivanmorett.simpletodo.constants.DateConstants;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ivanmorett on 6/26/18.
 */

@Entity
public class Item {

    @ColumnInfo
    @PrimaryKey(autoGenerate=true)
    private Long uid;

    @ColumnInfo(name="text")
    private String text;

    @ColumnInfo(name="state")
    private Boolean state;

    @ColumnInfo(name="due_date")
    private Date dueDate;

    @Ignore
    public Item(String text){
        this.text = text;
        state = false;
        this.setDueDate(Calendar.getInstance().getTime());
    }

    @Ignore
    public Item(String text, String dueDate) throws ParseException{
        this.text = text;
        state = false;
        this.setDueDate(dueDate);
    }


    public Item(){
        this("");
    }

    public void setUid(Long uid){ this.uid = uid; }

    public Long getUid(){ return this.uid; }

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

    public void setState(boolean state){ this.state = state; }
}
