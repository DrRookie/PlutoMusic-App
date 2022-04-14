package com.comtproject.plutomusic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

public class PlaySongActivityThree extends AppCompatActivity {
    static int currentIndexthree;
    static ArrayList<Song> favlistthree = new ArrayList<Song>();
    SeekBar musicbar;
    Handler handler = new Handler();
    SeekBar volumebar;
    AudioManager myVolumeChanger;
    HomeCollection homeCollection = new HomeCollection();
    Button repeatBtn;
    Boolean repeatFlag = false;
    TextView totalTime;
    SharedPreferences sharedPreferencesthree;
    private String title;
    private String artist;
    private String fileLink;
    private int drawable;
    private MediaPlayer player = new MediaPlayer();
    Runnable mbar = new Runnable() {
        @Override
        public void run() {
            if (player != null && player.isPlaying()) {
                musicbar.setProgress(player.getCurrentPosition());

            }
            handler.postDelayed(this, 1000);


        }
    };
    private Button btnPlayPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song_three);

        btnPlayPause = findViewById(R.id.btnPlayPause);
        musicbar = findViewById(R.id.seekBar2);
        musicbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (player != null && player.isPlaying()) {
                    player.seekTo(musicbar.getProgress());

                }


            }
        });

        volumebar = findViewById(R.id.seekbarvol);
        myVolumeChanger = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //get Max Volume
        int maxVol = myVolumeChanger.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //get Current Volume
        int currentVol = myVolumeChanger.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumebar.setMax(maxVol);
        volumebar.setProgress(currentVol);
        volumebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //set volume
                myVolumeChanger.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sharedPreferencesthree = getSharedPreferences("Favourites3", MODE_PRIVATE);
        String albums = sharedPreferencesthree.getString("list3", "");
        if (!albums.equals("")) {
            TypeToken<ArrayList<Song>> token = new TypeToken<ArrayList<Song>>() {
            };
            Gson gson = new Gson();
            favlistthree = gson.fromJson(albums, token.getType());
        }


        totalTime = findViewById(R.id.TotalTimer);
        repeatBtn = findViewById(R.id.repeat);
        Bundle songData = this.getIntent().getExtras();
        currentIndexthree = songData.getInt("index");
        Log.d("poly", "We have received : " + currentIndexthree);
        displaySongBasedOnIndex(currentIndexthree);
        playSong(fileLink);


    }

    public void playPreviousthree(View view) {
        currentIndexthree = homeCollection.getHomePrevSong(currentIndexthree);
        Log.d("temasek", "After playPrevious, the index is now  " + currentIndexthree);
        displaySongBasedOnIndex(currentIndexthree);
        playSong(fileLink);


    }


    public void playSong(String url) {


        try {
            player.reset();
            player.setDataSource(url);
            player.prepare();
            player.start();

            musicbar.setMax(player.getDuration());
            String gettotaltime = createTimerLabel(player.getDuration());
            totalTime.setText(gettotaltime);


            handler.removeCallbacks(mbar);
            handler.postDelayed(mbar, 1000);
            gracefullyStopsWhenMusicEnds();
            btnPlayPause.setBackgroundResource(R.drawable.group_7352);
            setTitle(title);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void displaySongBasedOnIndex(int index) {

        Song song = homeCollection.getHomeCurrentSong(index);
        title = song.getTitle();
        artist = song.getArtist();
        fileLink = song.getFileLink();
        drawable = song.getDrawable();
        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);
        TextView txtArtist = findViewById(R.id.txtArtist);
        txtArtist.setText(artist);
        ImageView imgCoverArt = findViewById(R.id.imgCoverArt);
        imgCoverArt.setImageResource(drawable);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(mbar);
        player.release();
    }


    public void playOrPauseMusicthree(View view) {
        if (player.isPlaying()) {
            player.pause();
            btnPlayPause.setBackgroundResource(R.drawable.group_7353);
        } else {
            player.start();
            btnPlayPause.setBackgroundResource(R.drawable.group_7352);
        }
    }

    private void gracefullyStopsWhenMusicEnds() {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (repeatFlag) {
                    playOrPauseMusicthree(null);

                } else {
                    btnPlayPause.setBackgroundResource(R.drawable.group_7352);

                }

            }
        });
    }


    public void playNextthree(View view) {
        currentIndexthree = homeCollection.getHomeNextSong(currentIndexthree);
        displaySongBasedOnIndex(currentIndexthree);
        playSong(fileLink);
    }


    public void repeatSongthree(View view) {
        if (repeatFlag) {
            repeatBtn.setBackgroundResource(R.drawable.outline_repeat_black_20);

        } else {
            repeatBtn.setBackgroundResource(R.drawable.outline_repeat_on_white_18);

        }
        repeatFlag = !repeatFlag;
    }

    public String createTimerLabel(int duration) {
        String timerLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        timerLabel += min + ":";

        if (sec < 10) timerLabel += "0";
        timerLabel += sec;

        return timerLabel;


    }

    public void backpressthree(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        handler.removeCallbacks(mbar);
        player.release();
    }

    public void addtofavthree(View view) {

        Song song = homeCollection.getHomeCurrentSong(currentIndexthree);
        favlistthree.add(song);
        Gson gson = new Gson();
        String json = gson.toJson(favlistthree);
        SharedPreferences.Editor editor = sharedPreferencesthree.edit();
        editor.putString("list3", json);
        editor.apply();

        Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show();

    }


}