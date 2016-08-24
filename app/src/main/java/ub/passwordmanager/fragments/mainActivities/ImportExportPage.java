package ub.passwordmanager.fragments.mainActivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ub.passwordmanager.R;

public class ImportExportPage extends Fragment {


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
        return inflater.inflate(R.layout.fragment_import_export_page, container, false);
    }

}
