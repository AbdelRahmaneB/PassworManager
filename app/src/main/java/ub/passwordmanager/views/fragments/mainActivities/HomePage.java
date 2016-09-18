package ub.passwordmanager.views.fragments.mainActivities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_ImportExportDataBase;
import ub.passwordmanager.Services.Service_PwdAccount;
import ub.passwordmanager.views.adapters.MyRecyclerViewAdapter;
import ub.passwordmanager.views.fragments.dialogs.CustomDialog;
import ub.passwordmanager.views.fragments.dialogs.NewPwdAccountDialog;


public class HomePage extends Fragment {

    // The CardView adapter that will hold the Password Account objects
    private MyRecyclerViewAdapter mAdapter;

    // Constructor
    public HomePage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        view.setTag(getResources().getString(R.string.nav_label_home));

        // Initialise the recycle view that will hold the CardView adapter
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ProgressBar pb = (ProgressBar) view.findViewById(R.id.home_progress);
        pb.setVisibility(View.VISIBLE);
        // Initialise the CardView adapter
        mAdapter = new MyRecyclerViewAdapter(getActivity());
        if (mAdapter.getItemCount() <= 0) {
            hideShowMessage(view, View.VISIBLE, View.INVISIBLE);
        } else {
            hideShowMessage(view, View.INVISIBLE, View.VISIBLE);
        }
        // Assign the adapter to the RecycleView
        mRecyclerView.setAdapter(mAdapter);
        pb.setVisibility(View.INVISIBLE);

        // Set the floating button to add a new password account
        setFloatingButton(view);

        //return the current view
        return view;
    }

    /**
     * The role of this function is to initialise
     * and show the foaling button for the new Password account
     */
    private void setFloatingButton(View view) {
        // Add the even handler for the floating button
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.addItem();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Show the buttons for Edit and Delete onItemLongClick in the cardView
        mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View view) {
            }

            @Override
            public void onItemLongClick(int position, View view) {
                view.findViewById(R.id.bt_deleteAccount).setVisibility(View.VISIBLE);
                view.findViewById(R.id.bt_editAccount).setVisibility(View.VISIBLE);
            }

        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Hide the cardView if there is no Data
     *
     * @param view        : The used view
     * @param visibility1 : Visibility for the message if there is no Data.
     * @param visibility2 : visibility for the CardView.
     */
    private void hideShowMessage(View view, int visibility1, int visibility2) {
        view.findViewById(R.id.home_no_record).setVisibility(visibility1);
        view.findViewById(R.id.my_recycler_view).setVisibility(visibility2);
    }


}
