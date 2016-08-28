package ub.passwordmanager.Models;

import java.util.Date;

/**
 * Created by UB on 28/08/2016.
 */
public class UserAccountModel {

    private String mRef;
    private String mUsername;
    private String mEmail;
    private String mPassword;
    private Date mLastLogIn;

    public UserAccountModel() {
    }

    public UserAccountModel(String username, String pwd_email) {
        this.mUsername = username;
        this.mPassword = pwd_email;
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

    public Date getLastLogIn() {
        return mLastLogIn;
    }

    public void setLastLogIn(Date mLastLogIn) {
        this.mLastLogIn = mLastLogIn;
    }
}
