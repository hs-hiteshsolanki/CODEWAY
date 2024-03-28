package com.codeway_task.university_attendance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codeway_task.university_attendance.R;

public class MainActivity extends Activity {

    Button start;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start =(Button)findViewById(R.id.buttonstart);
        start.setOnClickListener(new View.OnClickListener() {
            Boolean isOnePressed = false, isSecondPlace = false;
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                isOnePressed = true;
                start.setBackgroundColor(Color.GRAY);
                if (isSecondPlace) {
                    start.setBackgroundColor(Color.WHITE);
                    isSecondPlace = false;
                }
                Intent intent =new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });



    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}