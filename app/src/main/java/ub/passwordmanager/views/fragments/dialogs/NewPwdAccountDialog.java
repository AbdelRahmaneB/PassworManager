package ub.passwordmanager.views.fragments.dialogs;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_ImportExportDataBase;
import ub.passwordmanager.Services.Service_PwdAccount;
import ub.passwordmanager.tools.PwdGenerator.PwdGenerator;

/**
 * This class is used to create a new Password Account.
 * Extends the {@link CustomDialog} class, and redefine the :
 * - {@link #getDialog()} : To create the dialog and show it's content
 * - {@link #setDialogAction()} : to set the action to do if the "Save" button is clicked
 * <p>
 * Created by UcefBen on 30/08/2016.
 */
public class NewPwdAccountDialog extends CustomDialog {

    // This global object is needed for after (Search for a better idea)
    private PwdAccountModel mPwdAccountToSend;

    /**
     * Constructor of this class and initialise the "super class".
     *
     * @param activity : the current activity where the dialog will be created.
     */
    public NewPwdAccountDialog(Activity activity) {
        super(activity, R.layout.add_edit_dialog);
    }

    /**
     * Create and show the dialog depending on the parameters
     */
    @Override
    public AlertDialog getDialog() {
        super.createDialog();
        final EditText mPwd = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_password);
        final ImageView mPwdGen = (ImageView) getCurrentDialog().findViewById(R.id.iv_pwd_gen_account);
        mPwdGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPwd.setText(PwdGenerator.getInstance().generatePassword(getCurrentActivity()));
            }
        });
        return getCurrentDialog();
    }


    /**
     * Set the Action when the "Save Button" is clicked
     */
    @Override
    public Boolean setDialogAction() {
        // Initialise the fields in the current dialog
        final EditText mWebSite = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_siteWeb);
        final EditText mEmail = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_email);
        final EditText mPwd = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_password);
        final EditText mOther = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_otherInfo);

        // check if the fields are correctly filled
        if (isWebSiteEmpty(mWebSite.getText().toString())
                & isUsernameEmpty(mEmail.getText().toString())
                & isPasswordEmpty(mPwd.getText().toString())
                ) {

            // Create date object
            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(Calendar.getInstance().getTime());

            // in case if the "other information" field is empty
            String otherInfo = "" + mOther.getText().toString();

            // Initialise the password account object
            PwdAccountModel mPwdAccount = new PwdAccountModel(
                    mWebSite.getText().toString(),
                    mEmail.getText().toString(),
                    mPwd.getText().toString(),
                    otherInfo,
                    date
            );

            // This global object is needed for after (Search for a better idea)
            mPwdAccountToSend = new PwdAccountModel(
                    mWebSite.getText().toString(),
                    mEmail.getText().toString(),
                    mPwd.getText().toString(),
                    otherInfo,
                    date
            );

            // save into database
            try {
                return Service_PwdAccount.getInstance().saveNewData(getCurrentActivity(), mPwdAccount);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * set the Action for the dialog.
     *
     * @return the instance of the object
     */
    public PwdAccountModel setDialogActionForNew() {
        if (setDialogAction()) {
            return mPwdAccountToSend;
        }
        return null;
    }

    /********************* Private Functions *****************************/
    //*******************************************************************//

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
     * Test the Username/Email field
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
     * Test the Password field
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
