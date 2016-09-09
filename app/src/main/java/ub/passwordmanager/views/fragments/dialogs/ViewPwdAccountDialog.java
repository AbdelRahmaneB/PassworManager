package ub.passwordmanager.views.fragments.dialogs;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.R;

/**
 * This class is used to View the details of a Password Account.
 * Extends the {@link CustomDialog} class, and redefine the :
 * - {@link #getDialog()} : To create the dialog and show it's content
 * - {@link #setDialogAction()} : to set the action to do if the "Save" button is clicked
 * <p/>
 * Created by UB on 05/09/2016.
 */
public class ViewPwdAccountDialog extends CustomDialog {

    /**
     * Constructor of this class and initialise the "super class".
     *
     * @param activity : the current activity where the dialog will be created.
     */
    public ViewPwdAccountDialog(Activity activity) {
        super(activity, R.layout.consult_account_dialog);
    }

    /**
     * Create and show the dialog depending on the parameters
     */
    @Override
    public void getDialog() {
        super.createDialog();

        // ToDo : Remove this black and replace it with the correct action
        // Initialise the fields in the current dialog
        final EditText mWebSite = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_siteWeb);
        final EditText mEmail = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_email);
        final EditText mPwd = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_password);
        final EditText mOther = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_otherInfo);

        mWebSite.setText("MyWebSite");
        mEmail.setText("MyUsername");
        mPwd.setText("MyPassword");
        mOther.setText("MySuperDetails");
    }


    /**
     * Set the Action to do when the "Save Button" is clicked
     */
    @Override
    protected void setDialogAction() {
        // Initialise the fields in the current dialog
        final EditText mWebSite = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_siteWeb);
        final EditText mEmail = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_email);
        final EditText mPwd = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_password);
        final EditText mOther = (EditText) getCurrentDialog().findViewById(R.id.home_ae_t_otherInfo);

        // Get the current Date
        DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
        Calendar mCalender = Calendar.getInstance();

        // ToDo : Test on the object if there are not empty
        // Initialise the object so we can send it to the persistence class
        try {
            PwdAccountModel mPwdAcc = new PwdAccountModel(mWebSite.getText().toString(),
                    mEmail.getText().toString(), mPwd.getText().toString(),
                    mCalender.getTime(), mOther.getText().toString());

            // ToDo : Add the code to save the new object in the DataBase

        } catch (NullPointerException ex) {
            Log.e("ViewAccountPwd : ", ex.getStackTrace().toString());
        }

        // Notify the user that everything is good :)
        Toast.makeText(getCurrentActivity(), "View Dialog : " + mDateFormat.format(mCalender.getTime()), Toast.LENGTH_SHORT).show();
    }
}
