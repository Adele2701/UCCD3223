package com.example.myapplication2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Record3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record3);

        LinearLayout container = findViewById(R.id.container);
        container.removeAllViews();

        SharedPreferences pref = getSharedPreferences("SharedPreferences_3", MODE_PRIVATE);

        int m = pref.getInt("count", 0);

        for (int i = 1; i < (m + 1); i++) {

            TextView name = new TextView(Record3.this);

            name.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            name.setText(i + ") " + getString(R.string.name) + ":");
            name.setTextColor(Color.BLACK);
            name.setTextSize(18);

            container.addView(name);

            for (int n = 1; n < 10 + 1; n++) {
                TextView amount = new TextView(Record3.this);

                amount.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                amount.setTextColor(Color.BLACK);
                amount.setTextSize(18);

                String key = "history_cust_" + i + "_" + n;
                String defValue = "No value found";
                String retrieveAmt = pref.getString(key, defValue);

                if (!retrieveAmt.equals(defValue)) {
                    amount.setText("RM" + retrieveAmt);
                    container.addView(amount);
                } else {
                    container.removeView(amount);
                }
            }
        }
    }
}