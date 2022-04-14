package com.comtproject.plutomusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<MyView> {

    public SongAdapter(List<Song> songs) {
        this.songs = songs;
    }

    List<Song> songs;
    Context context;
    int drawable;


    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View songView = inflater.inflate(R.layout.item_song, parent, false);
        MyView viewHolder = new MyView(songView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, @SuppressLint("RecyclerView") int position) {
        Song song = songs.get(position);
        TextView artist = holder.artisit;
        artist.setText(song.getArtist());
        TextView title = holder.title;
        title.setText(song.getTitle());
        ImageButton image = holder.image;
        drawable = song.getDrawable();
        image.setImageResource(drawable);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySongActivity.favlist.remove(position);
                notifyDataSetChanged();

            }
        });


    }


    @Override
    public int getItemCount() {
        return songs.size();
    }
}

