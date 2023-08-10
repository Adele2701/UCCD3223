package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TranslateAnimation translate = new TranslateAnimation(0f,0f,0f,-1000f);
        translate.setDuration(1000);

        Button button = findViewById(R.id.enter);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Handle touch events here
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button.startAnimation(translate);
                        break;
                    case MotionEvent.ACTION_UP:
                        Intent intent = new Intent(MainActivity.this, MainMenu.class);
                        startActivity(intent);
                        break;
                }
                return true; // Return 'true' to indicate that you've handled the touch event
            }
        });
    }
}
