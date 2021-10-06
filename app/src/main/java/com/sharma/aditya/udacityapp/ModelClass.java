package com.sharma.aditya.udacityapp;

public class ModelClass {

    String Translated, Main;
    Integer Music;
    Integer ImageResource = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * This Constructor is for Phrases Activity
     *
     * @param Translated Initializing the translated text
     * @param Main       Initializing the Main text
     */

    public ModelClass(String Main, String Translated, Integer ImageResource, Integer Music) {
        this.Main = Main;
        this.Translated = Translated;
        this.ImageResource = ImageResource;
        this.Music = Music;
    }

    public ModelClass (String Main, String Translated, Integer Music) {
        this.Main = Main;
        this.Translated = Translated;
        this.Music = Music;
    }

    public String getTranslated() {
        return Translated;
    }

    public String getMain() {
        return Main;
    }


    public Integer getMusic() {
        return Music;
    }

    public Integer getImageResource() {
        return ImageResource;
    }

    /**
     * Returns whether the image is provided or not in the constructor
     * @return
     */
    public boolean hasImage() { return ImageResource != NO_IMAGE_PROVIDED; }

    @Override
    public String toString() {
        return "Word{" +
                "Main ='" + Main + '\'' +
                ", Translation='" + Translated + '\'' +
                ", mAudioResourceId=" + Music +
                ", mImageResourceId=" + ImageResource +
                '}';
    }
}
