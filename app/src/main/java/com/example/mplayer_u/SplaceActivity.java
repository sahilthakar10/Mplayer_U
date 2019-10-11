package com.example.mplayer_u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

public class SplaceActivity extends AppCompatActivity {

    RelativeLayout search_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splace);

        search_r = (RelativeLayout)findViewById(R.id.search_r);


        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplaceActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        };
        handler.postDelayed(runnable , 2000);

        search_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(intent);
                finish();
                handler.removeCallbacks(runnable);
            }
        });

    }
}
