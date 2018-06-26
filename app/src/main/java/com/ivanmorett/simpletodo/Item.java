package com.ivanmorett.simpletodo;

/**
 * Created by ivanmorett on 6/26/18.
 */

class Item {

    private String text;
    private Boolean state;

    public Item(String text){
        this.text = text;
        state = false;
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

    public Boolean getState() {
        return state;
    }

    public String getText() {
        return text;
    }
}
