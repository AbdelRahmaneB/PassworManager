package ub.passwordmanager.Models;

import java.util.Date;

/**
 * This Class represent the data holder for the user account information
 * Created by UcefBen on 28/08/2016.
 */
public class UserAccountModel {

    private int mId;
    private String mRef;
    private String mUsername;
    private String mEmail;
    private String mPassword;
    private String mConfirmationPwd;
    private String mLastLogIn;

    public UserAccountModel() {
    }

    public UserAccountModel(String username, String pwd_email) {
        this.mUsername = username;
        this.mPassword = pwd_email;
    }

    public UserAccountModel(int mId, String mRef, String mUsername, String mEmail, String mPassword, String mLastLogIn) {
        this.mId = mId;
        this.mRef = mRef;
        this.mUsername = mUsername;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mLastLogIn = mLastLogIn;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getRef() {
        return mRef;
    }

    public void setRef(String mRef) {
        this.mRef = mRef;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
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

    public String getLastLogIn() {
        return mLastLogIn;
    }

    public void setLastLogIn(String mLastLogIn) {
        this.mLastLogIn = mLastLogIn;
    }

    public String getConfirmationPwd() {
        return mConfirmationPwd;
    }

    public void setConfirmationPwd(String mConfirmationPwd) {
        this.mConfirmationPwd = mConfirmationPwd;
    }
}
