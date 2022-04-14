package com.comtproject.plutomusic;

public class SongCollection {
    private Song songs[] = new Song[5];


    public SongCollection() {

        Song theWayYouLookTonight = new Song("S1001", "The Way You Look Tonight",
                "Michael Bublé",
                "https://p.scdn.co/mp3-preview/a5b8972e764025020625bbf9c1c2bbb06e394a60?cid=2afe87a64b0042dabf51f37318616965",
                4.66, R.drawable.michael_buble_collection);

        Song billieJean = new Song("S1002", "Billie Jean",
                "Michael Jackson",
                "https://p.scdn.co/mp3-preview/f504e6b8e037771318656394f532dede4f9bcaea?cid=2afe87a64b0042dabf51f37318616965",
                4.90, R.drawable.billie_jean);


        Song one = new Song("S1003", "One",
                "Ed Sheeran",
                "https://p.scdn.co/mp3-preview/daa8679253ba20620db6e1db9c88edfcf1f4069f?cid=2afe87a64b0042dabf51f37318616965",
                4.21, R.drawable.photograph);


        Song onTheFloor = new Song("S1004", "On The Floor",
                "Jennifer Lopez",
                "https://p.scdn.co/mp3-preview/d56ad21679fe1f1b2cc800b6312bc284e53639a5?cid=2afe87a64b0042dabf51f37318616965",
                3.36, R.drawable.on_the_floor);

        Song holiday = new Song("S1005", "Holiday",
                "KSI", "https://p.scdn.co/mp3-preview/de1bf03287866de45384bd67332c9d98e9438aad?cid=2afe87a64b0042dabf51f37318616965",
                3.13, R.drawable.holiday);


        songs[0] = theWayYouLookTonight;
        songs[1] = billieJean;
        songs[2] = one;
        songs[3] = onTheFloor;
        songs[4] = holiday;


    }

    public Song getCurrentSong(int index) {
        return songs[index];
    }

    public int getNextSong(int index) {
        if (index >= songs.length - 1) {
            return index;

        } else {
            return index + 1;
        }
    }


    public int getPrevSong(int index) {
        if (index <= 0) {
            return index;

        } else {
            return index - 1;
        }
    }


    public int searchSongById(String id) {

        for (int i = 0; i < songs.length; i++) {

            Song tempSongs = songs[i];
            if (tempSongs.getId().equals(id)) {
                //we have a match
                return i;
            }

        }
        return -1;
    }


}
