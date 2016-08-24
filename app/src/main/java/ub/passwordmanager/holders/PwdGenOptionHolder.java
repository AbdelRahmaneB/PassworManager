package ub.passwordmanager.holders;

import java.util.List;

/** Class Description
 * Created by UB on 24/08/2016.
 */
public class PwdGenOptionHolder {

    // The title of the option menu
    private String mOptTitle;

    // This field for the description
    private String mOptDescription;

    // Note that this field gonna hold the values (0:UnChecked; 1:Checked)
    private int mIsChecked;

    public PwdGenOptionHolder(String optTitle, String optDescription, int isChecked) {
        this.mOptTitle = optTitle;
        this.mOptDescription = optDescription;
        this.mIsChecked = isChecked;
    }

    public String getOptTitle() {
        return mOptTitle;
    }

    public String getOptDescription() {
        return mOptDescription;
    }

    public int IsChecked() {
        return mIsChecked;
    }

}
