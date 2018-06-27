package com.ivanmorett.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private Intent receivedData;
    private EditText etItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        receivedData = getIntent();

        String item = receivedData.getExtras().getString("item");


        etItem = findViewById(R.id.etItem);
        etItem.setText(item);

    }

    public void onSaveItem(View view){
        int id = receivedData.getExtras().getInt("id");
        Intent value = new Intent();
        String txt = etItem.getText().toString();
        if(Validation.validateInput(EditItemActivity.this, txt)){
            Intent result = new Intent();
            result.putExtra("item", txt);
            result.putExtra("id", id);
            setResult(RESULT_OK, result);
            finish();
        }

    }
}
