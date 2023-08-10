package com.example.myapplication2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Record extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        LinearLayout container = findViewById(R.id.container);
        container.removeAllViews();
        SharedPreferences pref = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        int m = pref.getInt("count", 0);

        for (int i = 1; i < (m + 1); i++) {

            TextView name = new TextView(Record.this);
            TextView amount = new TextView(Record.this);

            name.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            name.setText(i + ") " + getString(R.string.name) + ":");
            name.setTextColor(Color.BLACK);
            name.setTextSize(18);

            amount.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            amount.setText(pref.getString("history_eq_" + i, "No value found"));
            amount.setTextColor(Color.BLACK);
            amount.setTextSize(18);

            container.addView(name);
            container.addView(amount);
        }
    }
}