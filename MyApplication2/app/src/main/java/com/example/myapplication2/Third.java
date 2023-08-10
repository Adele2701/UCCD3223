package com.example.myapplication2;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Third extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        EditText fill_amount = findViewById(R.id.input_amount);
        EditText fill_ppl = findViewById(R.id.input_people);
        TextView display = findViewById(R.id.display_cust);
        Button button = findViewById(R.id.verifyButton);
        Button button1 = findViewById(R.id.clear);
        Button button_back = findViewById(R.id.back);
        Button button_rec = findViewById(R.id.history);

        LinearLayout container = findViewById(R.id.container);
        ArrayList<Double> each_amt = new ArrayList<>();

        SharedPreferences pref = getSharedPreferences("SharedPreferences_3", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        //When want to clear history
        //edit.clear();
        //edit.commit();

        fill_ppl.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    int numberOfPeople = Integer.parseInt(fill_ppl.getText().toString());
                    //ArrayList<Integer> ratio = new ArrayList<>();

                    container.removeAllViews();

                    for (int i = 0; i < numberOfPeople; i++) {

                        TextView percentage = new TextView(Third.this);
                        EditText fill_ind_amount = new EditText(Third.this);

                        percentage.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        percentage.setText((i + 1) + ") " + getString(R.string.ind_amount));
                        percentage.setTextSize(18);

                        fill_ind_amount.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        fill_ind_amount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

                        each_amt.clear();


                        //ensure no empty string
                        fill_ind_amount.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View view, int keyCode, KeyEvent event) {
                                try {
                                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                                        each_amt.add(Double.parseDouble(fill_ind_amount.getText().toString()));
                                        return true;
                                    }
                                } catch (NumberFormatException e) {
                                    each_amt.add(0.0);
                                }
                                return false;
                            }
                        });
                        container.addView(percentage);
                        container.addView(fill_ind_amount);
                    }

                    return true;
                }
                return false;
            }
        });


        //to validate each inputs, total same as bill total
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double totalBillAmount = Double.parseDouble(fill_amount.getText().toString()),
                        total = 0.0;
                int numberOfPeople = Integer.parseInt(fill_ppl.getText().toString());

                for (int i = 0; i < each_amt.size(); i++) {
                    total += each_amt.get(i);
                }


                //validation
                if (total != totalBillAmount) { //Warning
                    display.setText(getString(R.string.warning_amt));
                } else {
                    display.setText("Custom break-down by amount scenario: Total bill RM" +
                            String.format("%.2f", totalBillAmount) +
                            ", number of people " + numberOfPeople +
                            ", each person to pay the individual amount: ");

                    // Plus one everytime clicked button
                    int m = pref.getInt("count", 0) + 1;

                    for (int i = 0; i < each_amt.size(); i++) {
                        if (i == 0)
                            display.append("RM" + String.format("%.2f", each_amt.get(i)));
                        else if (i == each_amt.size() - 1)
                            display.append(", RM" + String.format("%.2f", each_amt.get(i)) + ".");
                        else
                            display.append(", RM" + String.format("%.2f", each_amt.get(i)));

                        // Store to SharedPreference
                        edit.putString("history_cust_" + m + "_" + (i + 1), String.format("%.2f", each_amt.get(i)));
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
                Intent intent = new Intent(Third.this, MainMenu.class);
                startActivity(intent);
            }
        });


        //go to record list
        button_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Third.this, Record3.class);
                startActivity(intent);
            }
        });
    }
}
