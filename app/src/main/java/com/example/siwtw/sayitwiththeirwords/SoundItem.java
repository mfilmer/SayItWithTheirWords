package com.example.siwtw.sayitwiththeirwords;

/**
 * Created by Matt on 4/2/2016.
 */
public class SoundItem {
    public String iconPath;
    public String displayName;
    public String audioPath;
    public int soundID;
    private boolean hasIconBool;

    public SoundItem() {
        super();
    }

    public SoundItem(String audioPath) {
        super();
        this.audioPath = audioPath;
        this.displayName = audioPath;
        hasIconBool = false;
    }

    public SoundItem(String audioPath, String displayName) {
        super();
        this.audioPath = audioPath;
        if (displayName.equals("")) {
            this.displayName = audioPath;
        }
        else {
            this.displayName = displayName;
        }
        hasIconBool = false;
    }

    public SoundItem(String audioPath, String displayName, String iconPath) {
        super();
        this.audioPath = audioPath;
        if (displayName.equals("")) {
            this.displayName = audioPath;
        }
        else {
            this.displayName = displayName;
        }
        if (iconPath.equals("")) {
            hasIconBool = false;
        }
        else {
            this.iconPath = iconPath;
            hasIconBool = true;
        }
    }

    public boolean hasIcon() {
        return hasIconBool;
    }

}
