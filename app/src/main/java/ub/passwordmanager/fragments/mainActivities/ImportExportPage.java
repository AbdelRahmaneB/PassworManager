package ub.passwordmanager.fragments.mainActivities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ub.passwordmanager.R;
import ub.passwordmanager.adapters.ImportExportListAdapter;

public class ImportExportPage extends Fragment {

    // Our data for the Adapter
    private String[] mTitles;
    private String[] mDesc;

    public ImportExportPage() {
        // Required empty public constructor
    }

    public static ImportExportPage newInstance() {
        return new ImportExportPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_import_export_page, container, false);

        // Initialise the data for the ListView
        initialiseTheData();

        // Declare and initialise the ListView
        ListView mOptionList = (ListView) view.findViewById(R.id.ie_options_list);

        // Create a custom adapter and initialise it.
        ImportExportListAdapter mListAdapter = new ImportExportListAdapter(getActivity(),mTitles,mDesc);

        // Add the adapter to the ListView
        mOptionList.setAdapter(mListAdapter);

        // Deactivate the Floating button
        FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.INVISIBLE);

        return view;
    }

    /**
     * This function initialise the data for our ListView.
     */
    private void initialiseTheData() {
        mTitles = new String[]{
                getResources().getString(R.string.ie_title1),
                getResources().getString(R.string.ie_title2),
                getResources().getString(R.string.ie_title3),
                getResources().getString(R.string.ie_title4),
        };

        mDesc = new String[]{
                getResources().getString(R.string.ie_title1_desc),
                getResources().getString(R.string.ie_title2_desc),
                getResources().getString(R.string.ie_title3_desc),
                getResources().getString(R.string.ie_title4_desc),
        };

    }

}
