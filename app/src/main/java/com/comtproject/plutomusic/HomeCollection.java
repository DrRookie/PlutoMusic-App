package com.comtproject.plutomusic;

public class HomeCollection {
    //another songlist activity
    private Song mustListen[] = new Song[3];

    public HomeCollection() {
        Song shapeofyou = new Song("ML1","Shape of You",
                "Ed Sheeran","https://p.scdn.co/mp3-preview/84462d8e1e4d0f9e5ccd06f0da390f65843774a2?cid=2afe87a64b0042dabf51f37318616965",
                3.85,R.drawable.group_7233);

        Song motherNature = new Song("ML2","Mother Nature",
                "Liza","https://p.scdn.co/mp3-preview/6fb7240cdeffe3f521cecc89f4507b9d5c5c5fdb?cid=2afe87a64b0042dabf51f37318616965",
                3.81,R.drawable.group_7231);

        Song yummy = new Song("ML3","Yummy",
                "Justin Beiber","https://p.scdn.co/mp3-preview/2f5d14d8b6db14b6324a0dc194d944b9bcba4dfa?cid=2afe87a64b0042dabf51f37318616965",
                3.39,R.drawable.group_7229);



        mustListen[0] = shapeofyou;
        mustListen[1] = motherNature;
        mustListen[2] = yummy;

    }

    public Song getHomeCurrentSong(int index) {
        return mustListen[index];
    }

    public int getHomeNextSong(int index) {
        if(index >= mustListen.length -1) {
            return index;

        }else {
            return index+1;
        }
    }
    public int getHomePrevSong(int index) {
        if(index <= 0 ) {
            return index ;

        } else  {
            return index-1;
        }
    }

    public int searchHomeSongById(String id) {

        for (int i = 0; i < mustListen.length; i++) {

            Song tempSongs = mustListen[i];
            if(tempSongs.getId().equals(id)) {
                //we have a match
                return i;
            }

        }
        return -1;
    }



}
