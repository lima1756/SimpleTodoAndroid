package com.ivanmorett.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        String item = intent.getExtras().getString("item");
        final int id = intent.getExtras().getInt("id");

        final EditText etItem = findViewById(R.id.etItem);
        etItem.setText(item);

        Button btn = findViewById(R.id.btnSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
    }
}
