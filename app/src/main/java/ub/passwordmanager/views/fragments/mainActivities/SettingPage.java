package ub.passwordmanager.views.fragments.mainActivities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ub.passwordmanager.R;


public class SettingPage extends Fragment {


    public SettingPage() {
        // Required empty public constructor
    }

    public static SettingPage newInstance() {
        return new SettingPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_page, container, false);

        // Get the listView instance
        ListView mSettingListView = (ListView) view.findViewById(R.id.settings_list_view);

        // Add the listener for our listView
        mSettingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // ToDo : Action for each item in the list, in this case a Dialog
                Toast.makeText(getContext(), "Id : " + i, Toast.LENGTH_SHORT).show();
            }
        });

        // Deactivate the Floating button
        FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.INVISIBLE);

        return view;
    }
}
