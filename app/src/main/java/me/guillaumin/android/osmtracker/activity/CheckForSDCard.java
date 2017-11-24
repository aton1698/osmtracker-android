package me.guillaumin.android.osmtracker.activity;
import android.os.Environment;

/**
 * Created by brandon on 23/11/17.
 */

public class CheckForSDCard {
    public boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}
