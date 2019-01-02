package themollo.app.mollo.lullaby;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alex on 2018. 7. 19..
 */

public class LullabyModel {

    String lullabyTitle;
    String lullabyPlayTime;
    boolean isSelected;
    int rawFileName;

    public LullabyModel() {
    }

    public LullabyModel(String lullabyTitle, String lullabyPlayTime, boolean isSelected) {
        this.lullabyTitle = lullabyTitle;
        this.lullabyPlayTime = lullabyPlayTime;
        this.isSelected = isSelected;
    }

    public LullabyModel(String lullabyTitle, String lullabyPlayTime, boolean isSelected, int rawFileName) {
        this.lullabyTitle = lullabyTitle;
        this.lullabyPlayTime = lullabyPlayTime;
        this.isSelected = isSelected;
        this.rawFileName = rawFileName;
    }

    public int getRawFileName() {
        return rawFileName;
    }

    public void setRawFileName(int rawFileName) {
        this.rawFileName = rawFileName;
    }

    public String getLullabyTitle() {
        return lullabyTitle;
    }

    public void setLullabyTitle(String lullabyTitle) {
        this.lullabyTitle = lullabyTitle;
    }

    public String getLullabyPlayTime() {
        return lullabyPlayTime;
    }

    public void setLullabyPlayTime(String lullabyPlayTime) {
        this.lullabyPlayTime = lullabyPlayTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
