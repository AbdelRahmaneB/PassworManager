package ub.passwordmanager.views.fragments.dialogs;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_PwdAccount;
import ub.passwordmanager.tools.PwdGenerator.PwdGenerator;

/**
 * This class is used to edit am existing Password Account.
 * Extends the {@link CustomDialog} class, and redefine the :
 * - {@link #getDialog()} : To create the dialog and show it's content
 * - {@link #setDialogAction()} : to set the action to do if the "Save" button is clicked
 * <p/>
 * Created by UB on 30/08/2016.
 */
public class EditPwdAccountDialog extends CustomDialog {

    // Dialog fields
    private EditText mWebSite;
    private EditText mEmail;
    private EditText mPwd;
    private EditText mOther;
    private PwdAccountModel mPwdAccount;

    /**
     * Constructor of this class and initialise the "super class".
     *
     * @param activity : the current activity where the dialog will be created.
     */
    public EditPwdAccountDialog(Activity activity, PwdAccountModel pwdAccount) {
        super(activity, R.layout.add_edit_dialog);
        this.mPwdAccount = pwdAccount;
    }

    /**
     * Create and show the dialog depending on the parameters
     * This method uses the :
     * - {@link #initialiseTheFields()} : to initial all the fields in the dialog
     * - {@link #fillTheFields()} : to fill the dialog with the data of the selected item
     */
    @Override
    public AlertDialog getDialog() {
        super.createDialog();

        // initialise fields
        initialiseTheFields();

        // fill the fields
        fillTheFields();

        final ImageView mPwdGen = (ImageView) getCurrentDialog().findViewById(R.id.iv_pwd_gen_account);
        mPwdGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPwd.setText(PwdGenerator.generatePassword(getCurrentActivity()));
            }
        });

        return getCurrentDialog();
    }


    /**
     * Set the Action to do when the "Save Button" is clicked - Edit
     */
    @Override
    public Boolean setDialogAction() {
        if (isWebSiteEmpty(mWebSite.getText().toString())
                & isUsernameEmpty(mEmail.getText().toString())
                & isPasswordEmpty(mPwd.getText().toString())
                ) {

            // Create object
            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(Calendar.getInstance().getTime());

            String otherInfo = "" + mOther.getText().toString();

            PwdAccountModel mPwdAccount = new PwdAccountModel(
                   Integer.parseInt(mWebSite.getTag().toString()),
                    mWebSite.getText().toString(),
                    mEmail.getText().toString(),
                    mPwd.getText().toString(),
                    otherInfo,
                    date
            );

            // save into database
            try {
                return Service_PwdAccount.saveModifiedData(getCurrentActivity(), mPwdAccount);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * Initialise the fields of the edit dialog
     */
    private void initialiseTheFields() {
        // Initialise the fields in the current dialog
        this.mWebSite = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_siteWeb);
        this.mEmail = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_email);
        this.mPwd = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_password);
        this.mOther = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_otherInfo);

        final TextView mTitle = (TextView) getCurrentDialog().findViewById(R.id.label_Dialog_title);
        mTitle.setText(getCurrentActivity().getResources().getString(R.string.home_add_edit_ETitle));

        final ImageView mImage = (ImageView) getCurrentDialog().findViewById(R.id.ic_dialog_add_edit);
        mImage.setImageResource(R.drawable.ic_edit_white);

    }

    /**
     * Fill the fields of the edit Dialog
     */
    private void fillTheFields() {

        // Website
        this.mWebSite.setText(mPwdAccount.getWebSite());

        // Id
        this.mWebSite.setTag(mPwdAccount.getId());

        // Username/Email
        this.mEmail.setText(mPwdAccount.getEmail());

        // Password
        this.mPwd.setText(mPwdAccount.getPassword());

        // OtherInfo
        this.mOther.setText(mPwdAccount.getOtherInfo());

    }


    /**
     * Test the Website field
     */
    private Boolean isWebSiteEmpty(String value) {
        TextInputLayout mWebSite = (TextInputLayout) getCurrentDialog().findViewById(R.id.home_ae_input_siteWeb);
        if (TextUtils.isEmpty(value)) {
            mWebSite.setError(getCurrentActivity()
                    .getResources().getString(R.string.empty_website_error_home));
            return false;
        } else {
            mWebSite.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * Test the username/email field
     */
    private Boolean isUsernameEmpty(String value) {
        TextInputLayout mUsername = (TextInputLayout) getCurrentDialog().findViewById(R.id.home_ae_input_email);
        if (TextUtils.isEmpty(value)) {
            mUsername.setError(getCurrentActivity()
                    .getResources().getString(R.string.empty_username_error_home));
            return false;
        } else {
            mUsername.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * Test the password field
     */
    private Boolean isPasswordEmpty(String value) {
        TextInputLayout mPassword = (TextInputLayout) getCurrentDialog().findViewById(R.id.home_ae_input_password);
        if (TextUtils.isEmpty(value)) {
            mPassword.setError(getCurrentActivity()
                    .getResources().getString(R.string.empty_password_error_home));
            return false;
        } else {
            mPassword.setErrorEnabled(false);
            return true;
        }
    }

}
