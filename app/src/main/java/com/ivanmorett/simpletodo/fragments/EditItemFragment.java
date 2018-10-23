package com.ivanmorett.simpletodo.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ivanmorett.simpletodo.R;
import com.ivanmorett.simpletodo.constants.DateConstants;
import com.ivanmorett.simpletodo.interfaces.OnCloseDialog;
import com.ivanmorett.simpletodo.database.Item;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditItemFragment extends DialogFragment {


    @BindView(R.id.etItem) EditText etItem;
    @BindView(R.id.etDueDate) EditText etDueDate;
    @BindView(R.id.btnSave) Button btnSave;
    private Item item;
    private DatePickerDialog.OnDateSetListener date;
    private Calendar calendar;
    private OnCloseDialog onCloseDialog;

    public EditItemFragment(){}

    public static EditItemFragment newInstance(Item item, OnCloseDialog onCloseDialog){
        EditItemFragment fragment = new EditItemFragment();
        Bundle args = new Bundle();
        fragment.item = item;
        fragment.onCloseDialog = onCloseDialog;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.calendar = Calendar.getInstance();

        etItem.setText(item.getText());
        etDueDate.setText(DateConstants.SIMPLE_DATE_FORMAT.format(item.getDueDate()));

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void updateLabel() {
        etDueDate.setText(DateConstants.SIMPLE_DATE_FORMAT.format(calendar.getTime()));
    }

    @OnClick(R.id.btnSave)
    public void save(){
        item.setText(etItem.getText().toString());
        item.setDueDate(calendar.getTime());
        if(item.verify()) {
            onCloseDialog.beforeClose(item);
            this.dismiss();
        }
        else
            Toast.makeText(getContext(), "Please verify the todo item is not blank", Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.etDueDate)
    public void displayCalendar(){
        new DatePickerDialog(getActivity(), date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
