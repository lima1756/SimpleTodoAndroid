package com.ivanmorett.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ivanmorett.simpletodo.adapters.ItemAdapter;
import com.ivanmorett.simpletodo.fragments.EditItemFragment;
import com.ivanmorett.simpletodo.interfaces.OnCloseDialog;
import com.ivanmorett.simpletodo.models.Item;
import com.ivanmorett.simpletodo.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnCloseDialog{


    ArrayList<Item> items;
    private ItemAdapter itemAdapter;
    @BindView(R.id.rvItems) RecyclerView rvItems;
    @BindView(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        items = new ArrayList<>();
        // initialize the items list
        readItems();

        // initialize the adapter using the items list
        itemAdapter = new ItemAdapter(items, this);

        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.setAdapter(itemAdapter);

    }



    // returns the file in which the data is stored
    private File getDataFile() {
        return new File(getFilesDir(), "todo1.txt");
    }

    // read the items from the file system
    private void readItems() {
        try {
            ArrayList<String> lines;
            // create the array using the content in the file
            lines = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
            for(String line : lines){
                String[] data = line.split(",");
                items.add(new Item(data[0], data[1]));
            }
        } catch (IOException | ParseException | IndexOutOfBoundsException e) {
            // print the error to the console
            e.printStackTrace();
            // just load an empty list
            items = new ArrayList<>();
        }
    }

    // write the items to the filesystem
    private void writeItems() {
        try {
            // save the item list as a line-delimited text file
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            // print the error to the console
            e.printStackTrace();
        }
    }

    @OnClick(R.id.fab)
    public void addItem(){
        Item item = new Item();


        FragmentManager fm = getSupportFragmentManager();

        EditItemFragment editNameDialogFragment = EditItemFragment.newInstance(item, this);

        editNameDialogFragment.show(fm, "fragment");

    }


    @Override
    public void beforeClose(Item item) {
        items.add(item);
        itemAdapter.notifyItemChanged(items.size()-1);
        writeItems();
    }
}
