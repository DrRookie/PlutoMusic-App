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

public class PlaySongActivityTwo extends AppCompatActivity {

    SeekBar musicbar;
    Handler handler = new Handler();
    SeekBar volumebar;
    AudioManager myVolumeChanger;

    private String title;
    private String artist;
    private String fileLink;
    private int drawable;
    static int currentIndextwo;
    private MediaPlayer player = new MediaPlayer();
    private Button btnPlayPause;
    BestSongCollection bestSongCollection = new BestSongCollection();


    Button repeatBtn;
    Boolean repeatFlag = false;

    TextView totalTime;
    static ArrayList<Song> favlisttwo = new ArrayList<Song>();
    SharedPreferences sharedPreferencestwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song_two);
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

        sharedPreferencestwo = getSharedPreferences("Favourites2", MODE_PRIVATE);
        String albums = sharedPreferencestwo.getString("list2", "");
        if (!albums.equals("")) {
            TypeToken<ArrayList<Song>> token = new TypeToken<ArrayList<Song>>() {
            };
            Gson gson = new Gson();
            favlisttwo = gson.fromJson(albums, token.getType());
        }


        totalTime = findViewById(R.id.TotalTimer);
        repeatBtn = findViewById(R.id.repeat);
        Bundle songData = this.getIntent().getExtras();
        currentIndextwo = songData.getInt("index");
        Log.d("poly", "We have received : " + currentIndextwo);
        displaySongBasedOnIndex(currentIndextwo);
        playSong(fileLink);


    }

    Runnable mbar = new Runnable() {
        @Override
        public void run() {
            if (player != null && player.isPlaying()) {
                musicbar.setProgress(player.getCurrentPosition());

            }
            handler.postDelayed(this, 1000);


        }
    };


    public void playPrevioustwo(View view) {
        currentIndextwo = bestSongCollection.getBestPrevSong(currentIndextwo);
        Log.d("temasek", "After playPrevious, the index is now  " + currentIndextwo);
        displaySongBasedOnIndex(currentIndextwo);
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

        Song song = bestSongCollection.getBestCurrentSong(index);
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


    public void playOrPauseMusictwo(View view) {
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
                    playOrPauseMusictwo(null);

                } else {
                    btnPlayPause.setBackgroundResource(R.drawable.group_7352);

                }

            }
        });
    }


    public void playNexttwo(View view) {
        currentIndextwo = bestSongCollection.getBestNextSong(currentIndextwo);
        displaySongBasedOnIndex(currentIndextwo);
        playSong(fileLink);
    }


    public void repeatSongtwo(View view) {
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

    public void backpresstwo(View view) {
        Intent intent = new Intent(this, Discover.class);
        startActivity(intent);
        handler.removeCallbacks(mbar);
        player.release();
    }

    public void addtofavtwo(View view) {

        Song song = bestSongCollection.getBestCurrentSong(currentIndextwo);
        favlisttwo.add(song);
        Gson gson = new Gson();
        String json = gson.toJson(favlisttwo);
        SharedPreferences.Editor editor = sharedPreferencestwo.edit();
        editor.putString("list2", json);
        editor.apply();

        Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show();

    }


}