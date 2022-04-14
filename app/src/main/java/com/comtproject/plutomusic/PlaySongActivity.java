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

public class PlaySongActivity extends AppCompatActivity {


    static int currentIndex;
    static ArrayList<Song> favlist = new ArrayList<Song>();
    SeekBar musicbar;
    Handler handler = new Handler();
    SeekBar volumebar;
    AudioManager myVolumeChanger;
    SongCollection songCollection = new SongCollection();
    BestSongCollection bestSongCollection = new BestSongCollection();
    Button repeatBtn;
    Boolean repeatFlag = false;
    TextView totalTime;
    SharedPreferences sharedPreferences;
    private String title;
    private String artist;
    private String fileLink;
    private String filelinktwo;
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
        setContentView(R.layout.activity_play_song);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        musicbar = findViewById(R.id.seekBar2);
        //setting up the seekbar for music time changer
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
        //storage for the favs
        sharedPreferences = getSharedPreferences("Favourites", MODE_PRIVATE);
        String albums = sharedPreferences.getString("list", "");
        if (!albums.equals("")) {
            TypeToken<ArrayList<Song>> token = new TypeToken<ArrayList<Song>>() {
            };
            Gson gson = new Gson();
            favlist = gson.fromJson(albums, token.getType());
        }


        totalTime = findViewById(R.id.TotalTimer);
        repeatBtn = findViewById(R.id.repeat);
        Bundle songData = this.getIntent().getExtras();
        currentIndex = songData.getInt("index");
        Log.d("poly", "We have received : " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
        playSong(fileLink);


    }

    public void playPrevious(View view) {
        currentIndex = songCollection.getPrevSong(currentIndex);
        Log.d("temasek", "After playPrevious, the index is now  " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
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

        Song song = songCollection.getCurrentSong(index);
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


    public void playOrPauseMusic(View view) {
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
                    playOrPauseMusic(null);

                } else {
                    btnPlayPause.setBackgroundResource(R.drawable.group_7352);

                }

            }
        });
    }


    public void playNext(View view) {
        currentIndex = songCollection.getNextSong(currentIndex);
        displaySongBasedOnIndex(currentIndex);
        playSong(fileLink);
    }


    public void repeatSong(View view) {
        if (repeatFlag) {
            repeatBtn.setBackgroundResource(R.drawable.outline_repeat_black_20);

        } else {
            repeatBtn.setBackgroundResource(R.drawable.outline_repeat_on_white_18);

        }
        repeatFlag = !repeatFlag;
    }

    //mahth for the time for song
    public String createTimerLabel(int duration) {
        String timerLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        timerLabel += min + ":";

        if (sec < 10) timerLabel += "0";
        timerLabel += sec;

        return timerLabel;


    }

    public void backpress(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //super.onBackPressed();
        handler.removeCallbacks(mbar);
        player.release();
    }

    public void addtofav(View view) {

        //when the button is pressed, it changes to string
        //then stores in the ArrayList

        Song song = songCollection.getCurrentSong(currentIndex);
        favlist.add(song);
        Gson gson = new Gson();
        String json = gson.toJson(favlist);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("list", json);
        editor.apply();

        Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show();

    }


}