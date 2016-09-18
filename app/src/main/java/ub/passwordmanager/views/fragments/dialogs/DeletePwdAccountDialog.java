package ub.passwordmanager.views.fragments.dialogs;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_PwdAccount;

/**
 * This class is used to Delete a Password Account.
 * Extends the {@link CustomDialog} class, and redefine the :
 * - {@link #getDialog()} : To create the dialog and show it's content
 * - {@link #setDialogAction()} : to set the action to do if the "Save" button is clicked
 * <p/>
 * Created by UcefBen on 30/08/2016.
 */
public class DeletePwdAccountDialog extends CustomDialog {

    // fields for the object password account
    private PwdAccountModel mPwdAccount;

    /**
     * Constructor of this class and calls the "super class".
     *
     * @param activity : the current activity where the dialog will be created.
     */
    public DeletePwdAccountDialog(Activity activity, PwdAccountModel pwdAccount) {
        super(activity, R.layout.delete_account_dialog);
        this.mPwdAccount = pwdAccount;
    }

    /**
     * Create and show the dialog depending on the parameters
     */
    @Override
    public AlertDialog getDialog() {
        super.createDialog();

        // Initialise the fields in the current dialog
        final TextView mMessageTitle = (TextView) getCurrentDialog().findViewById(R.id.label_account_to_delete);

        // set the Dialog Title
        String mDialogTitle = "[" + mPwdAccount.getWebSite() + "]";
        mMessageTitle.setText(mDialogTitle);

        // return an instance of the dialog
        return getCurrentDialog();
    }


    /**
     * Set the Action to do when the "Save Button" is clicked
     */
    @Override
    public Boolean setDialogAction() {
        // Delete from DataBase
        try {
            // return True or False depending on the service result if everything went correctly
            return Service_PwdAccount.getInstance().deleteData(getCurrentActivity(), mPwdAccount);
        } catch (Exception e) {
            Log.e("*Deleting Account *", "[" + e.getMessage() + "]");
            e.printStackTrace();
            return false;
        }
    }

}
