package com.example.mplayer_u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mplayer_u.Favourites.Favourites;
import com.example.mplayer_u.VIewModel.NoteViewModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Stream extends AppCompatActivity implements View.OnClickListener {

    String track_name, artist_name, url, image_url, Trackid, t_flag;
    ImageView arrow_back, favorite, shape_heart, shape_heart_red, image_url_pic, pause, play;
    TextView t_track_name, t_artist_name;
    public MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private NoteViewModel noteViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


        Intent intent = getIntent();
        track_name = intent.getStringExtra("track_name");
        artist_name = intent.getStringExtra("artist_name");
        url = intent.getStringExtra("url");
        image_url = intent.getStringExtra("image_url");
        Trackid = intent.getStringExtra("Trackid");
        t_flag = intent.getStringExtra("flag");

        findviewById();
        onclick();
        media();

        if (t_flag.equals("1")) {
            shape_heart_red.setVisibility(View.VISIBLE);
            shape_heart.setVisibility(View.GONE);
        } else if (t_flag.equals("0")) {
            shape_heart.setVisibility(View.VISIBLE);
            shape_heart_red.setVisibility(View.GONE);
        }

        Picasso.get().load(image_url).fit().into(image_url_pic);

        t_track_name.setText(track_name);
        t_artist_name.setText(artist_name);
    }

    public void media() {

        mediaPlayer = new MediaPlayer();

        // try to load data and play
        try {

            // give data to mediaPlayer
            mediaPlayer.setDataSource(url);
            // media player asynchronous preparation
            mediaPlayer.prepareAsync();

            // create a progress dialog (waiting media player preparation)
            final ProgressDialog dialog = new ProgressDialog(Stream.this);

            // set message of the dialog
            dialog.setMessage(getString(R.string.loading));

            // prevent dialog to be canceled by back button press
            dialog.setCancelable(false);

            // show dialog at the bottom
            dialog.getWindow().setGravity(Gravity.CENTER);

            // show dialog
            dialog.show();


            // execute this code at the end of asynchronous media player preparation
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(final MediaPlayer mp) {


                    //start media player
                    mp.start();

                    // link seekbar to bar view
                    seekBar = (SeekBar) findViewById(R.id.seekBar);

                    //update seekbar
                    mRunnable.run();

                    //dismiss dialog
                    dialog.dismiss();
                }
            });


        } catch (IOException e) {
            Activity a = this;
            a.finish();
            Toast.makeText(this, getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    public void findviewById() {
        image_url_pic = (ImageView) findViewById(R.id.image_url);
        arrow_back = (ImageView) findViewById(R.id.arrow_back);
        favorite = (ImageView) findViewById(R.id.favorite);
        shape_heart = (ImageView) findViewById(R.id.shape_heart);
        shape_heart_red = (ImageView) findViewById(R.id.shape_heart_red);

        pause = (ImageView) findViewById(R.id.pause);
        play = (ImageView) findViewById(R.id.play);

        t_track_name = (TextView) findViewById(R.id.track_name);
        t_artist_name = (TextView) findViewById(R.id.artist_name);
    }

    public void onclick() {
        arrow_back.setOnClickListener(this);
        favorite.setOnClickListener(this);
        shape_heart.setOnClickListener(this);
        shape_heart_red.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.arrow_back) {
           onBackPressed();
        } else if (view.getId() == R.id.favorite) {
            Intent intent = new Intent(getApplicationContext(), Favourites.class);
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;

            startActivity(intent);
        } else if (view.getId() == R.id.shape_heart) {
            shape_heart_red.setVisibility(View.VISIBLE);
            shape_heart.setVisibility(View.GONE);
            noteViewModel.updateflag1(Trackid);
            //   db.updateflag1(Trackid);
        } else if (view.getId() == R.id.shape_heart_red) {
            shape_heart.setVisibility(View.VISIBLE);
            shape_heart_red.setVisibility(View.GONE);
            noteViewModel.updateflag0(Trackid);
            //  db.updateflag0(Trackid);
        }
    }

    public void play(View view) {
        play.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);
        mediaPlayer.start();
    }


    public void pause(View view) {
        pause.setVisibility(View.GONE);
        play.setVisibility(View.VISIBLE);
        mediaPlayer.pause();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            if (mediaPlayer != null) {

                //set max value
                int mDuration = mediaPlayer.getDuration();
                seekBar.setMax(mDuration);

                //update total time text view
                TextView totalTime = (TextView) findViewById(R.id.totalTime);
                totalTime.setText(getTimeString(mDuration));

                //set progress to current position
                int mCurrentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mCurrentPosition);

                //update current time text view
                TextView currentTime = (TextView) findViewById(R.id.currentTime);
                currentTime.setText(getTimeString(mCurrentPosition));

                //handle drag on seekbar
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (mediaPlayer != null && fromUser) {
                            mediaPlayer.seekTo(progress);
                        }
                    }
                });


            }

            //repeat above code every second
            mHandler.postDelayed(this, 10);
        }
    };

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        long hours = millis / (1000 * 60 * 60);
        long minutes = (millis % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = ((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

        buf
                .append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
}
