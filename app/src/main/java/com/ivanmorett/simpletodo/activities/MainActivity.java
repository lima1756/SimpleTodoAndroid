package com.ivanmorett.simpletodo.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.ivanmorett.simpletodo.adapters.ItemAdapter;
import com.ivanmorett.simpletodo.database.AppDatabase;
import com.ivanmorett.simpletodo.fragments.EditItemFragment;
import com.ivanmorett.simpletodo.interfaces.OnCloseDialog;
import com.ivanmorett.simpletodo.database.Item;
import com.ivanmorett.simpletodo.R;
import com.ivanmorett.simpletodo.utils.RecyclerItemTouchHelper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnCloseDialog, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{


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

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvItems);


    }

    // read the items from the file system
    private void readItems() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                items.addAll(AppDatabase.getDatabase(MainActivity.this).itemDao().getAll());
            }
        }).start();
    }

    @OnClick(R.id.fab)
    public void addItem(){
        Item item = new Item();


        FragmentManager fm = getSupportFragmentManager();

        EditItemFragment editNameDialogFragment = EditItemFragment.newInstance(item, this);

        editNameDialogFragment.show(fm, "fragment");

    }


    @Override
    public void beforeClose(final Item item) {
        items.add(item);
        itemAdapter.notifyItemChanged(items.size()-1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getDatabase(MainActivity.this).itemDao().insert(item);
            }
        }).start();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ItemAdapter.ViewHolder) {
            final Item delete = items.get(viewHolder.getAdapterPosition());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase.getDatabase(MainActivity.this).itemDao().delete(delete);
                }
            }).start();
            items.remove(viewHolder.getAdapterPosition());
            itemAdapter.notifyDataSetChanged();
        }
    }
}
