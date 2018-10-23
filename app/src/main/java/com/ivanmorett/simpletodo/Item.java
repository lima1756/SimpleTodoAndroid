package com.ivanmorett.simpletodo;

import java.util.Date;

/**
 * Created by ivanmorett on 6/26/18.
 */

class Item {

    private String text;
    private Boolean state;
    private Date dueDate;

    public Item(String text){
        this.text = text;
        state = false;
        this.dueDate = null;
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

    public Boolean getState() {
        return state;
    }

    public String getText() {
        return text;
    }

    public Date getDueDate() { return dueDate; }
}
