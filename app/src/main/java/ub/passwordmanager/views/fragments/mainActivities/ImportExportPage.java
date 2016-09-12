package ub.passwordmanager.views.fragments.mainActivities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import ub.passwordmanager.R;
import ub.passwordmanager.views.adapters.ImportExportListAdapter;

public class ImportExportPage extends Fragment {

    // Our data for the Adapter
    private List<String> mImportTitles;
    private List<String> mImportDesc;
    private List<String> mExportTitles;
    private List<String> mExportDesc;

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
        buildTheData();

        // Declare and initialise the ListView
        ListView mImportOptionList = (ListView) view.findViewById(R.id.ie_options_list);
        ListView mExportOptionList = (ListView) view.findViewById(R.id.ie_options_list_export);

        // Create a custom adapter and initialise it.
        ImportExportListAdapter mImportListAdapter = new ImportExportListAdapter(getActivity(), mImportTitles, mImportDesc);
        ImportExportListAdapter mExportListAdapter = new ImportExportListAdapter(getActivity(), mExportTitles, mExportDesc);


        // Add the adapter to the ListView
        mImportOptionList.setAdapter(mImportListAdapter);
        mExportOptionList.setAdapter(mExportListAdapter);

        // Deactivate the Floating button
        FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.INVISIBLE);

        return view;
    }

    /**
     * This function initialise the data for our ListView.
     */
    private void buildTheData() {
        mImportTitles = Arrays.asList(
                getResources().getString(R.string.ie_title2),
                getResources().getString(R.string.ie_title4)
        );

        mExportTitles = Arrays.asList(
                getResources().getString(R.string.ie_title1),
                getResources().getString(R.string.ie_title3)
        );

        mImportDesc = Arrays.asList(
                getResources().getString(R.string.ie_title2_desc),
                getResources().getString(R.string.ie_title4_desc)
        );

        mExportDesc = Arrays.asList(
                getResources().getString(R.string.ie_title1_desc),
                getResources().getString(R.string.ie_title3_desc)
        );

    }

}
