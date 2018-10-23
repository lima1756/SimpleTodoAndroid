package com.ivanmorett.simpletodo.interfaces;

import com.ivanmorett.simpletodo.database.Item;

public interface OnCloseDialog {
    void beforeClose(Item item);
}
