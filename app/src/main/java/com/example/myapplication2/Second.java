package com.example.myapplication2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        EditText fill_amount = findViewById(R.id.input_amount);
        EditText fill_ppl = findViewById(R.id.input_people);
        TextView display = findViewById(R.id.display_cust);
        Button button = findViewById(R.id.calculateButton);
        Button button1 = findViewById(R.id.clear);
        Button button_back = findViewById(R.id.back);
        Button button_rec = findViewById(R.id.history);

        LinearLayout container = findViewById(R.id.container);
        ArrayList<Integer> ratio = new ArrayList<>();

        SharedPreferences pref = getSharedPreferences("SharedPreferences_2", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        //When want to clear history
        //edit.clear();
        //edit.commit();


        //to trigger the editText according to numberOfPeople
        fill_ppl.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    int numberOfPeople = Integer.parseInt(fill_ppl.getText().toString());

                    container.removeAllViews();

                    //Custom break down
                    for (int i = 0; i < numberOfPeople; i++) {

                        TextView percentage = new TextView(Second.this);
                        EditText fill_ratio = new EditText(Second.this);

                        percentage.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        percentage.setText((i + 1) + ") " + getString(R.string.ratio));
                        percentage.setTextColor(Color.BLACK);
                        percentage.setTextSize(18);

                        fill_ratio.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        fill_ratio.setInputType(InputType.TYPE_CLASS_NUMBER);

                        ratio.clear();

                        //only get the value when enter or action_down happen
                        fill_ratio.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View view, int keyCode, KeyEvent event) {
                                try {
                                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                                        ratio.add(Integer.parseInt(fill_ratio.getText().toString()));
                                        return true;
                                    }
                                } catch (NumberFormatException e) {
                                    ratio.add(0);
                                }
                                return false;
                            }
                        });
                        container.addView(percentage);
                        container.addView(fill_ratio);
                    }

                    return true;
                }
                return false;
            }
        });


        //trigger to calculate the amount by percentage
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double totalBillAmount = Double.parseDouble(fill_amount.getText().toString());
                int numberOfPeople = Integer.parseInt(fill_ppl.getText().toString());
                int ratios, total = 0;
                ArrayList<Double> amount = new ArrayList<>();


                //Custom break down
                for (int i = 0; i < ratio.size(); i++) {
                    ratios = ratio.get(i);
                    amount.add(totalBillAmount * (ratios / 100.0));
                    total += ratios;
                }


                //validation
                if (total != 100) {
                    display.setText(getString(R.string.warning_per));
                } else {
                    display.setText("Custom break-down by percentage scenario: Total bill RM" +
                            String.format("%.2f", totalBillAmount) +
                            ", number of people " + numberOfPeople +
                            ", each person to pay by percentage and the amount (");

                    // Plus one everytime clicked button
                    int m = pref.getInt("count", 0) + 1;

                    for (int i = 0; i < ratio.size(); i++) {
                        if (i == 0)
                            display.append(ratio.get(i) + "%: ");
                        else
                            display.append(", " + ratio.get(i) + "%: ");

                        if (i == ratio.size() - 1)
                            display.append(" RM" + String.format("%.2f", amount.get(i)) + ").");
                        else
                            display.append(" RM" + String.format("%.2f", amount.get(i)));

                        // Store to SharedPreference
                        edit.putString("history_cust_" + m + "_" + (i + 1), String.format("%.2f", amount.get(i)));
                        edit.putInt("count", m);
                        edit.apply();
                    }
                }
            }
        });


        //clear all inputs and output
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fill_amount.getText().clear();
                fill_ppl.getText().clear();
                container.removeAllViews();
                display.setText("");

            }
        });


        //back button
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Second.this, MainMenu.class);
                startActivity(intent);
            }
        });


        //go to record list
        button_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Second.this, Record2.class);
                startActivity(intent);
            }
        });
    }
}