package ub.passwordmanager.views.fragments.dialogs;

import android.app.Activity;
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

/**
 * This class is used to Delete a Password Account.
 * Extends the {@link CustomDialog} class, and redefine the :
 * - {@link #getDialog()} : To create the dialog and show it's content
 * - {@link #setDialogAction()} : to set the action to do if the "Save" button is clicked
 * <p/>
 * Created by UB on 30/08/2016.
 */
public class DeletePwdAccountDialog extends CustomDialog {


    /**
     * Constructor of this class and initialise the "super class".
     *
     * @param activity : the current activity where the dialog will be created.
     */
    public DeletePwdAccountDialog(Activity activity) {
        super(activity, R.layout.delete_account_dialog);
    }

    /**
     * Create and show the dialog depending on the parameters
     */
    @Override
    public void getDialog() {
        super.createDialog();

        // Initialise the fields in the current dialog
        final TextView mMessageTitle = (TextView) getCurrentDialog().findViewById(R.id.label_Warning_delete);
        String str = getCurrentActivity().getResources().getString(R.string.home_add_edit_DMessage)
                + "[UserNameAccount]"
                + getCurrentActivity().getResources().getString(R.string.home_add_edit_DMessage_suite);

        mMessageTitle.setText(str);
    }


    /**
     * Set the Action to do when the "Save Button" is clicked
     */
    @Override
    protected void setDialogAction() {


        // ToDo : Test on the object if there are not empty
        // Initialise the object so we can send it to the persistence class
        try {

            //Set the ID of the object to delete
//            PwdAccountModel mPwdAcc = new PwdAccountModel(mWebSite.getText().toString());

            // ToDo : Add the code to save the new object in the DataBase

        } catch (NullPointerException ex) {
            Log.e("DeleteAccountPwd : ", ex.getStackTrace().toString());
        }

        // Notify the user that everything is good :)
        Toast.makeText(getCurrentActivity(), "Delete Dialog : ", Toast.LENGTH_SHORT).show();
    }

}
