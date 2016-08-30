package ub.passwordmanager.Models;

/**
 * Created by UB on 27/08/2016.
 */
public class PwdAccountModel {

    private String mWebSite;
    private String mEmail;
    private String mPassword;
    private String mLastUpdate;
    private String mOtherInfo;

    public PwdAccountModel() {
        // leave this empty
    }


    public PwdAccountModel(String mWebSite, String mEmail, String mPassword, String mLastUpdate, String otherInfo) {
        this.mWebSite = mWebSite;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mLastUpdate = mLastUpdate;
        this.mOtherInfo = otherInfo;
    }

    public PwdAccountModel(String mWebSite, String mEmail, String mLastUpdate) {
        this.mWebSite = mWebSite;
        this.mEmail = mEmail;
        this.mLastUpdate = mLastUpdate;
    }

    public String getWebSite() {
        return mWebSite;
    }

    public void setmWebSite(String mWebSite) {
        this.mWebSite = mWebSite;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getLastUpdate() {
        return mLastUpdate;
    }

    public void setLastUpdate(String mLastUpdate) {
        this.mLastUpdate = mLastUpdate;
    }

    public String getOtherInfo() {return mOtherInfo;}

    public void setOtherInfo(String mOtherInfo) {this.mOtherInfo = mOtherInfo;}
}