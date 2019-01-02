package com.example.android.miwok;

public class Word {
    private String miwok;
    private String english;
    private int resId=IMG_RES;
    private static final int IMG_RES=-1;
    private int audioId;

    public String getEnglish() {
        return english;
    }

    public String getMiwok() {
        return miwok;
    }

    public int getResource(){return resId;}

    public int getAudioId(){return audioId;}

    public Word(String miwok,String english,int resId,int audioId){
        this.english=english;
        this.miwok=miwok;
        this.resId=resId;
        this.audioId=audioId;
    }

    public Word(String miwok,String english,int audioId){
        this.english=english;
        this.miwok=miwok;
        this.audioId=audioId;
    }

    public boolean hasImage(){
        boolean x=resId!=IMG_RES;
        return x;
    }
}
