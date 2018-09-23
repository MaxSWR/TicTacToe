package com.softpunkgame.max.tictactoe;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;

import com.softpunkgame.max.tictactoe.drawerview.DrawView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout main = findViewById(R.id.main);
        Display display = getWindowManager().getDefaultDisplay();
        DrawView d = new DrawView(this, display);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(display.getWidth(), display.getHeight());
        d.setLayoutParams(params);
        main.addView(d);

    }


}

