package com.example.myapplication2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Record2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record2);

        LinearLayout container = findViewById(R.id.container);
        container.removeAllViews();

        SharedPreferences pref = getSharedPreferences("SharedPreferences_2", MODE_PRIVATE);
        SharedPreferences pref1 = getSharedPreferences("SharedPreferences_3", MODE_PRIVATE);

        int m = pref.getInt("count", 0);

        for (int i = 1; i < (m + 1); i++) {

            TextView name = new TextView(Record2.this);

            name.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            name.setText(i + ") " + getString(R.string.name) + ":");
            name.setTextColor(Color.BLACK);
            name.setTextSize(18);

            container.addView(name);

            for (int n = 1; n < 10 + 1; n++) {
                TextView amount = new TextView(Record2.this);

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


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String[] phoneNo = new String[1];
//                AlertDialog.Builder builder = new AlertDialog.Builder(Record2.this);
//                builder.setTitle("Enter Value");
//
//// Create an EditText view for input
//                final EditText inputEditText = new EditText(Record2.this);
//                builder.setView(inputEditText);
//
//// Add OK and Cancel buttons
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                       // String userInput = inputEditText.getText().toString();
//                        phoneNo[0] = inputEditText.getText().toString();
//
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNo[0]);
//                        intent.setData(uri);
//                        startActivity(intent);
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                // Create and show the AlertDialog
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//
//            }
//        });
