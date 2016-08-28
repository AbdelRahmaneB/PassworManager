package ub.passwordmanager.fragments.mainActivities;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import ub.passwordmanager.Models.AccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.adapters.MyRecyclerViewAdapter;


public class HomePage extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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


        // Initialise the CardView and his ad
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                ArrayList<AccountModel> mDataSet = getDataSet();
                Log.i("***", " Clicked on Item " + mDataSet.get(position).getmWebSite());
            }

            @Override
            public void onItemLongClick(int position, View v) {
                ArrayList<AccountModel> mDataSet = getDataSet();
                ImageView bt_delete = (ImageView) v.findViewById(R.id.bt_deleteAccount);
                ImageView bt_edit = (ImageView) v.findViewById(R.id.bt_editAccount);
                bt_delete.setVisibility(View.VISIBLE);
                bt_edit.setVisibility(View.VISIBLE);
                Toast.makeText(v.getContext(), "LongPress" + mDataSet.get(position).getmWebSite(), Toast.LENGTH_SHORT).show();
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

    private ArrayList<AccountModel> getDataSet() {
        ArrayList<AccountModel> results = new ArrayList<>();
        for (int index = 0; index < 20; index++) {
            AccountModel obj = new AccountModel("Some Primary Text " + index,
                    "Secondary " + index, "Last Update : 27/08/2016");
            results.add(index, obj);
        }
        return results;
    }

}
