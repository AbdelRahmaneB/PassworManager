package ub.passwordmanager.views.fragments.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Window;

import ub.passwordmanager.R;

/**
 * ToDo : Transform this Class into an abstract one and use The Strategy Pattern
 *
 * Created by UB on 29/08/2016.
 */
public class AddEditDialog {

    /**
     * Our Instance for the AppConfig using Singleton
     */
    private static AddEditDialog Instance;

    // The instance of our dialog
    private AlertDialog mDialog;

    //------------------------------------------------------

    /**
     * Restrict the access to the Constructor
     */
    private AddEditDialog() {
    }

    /**
     * This method allow us to be sure that there will be only one instance of this class
     *
     * @return the instance of this class
     */
    public static AddEditDialog getInstance() {
        if (Instance == null) {
            synchronized (AddEditDialog.class) {
                Instance = new AddEditDialog();
            }
        }
        return Instance;
    }


    public void getAddDialog(Activity activity) {
        createDialog(activity, 1);
    }

    public void getEditDialog(Activity activity) {

    }

    public void getConsultDialog(Activity activity) {

    }

    public void getDeleteDialog(Activity activity) {

    }


    /**
     * This function Create an instance of the a AlertDialog.
     * For the ID of the dialog we find :
     * - Number 1 : For the Add dialog
     * - Number 2 : For the Edit dialog
     * - Number 3 : For the Consult dialog
     * - Number 4 : For the Delete dialog
     * - Number 5 : For the Language dialog
     * - Number 6 : for the Theme dialog
     *
     * @param activity : The used activity to call the dialog
     * @param idDialog : The Id of the dialog that we want
     */
    private void createDialog(Activity activity, int idDialog) {

        // Create the instance of the builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // Inflate the desired dialog depending on the IdDialog
        builder.setView(getDialogLayout(idDialog));

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User Ok the dialog
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });

        // Create the Dialog
        mDialog = builder.create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(R.layout.add_edit_dialog);
        mDialog.show();
    }


    /**
     * This function allow to choose the layout to use depending on the ID
     *
     * @param idDialog : the requested dialog.
     * @return : the real id of the requested dialog
     */
    private int getDialogLayout(int idDialog) {
        switch (idDialog) {
            case 1:
                return R.layout.add_edit_dialog;

            default:
                return R.layout.add_edit_dialog;
        }
    }

    private void getDialogAction(int idDialog, AlertDialog dialog){

        switch (idDialog) {
            case 1:
                break;

            default:
                break;
        }

    }


}
