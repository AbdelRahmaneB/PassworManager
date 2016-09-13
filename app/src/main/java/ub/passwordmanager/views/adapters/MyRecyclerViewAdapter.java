package ub.passwordmanager.views.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.views.fragments.dialogs.DeletePwdAccountDialog;
import ub.passwordmanager.views.fragments.dialogs.EditPwdAccountDialog;
import ub.passwordmanager.views.fragments.dialogs.ViewPwdAccountDialog;

/**
 * Description :
 * ToDo : explain the purpose of this class
 * <p/>
 * Created by UB on 25/08/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {

    // Use this as a reference in the log window
    private static String LOG_TAG = "MyRecyclerViewAdapter";

    /**
     * 1. List of DataObject that represent our CardView
     * 2. Our Adapter listener
     * 3. The Current Activity
     */
    private static List<PwdAccountModel> mDataSet;
    private static MyClickListener myClickListener;
    protected Activity mActivity;
    /** *********************************************************************** */

    /**
     * Adding the AdapterListener
     *
     * @param mClickListener : Instance of the listener
     */
    public void setOnItemClickListener(MyClickListener mClickListener) {
        myClickListener = mClickListener;
    }


    /**
     * Adding the The Data to the adapter
     *
     * @param dataSet : List of data.
     */
    public MyRecyclerViewAdapter(ArrayList<PwdAccountModel> dataSet) {
        mDataSet = dataSet;
    }

    /**
     * Adding the The Data to the adapter and get the current activity
     *
     * @param dataSet : List of data.
     */
    public MyRecyclerViewAdapter(List<PwdAccountModel> dataSet, Activity activity) {
        mDataSet = dataSet;
        mActivity = activity;
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

        return new DataObjectHolder(view, this.mActivity);
    }


    /**
     * Binding between the DataHolder and the view
     *
     * @param holder   : The DataHolder that will contain our data.
     * @param position : The index of our items in the "mDataSet".
     */
    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.hSiteWeb.setText(mDataSet.get(position).getWebSite());
        holder.hLastUpdate.setText(mDataSet.get(position).getLastUpdate());
        holder.hEmailAddress.setText(mDataSet.get(position).getEmail());

        holder.h_bt_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ToDo : Event to delete the CardView
                new DeletePwdAccountDialog(mActivity).getDialog();
            }
        });

        holder.h_bt_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ToDo : Event to modify the CardView
                new EditPwdAccountDialog(mActivity, 0).getDialog();

            }
        });
    }

    /**
     * Function to add a new object to the CardView in a specific position
     *
     * @param dataObj : The data information "DataObject" for the CardView
     * @param index   : The position where we want to add the new CardView
     */
    public void addItem(PwdAccountModel dataObj, int index) {
        mDataSet.add(index, dataObj);
        notifyItemInserted(index);
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

    /** *********************************************************************** */
    //**************************************************************************//

    /**
     * Our DataHolder for the Adapter
     * In this class we add all the required listener that we want to use
     */
    public static class DataObjectHolder extends RecyclerView.ViewHolder
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

            // Add the listeners to our view
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            mHolderActivity = holderActivity;
        }


        @Override
        public void onClick(View v) {
            new ViewPwdAccountDialog(this.mHolderActivity, mDataSet.get(getAdapterPosition())).getDialog();
            myClickListener.onItemClick(getAdapterPosition(), v);
        }


        @Override
        public boolean onLongClick(View view) {
            myClickListener.onItemLongClick(getAdapterPosition(), view);
            return true;
        }
    }

    /**
     * Our Listener for the Adapter that wil be implementer in the DataHolder
     */
    public interface MyClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }
}
