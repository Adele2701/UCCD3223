package com.example.myapplication2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class First extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        EditText fill_amount = findViewById(R.id.input_amount);
        EditText fill_ppl = findViewById(R.id.input_people);
        TextView display = findViewById(R.id.display_eq);
        Button button = findViewById(R.id.calculateButton);
        Button button1 = findViewById(R.id.clear);
        Button button_back = findViewById(R.id.back);
        Button button_rec = findViewById(R.id.history);

        SharedPreferences pref = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        //When want to clear history
        //edit.clear();
        //edit.commit();

        //to trigger equal break down calculation
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double totalBillAmount = Double.parseDouble(fill_amount.getText().toString());
                int numberOfPeople = Integer.parseInt(fill_ppl.getText().toString());

                //Equal break down
                double equalAmount = totalBillAmount / numberOfPeople;

                display.setText("Equal break-down scenario: Total bill RM" +
                        String.format("%.2f", totalBillAmount) +
                        ", number of people " +
                        numberOfPeople +
                        ", each person to pay RM" +
                        String.format("%.2f", equalAmount) + ".");

                // Plus one everytime clicked
                int i = pref.getInt("count", 0) + 1;

                // store to SharedPreference
                edit.putString("history_eq_" + i, String.format("%.2f", equalAmount));
                edit.putInt("count", i);
                edit.apply();
            }
        });


        //clear all inputs and output
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fill_amount.getText().clear();
                fill_ppl.getText().clear();
                display.setText("");

            }
        });


        //back button
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First.this, MainMenu.class);
                startActivity(intent);
            }
        });

        //go to record list
        button_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First.this, Record.class);
                startActivity(intent);
            }
        });
    }
}