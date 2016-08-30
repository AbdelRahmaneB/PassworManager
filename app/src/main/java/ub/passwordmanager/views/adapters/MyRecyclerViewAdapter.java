package ub.passwordmanager.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.R;

/** Description :
 * ToDo : explain the purpose of this class
 *
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
     */
    private ArrayList<PwdAccountModel> mDataSet;
    private static MyClickListener myClickListener;
    /** *********************************************************************** */

    /**
     * Adding the AdapterListener
     *
     * @param myClickListener : Instance of the listener
     */
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }


    /**
     * Adding the The Data to the adapter
     *
     * @param dataSet
     */
    public MyRecyclerViewAdapter(ArrayList<PwdAccountModel> dataSet) {
        this.mDataSet = dataSet;
    }

    /**
     * Creating and initialising our view of the Adapter
     *
     * @param parent
     * @param viewType
     * @return a DataObjectHolder
     */
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_cardview_row, parent, false);

        return new DataObjectHolder(view);
    }

    /**
     * Binding between the DataHolder and the view
     *
     * @param holder   : The DataHolder that will contain our data.
     * @param position : The index of our items in the "mDataSet".
     */
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.hSiteWeb.setText(mDataSet.get(position).getWebSite());
        holder.hLastUpdate.setText(mDataSet.get(position).getLastUpdate());
        holder.hEmailAddress.setText(mDataSet.get(position).getEmail());
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
        return mDataSet.size();
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

        /**
         * Constructor of The DataHolder.
         * Here we initialise the Holder fields and
         * adding a listener to our current CardView
         *
         * @param itemView : The current View
         */
        public DataObjectHolder(View itemView) {
            super(itemView);

            // Initialise the View objects
            hSiteWeb = (TextView) itemView.findViewById(R.id.l_SiteWeb);
            hLastUpdate = (TextView) itemView.findViewById(R.id.l_LastUpdate);
            hEmailAddress = (TextView) itemView.findViewById(R.id.l_EmailAddress);
            h_bt_Delete = (ImageView) itemView.findViewById(R.id.bt_deleteAccount);

            // Log the current action
            Log.i(LOG_TAG, "Adding Listener");

            // Add the listeners to our view
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Here" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
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