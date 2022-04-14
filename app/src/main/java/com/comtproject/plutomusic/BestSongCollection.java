package com.comtproject.plutomusic;

public class BestSongCollection {
    private Song bestSong[] = new Song[3];

    //songslist is here

    public BestSongCollection() {

        Song badHabits = new Song("ML1", "Bad Habits",
                "Ed Sheeran", "https://p.scdn.co/mp3-preview/0e537207ad0fff8c9e9011735c665d99b9b58d5e?cid=2afe87a64b0042dabf51f37318616965",
                3.85, R.drawable.group_2);

        Song sucker = new Song("ML2", "Sucker",
                "Jones Brothers", "https://p.scdn.co/mp3-preview/4200cbd6f4b2beac1dde20fabd04959b82f1ed8d?cid=2afe87a64b0042dabf51f37318616965",
                3.81, R.drawable.group_211);

        Song reckless = new Song("ML3", "Reckless",
                "Madison Beer", "https://p.scdn.co/mp3-preview/993d3a35b7066c15a57dd2a000a9df95ab4e1fbc?cid=2afe87a64b0042dabf51f37318616965",
                3.39, R.drawable.group_277);


        bestSong[0] = badHabits;
        bestSong[1] = sucker;
        bestSong[2] = reckless;

    }

    public Song getBestCurrentSong(int index) {
        return bestSong[index];
    }

    public int getBestNextSong(int index) {
        if (index >= bestSong.length - 1) {
            return index;

        } else {
            return index + 1;
        }
    }

    public int getBestPrevSong(int index) {
        if (index <= 0) {
            return index;

        } else {
            return index - 1;
        }
    }

    public int searchBestSongById(String id) {

        for (int i = 0; i < bestSong.length; i++) {

            Song tempSongs = bestSong[i];
            if (tempSongs.getId().equals(id)) {
                //we have a match
                return i;
            }

        }
        return -1;
    }


}
