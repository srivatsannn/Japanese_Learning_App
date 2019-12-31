package com.example.miwok;

import android.media.Image;
import android.media.MediaPlayer;

public class word {
    /** Default translation for the word */
    private String mDefaultTranslation;

    /** Miwok translation for the word */
    private String mMiwokTranslation;
    private int mimage=noimage;
    private static final int noimage=-1;
    private int mmedia;

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     */
    public word(String defaultTranslation, String miwokTranslation,int media) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mmedia=media;


    }
    public word(String defaultTranslation, String miwokTranslation,int imageid,int media) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mimage=imageid;
        mmedia=media;

    }

    public int getMmedia(){
        return mmedia;
    }
    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }
    public  int getMimage(){
        return mimage;
    }
    public boolean hasimage(){
       return mimage!=noimage;

    }

}
