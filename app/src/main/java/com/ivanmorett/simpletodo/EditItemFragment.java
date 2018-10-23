package com.ivanmorett.simpletodo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class EditItemFragment extends DialogFragment {


    @BindView(R.id.etItem) EditText etItem;
    @BindView(R.id.etDueDate) EditText etDueDate;
    @BindView(R.id.btnSave) Button btnSave;
    private final static String DATEFORMAT="MM/dd/yy";
    private Item item;
    private DatePickerDialog.OnDateSetListener date;
    private Calendar calendar;

    public EditItemFragment(){}

    public static EditItemFragment newInstance(Item item){
        EditItemFragment fragment = new EditItemFragment();
        Bundle args = new Bundle();
        fragment.item = item;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.calendar = Calendar.getInstance();
        this.date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    item.setText(etItem.getText().toString());
                    item.setDueDate(new SimpleDateFormat(DATEFORMAT).parse(etDueDate.getText().toString()));
                }
                catch(ParseException ex){
                    Log.e("ERROR", "onClick: ",ex );
                }
            }
        });

        etDueDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new DatePickerDialog(getActivity(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT, Locale.US);
        etDueDate.setText(sdf.format(calendar.getTime()));
    }
}
