package ub.passwordmanager.views.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_PwdAccount;
import ub.passwordmanager.appConfig.AppConfig;
import ub.passwordmanager.tools.dataEncryption.DataEncryption;
import ub.passwordmanager.tools.drawableGenerator.ColorGenerator;
import ub.passwordmanager.tools.drawableGenerator.TextDrawable;
import ub.passwordmanager.views.fragments.dialogs.CustomDialog;
import ub.passwordmanager.views.fragments.dialogs.DeletePwdAccountDialog;
import ub.passwordmanager.views.fragments.dialogs.EditPwdAccountDialog;
import ub.passwordmanager.views.fragments.dialogs.NewPwdAccountDialog;
import ub.passwordmanager.views.fragments.dialogs.ViewPwdAccountDialog;

/**
 * Description :
 * This class represent the adapter for the cardView in the "Home fragment".
 * it's used to show the account information : (Website, email, last update).
 * This class contain the Data holder, the listener.
 * <p>
 * Created by UcefBen on 25/08/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {

    /**
     * 1. List of DataObject that represent our CardView
     * 2. Our Adapter listener
     * 3. The Current Activity
     */
    private static List<PwdAccountModel> mDataSet;
    private static MyClickListener myClickListener;
    protected static Activity mActivity;
    /** *********************************************************************** **/

    /**
     * Adding the AdapterListener
     *
     * @param mClickListener : Instance of the listener
     */
    public void setOnItemClickListener(MyClickListener mClickListener) {
        myClickListener = mClickListener;
    }

    /**
     * Adding the The Data to the adapter and get the current activity
     */
    public MyRecyclerViewAdapter(Activity activity) {
        mActivity = activity;
        refreshDataSet();
    }

    /**
     * Creating and initialising our view of the Adapter
     *
     * @param parent   : parent
     * @param viewType : the type of the view
     * @return a DataObjectHolder
     */
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_cardview_row, parent, false);

        return new DataObjectHolder(view, mActivity);
    }

    /**
     * Function that get the Data from DataBase
     */
    private static void refreshDataSet() {
        try {
            if (mDataSet != null)
                mDataSet.clear();
            mDataSet = Service_PwdAccount.getInstance().getAllAccounts(mActivity.getBaseContext(),
                    AppConfig.getInstance().getCurrentPassword());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Binding between the DataHolder and the view
     *
     * @param holder   : The DataHolder that will contain our data.
     * @param position : The index of our items in the "mDataSet".
     */
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        holder.hSiteWeb.setText(mDataSet.get(position).getWebSite());
        holder.hLastUpdate.setText(mDataSet.get(position).getLastUpdate());
        holder.hEmailAddress.setText(mDataSet.get(position).getEmail());

        setIconAccount(holder);

        holder.h_bt_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the deleting Dialog
                configureDeleteDialog(mActivity, holder);
                holder.mHideShowButtons(true);
            }
        });

        holder.h_bt_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureEditDialog(mActivity, holder);
                holder.mHideShowButtons(true);

            }
        });
    }

    /**
     * Function to add a new object to the CardView in a specific position
     */
    public void addItem() {
        configureAddDialog(mActivity);
    }

    /**
     * Function to edit an object from the cardView in a specific position
     *
     * @param index : The position of the object that we want to edit
     */
    public void itemChanged(int index) {
        notifyItemChanged(index);
    }

    /**
     * Function to delete an object from the CardView
     *
     * @param index : The position of the object that we want to delete
     */
    public void deleteItem(int index) {
        mDataSet.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * @return : The number of element in the DataSet
     */
    @Override
    public int getItemCount() {
        return (mDataSet != null) ? mDataSet.size() : -1;
    }

    /**
     * Function to build the icon for an account based on his first char
     */
    private void setIconAccount(final DataObjectHolder holder) {
        ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
        TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder().roundRect(5);
        TextDrawable drawable = mDrawableBuilder.build(
                String.valueOf(Character.toUpperCase(holder.hSiteWeb.getText().toString().charAt(0))),
                mColorGenerator.getColor(holder.hSiteWeb.getText().toString()));
        holder.h_icon.setImageDrawable(drawable);
        holder.h_icon.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * Function to configure the addition dialog
     */
    private void configureAddDialog(final Activity holderActivity) {
        // Create the Dialog to add a new Password Account
        final CustomDialog myDialog = new NewPwdAccountDialog(holderActivity);
        final AlertDialog dialog = myDialog.getDialog();

        // set the action for the "save button"
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PwdAccountModel mPwd = myDialog.setDialogActionForNew();
                if (mPwd != null) {
                    mDataSet.add(getItemCount(), mPwd);
                    notifyItemInserted(getItemCount());
                    mActivity.recreate();
                    dialog.dismiss();
                }
            }
        });
        // Hide the delete button
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setVisibility(View.GONE);
    }

    /**
     * Function to configure the View Data Dialog
     */
    private void configureViewDialog(final Activity holderActivity, final DataObjectHolder holder) {

        // Create and show the Dialog to view the data
        final CustomDialog myDialog = new ViewPwdAccountDialog(holderActivity,
                mDataSet.get(holder.getAdapterPosition()));
        final AlertDialog dialog = myDialog.getDialog();

        // Set the text and the new action for the positive button
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setText(holderActivity
                .getResources().getText(R.string.dialog_button_edit));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the current Dialog
                dialog.dismiss();

                // Open the Editing Dialog
                configureEditDialog(holderActivity, holder);
            }
        });

        // Center the negative button
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 180, 0);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setLayoutParams(params);


        // Override the Neutral Button
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the current Dialog
                dialog.dismiss();

                // Open the deleting Dialog
                configureDeleteDialog(holderActivity, holder);
            }
        });
    }

    /**
     * Function to configure the Editing Dialog
     */
    private void configureEditDialog(final Activity holderActivity, final DataObjectHolder holder) {

        // Create and show the Dialog to view the data
        final CustomDialog myDialog = new EditPwdAccountDialog(holderActivity,
                mDataSet.get(holder.getAdapterPosition()));
        final AlertDialog dialog = myDialog.getDialog();

        // Set the text and the new action for the positive button
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setText(holderActivity
                .getResources().getText(R.string.dialog_button_save));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the changes into DataBase
                if (myDialog.setDialogAction()) {
                    refreshDataSet();
                    itemChanged(holder.getAdapterPosition());
                    dialog.dismiss(); // Close the current Dialog
                }
            }
        });

        // Override the Neutral Button
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setVisibility(View.GONE);
    }

    /**
     * Function to configure the Deleting Dialog
     */
    private void configureDeleteDialog(final Activity holderActivity, final DataObjectHolder holder) {

        // Create and show the Dialog to delete the data
        final CustomDialog myDialog = new DeletePwdAccountDialog(holderActivity,
                mDataSet.get(holder.getAdapterPosition()));
        final AlertDialog dialog = myDialog.getDialog();

        // Set the text and the new action for the positive button
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setText(holderActivity
                .getResources().getText(R.string.dialog_button_delete));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the changes into DataBase
                if (myDialog.setDialogAction()) {
                    deleteItem(holder.getAdapterPosition());
                    dialog.dismiss(); // Close the current Dialog
                }
            }
        });

        // Override the Neutral Button
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setVisibility(View.GONE);
    }


    /** *********************************************************************** */
    //**************************************************************************//

    /**
     * Our DataHolder for the Adapter
     * In this class we add all the required listener that we want to use
     */
    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener,
            View.OnLongClickListener {

        /**
         * Those fields represent the object in the view
         */
        TextView hSiteWeb;
        TextView hLastUpdate;
        TextView hEmailAddress;
        ImageView h_bt_Delete;
        ImageView h_bt_Edit;
        ImageView h_icon;
        private Activity mHolderActivity;

        /**
         * Constructor of The DataHolder.
         * Here we initialise the Holder fields and
         * adding a listener to our current CardView
         *
         * @param itemView : The current View
         */
        public DataObjectHolder(View itemView, Activity holderActivity) {
            super(itemView);

            // Initialise the View objects
            hSiteWeb = (TextView) itemView.findViewById(R.id.l_SiteWeb);
            hLastUpdate = (TextView) itemView.findViewById(R.id.l_LastUpdate);
            hEmailAddress = (TextView) itemView.findViewById(R.id.l_EmailAddress);
            h_bt_Delete = (ImageView) itemView.findViewById(R.id.bt_deleteAccount);
            h_bt_Edit = (ImageView) itemView.findViewById(R.id.bt_editAccount);
            h_icon = (ImageView) itemView.findViewById(R.id.account_icon);

            // Add the listeners to our view
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            mHolderActivity = holderActivity;
        }

        public void mHideShowButtons(Boolean hideShow) {
            if (hideShow) {
                h_bt_Delete.setVisibility(View.INVISIBLE);
                h_bt_Edit.setVisibility(View.INVISIBLE);
            } else {
                h_bt_Delete.setVisibility(View.VISIBLE);
                h_bt_Edit.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onClick(View v) {
            configureViewDialog(mHolderActivity, this);
            myClickListener.onItemClick(getAdapterPosition(), v);
        }


        @Override
        public boolean onLongClick(View view) {
            myClickListener.onItemLongClick(getAdapterPosition(), view);
            return true;
        }
    }

    /**
     * The interface fot our Adapter listener
     * that wil be implementer in the DataHolder
     */
    public interface MyClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

}
