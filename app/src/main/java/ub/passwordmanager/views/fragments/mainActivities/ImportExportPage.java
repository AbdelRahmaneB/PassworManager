package ub.passwordmanager.views.fragments.mainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_ImportExportDataBase;
import ub.passwordmanager.views.SplashScreen;
import ub.passwordmanager.views.adapters.ImportExportListAdapter;

public class ImportExportPage extends Fragment {

    // Our data for the Adapter
    private List<String> mImportTitles;
    private List<String> mImportDesc;
    private List<String> mExportTitles;
    private List<String> mExportDesc;

    // Constructor
    public ImportExportPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
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
        mImportOptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                // For The DataBase import
                if (index == 0) {
                    // Import DataBase
                    if (Service_ImportExportDataBase.getInstance().importDataBase(getContext())) {
                        getActivity().finish();
                        getActivity().getFragmentManager().popBackStack();
                        getActivity().startActivity(new Intent(getContext(), SplashScreen.class));
                    }
                } else {
                    // Import XML File
                    Service_ImportExportDataBase.getInstance().importFromXml(getContext());
                }
            }
        });

        mExportOptionList.setAdapter(mExportListAdapter);
        mExportOptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                // For The DataBase Export
                if (index == 0) {
                    // Export DataBase
                    Service_ImportExportDataBase.getInstance().exportDataBase(getContext());
                } else {
                    // Export XML File
                    Service_ImportExportDataBase.getInstance().exportToXml(getContext());
                }
            }
        });

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
