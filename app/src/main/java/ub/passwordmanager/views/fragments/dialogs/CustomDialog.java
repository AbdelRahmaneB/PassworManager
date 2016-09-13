package ub.passwordmanager.views.fragments.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Window;

import ub.passwordmanager.R;

/**
 * This is an abstract class that allow us to create a Custom Dialog.
 * Up until now we have the following Dialog Classes :
 * - Dialog for Adding a new Password Account.
 * - Dialog for Editing an existing Password Account.
 * - Dialog for Consulting the selected Password Account.
 * - Dialog for Choosing a new Theme.
 * - Dialog for Choosing a new language.
 * <p/>
 * This class have two Methods that needs to be implemented by the
 * sub-classes : {@link #getDialog()} and {@link #setDialogAction()}
 * <p/>
 * Created by UB on 30/08/2016.
 */
public abstract class CustomDialog {

    // Contain the current activity where the dialog is needed
    private Activity mCurrentActivity;

    // Contain the current used dialog
    private AlertDialog mCurrentDialog;

    // Contain the id of the layout that is used to build the current dialog
    private int mDialogLayout;
    //---------------------------------------------------

    /**
     * The constructor of the this abstract class.
     * Used to initialise the {@link #mCurrentActivity} and {@link #mDialogLayout}.
     *
     * @param activity     : The current Activity
     * @param dialogLayout : The layout to use in our dialog
     */
    public CustomDialog(Activity activity, int dialogLayout) {
        this.mCurrentActivity = activity;
        this.mDialogLayout = dialogLayout;
    }


    /**
     * The role of this function is to create a new dialog depending on the
     * different parameters (Activity, Layout),
     * and uses the function {@link #setDialogAction()}
     */
    protected AlertDialog createDialog() {

        // Create the instance of the AlertDialog builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.mCurrentActivity);

        // Inflate the view of the desired dialog
        builder.setView(this.mDialogLayout);

        // Set the action for the Save and Cancel button of the dialog
        builder.setPositiveButton(this.mCurrentActivity
                .getResources().getText(R.string.dialog_button_save)
                .toString(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /**
                 * Do nothing here because we override this button later to change the close behaviour.
                 * However, we still need this because on older versions of Android unless we
                 * pass a handler the button doesn't get instantiated.
                 * http://stackoverflow.com/questions/27345584/how-to-prevent-alertdialog-to-close
                 */
                //setDialogAction();
            }
        });

        builder.setNegativeButton(this.mCurrentActivity
                        .getResources().getText(R.string.dialog_button_cancel),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        // User Cancelling action depending on the used Sub-Class
                        dialog.cancel();
                    }
                });

        // Create the Dialog
        this.mCurrentDialog = builder.create();

        // Indicate that the dialog will be without the build in Title
        this.mCurrentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // The user can cancel the dialog via the BackPress button
        this.mCurrentDialog.setCancelable(true);

        // The Dialog can't be canceled if the user click on the outside of the dialog
        this.mCurrentDialog.setCanceledOnTouchOutside(false);

        // Finally show the dialog to the user
        this.mCurrentDialog.show();

        return this.mCurrentDialog;
    }

    /**
     * This is an abstract method that will be implemented by the Sub-Classes.
     * Depending on the Sub-Class, it will initialise the {@link #createDialog()}
     * with the desired parameters.
     */
    public abstract AlertDialog getDialog();

    /**
     * This abstract method will allow to implement the action to do depending on
     * the calling Sub-Class.
     */
    public abstract Boolean setDialogAction();

    /**
     * @return The current used activity. Mostly used in the Sub-Classes
     */
    protected Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    /**
     * @return The instance of the current Dialog
     */
    protected AlertDialog getCurrentDialog() {
        return this.mCurrentDialog;
    }
}
