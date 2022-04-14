package com.comtproject.plutomusic;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyView extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView artisit;
    public ImageButton image;
    public Button remove;

        //the setting of layout item_song

    public MyView(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.Title);
        artisit = itemView.findViewById(R.id.Artisit);
        image = itemView.findViewById(R.id.image);
        remove = itemView.findViewById(R.id.remove);


    }
}
