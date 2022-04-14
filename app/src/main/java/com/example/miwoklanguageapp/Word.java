package com.example.miwoklanguageapp;

public class Word {

    /** Default translation of the word */
    private String mDefaultTranslation;

    /** Miwok translation of the word */
    private String mMiwokTranslation;

    private int mImageResourceId=NO_IMAGE_PROVIDED;

    private int mAudioResourceId;

    private static final int NO_IMAGE_PROVIDED=-1;

    /**
     *
     * @param mDefaultTranslation is the word in a language that the user is familiar to like english
     * @param mMiwokTranslation is the word in the Miwok language
     * @param mImageResourceId is the drawable resource id for the image asset
     * @param mAudioResourceId is the drawable resource id for the audio asset
     */
    public Word(String mDefaultTranslation, String mMiwokTranslation, int mImageResourceId,int mAudioResourceId) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageResourceId = mImageResourceId;
        this.mAudioResourceId=mAudioResourceId;
    }


    /**
     *
     * @param defaultTranslation is the word in a language that the user is familiar to like english
     * @param miwokTranslation is the word in the Miwok language
     * @param mAudioResourceId is the drawable resource id for the audio asset
     */
    public Word(String defaultTranslation, String miwokTranslation,int mAudioResourceId){
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        this.mAudioResourceId=mAudioResourceId;

    }

    /**
     * Get the default translation of the word.
     * @return the default language translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     * @return the miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /**
     * @return the image resource id of the word.
     */
    public int getmImageResourceId(){
        return mImageResourceId;
    }

    /**
     * @return whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /**
     * @return the audio resource id of the word.
     */
    public int getmAudioResourceId(){
        return mAudioResourceId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }
}
