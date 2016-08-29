package ub.passwordmanager.Models;

/**
 * Created by UB on 27/08/2016.
 */
public class PwdAccountModel {

    private String mWebSite;
    private String mEmail;
    private String mPassword;
    private String mLastUpdate;

    public PwdAccountModel() {
        // leave this empty
    }


    public PwdAccountModel(String mWebSite, String mEmail, String mPassword, String mLastUpdate) {
        this.mWebSite = mWebSite;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mLastUpdate = mLastUpdate;
    }

    public PwdAccountModel(String mWebSite, String mEmail, String mLastUpdate) {
        this.mWebSite = mWebSite;
        this.mEmail = mEmail;
        this.mLastUpdate = mLastUpdate;
    }

    public String getmWebSite() {
        return mWebSite;
    }

    public void setmWebSite(String mWebSite) {
        this.mWebSite = mWebSite;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmLastUpdate() {
        return mLastUpdate;
    }

    public void setmLastUpdate(String mLastUpdate) {
        this.mLastUpdate = mLastUpdate;
    }
}
