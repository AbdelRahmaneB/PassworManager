package ub.passwordmanager.views.fragments.dialogs;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.R;

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
    private int mSelectedItem;

    /**
     * Constructor of this class and initialise the "super class".
     *
     * @param activity : the current activity where the dialog will be created.
     */
    public EditPwdAccountDialog(Activity activity, int selectedItem) {
        super(activity, R.layout.add_edit_dialog);
        this.mSelectedItem = selectedItem;
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

        return getCurrentDialog();
    }


    /**
     * Set the Action to do when the "Save Button" is clicked - Edit
     */
    @Override
    public Boolean setDialogAction() {

        // Get the current Date
        DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
        Calendar mCalender = Calendar.getInstance();

        // ToDo : Test on the object if there are not empty
        // Initialise the object so we can send it to the persistence class
        try {
            PwdAccountModel mPwdAcc = new PwdAccountModel(mWebSite.getText().toString(),
                    mEmail.getText().toString(), mPwd.getText().toString(),
                    new SimpleDateFormat("dd/MM/yyyy").format(mCalender.getTime()),
                    mOther.getText().toString());


            // ToDo : Add the code to save the changes of the object in the DataBase

        } catch (NullPointerException ex) {
            Log.e("EditAccountPwd : ", ex.getStackTrace().toString());
        }

        // Notify the user that everything is good :)
        Toast.makeText(getCurrentActivity(), "Edit Dialog : " + mDateFormat.format(mCalender.getTime()), Toast.LENGTH_SHORT).show();
        return false;
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
        // ToDo : get the data from the DataBase
        this.mWebSite.setText("MyWebSite");

        // ToDo : Affect the data to the fields
    }
}
