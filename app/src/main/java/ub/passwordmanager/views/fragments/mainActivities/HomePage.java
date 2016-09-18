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

    private MyRecyclerViewAdapter mAdapter;
    private  View mCurrentView;

    public HomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomePage.
     */
    public static HomePage newInstance() {
        return new HomePage();
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

        mCurrentView = view;

        // Initialise the CardView and his ad
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter( getActivity());
        if (mAdapter.getItemCount() <= 0) {
            hideShowMessage(view, View.VISIBLE, View.INVISIBLE);
        } else {
            hideShowMessage(view, View.INVISIBLE, View.VISIBLE);
        }
        mAdapter.getItemCount();
        mRecyclerView.setAdapter(mAdapter);

        setFloatingButton(view);


        return view;
    }

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
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
            }

            @Override
            public void onItemLongClick(int position, View v) {
                ImageView bt_delete = (ImageView) v.findViewById(R.id.bt_deleteAccount);
                ImageView bt_edit = (ImageView) v.findViewById(R.id.bt_editAccount);
                bt_delete.setVisibility(View.VISIBLE);
                bt_edit.setVisibility(View.VISIBLE);
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
