package ub.passwordmanager.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_ImportExportDataBase;
import ub.passwordmanager.views.SplashScreen;

/**
 * Adapter for the Import/Export view.
 * <p/>
 * Created by UcefBen on 25/08/2016.
 */
public class ImportExportListAdapter extends BaseAdapter {

    // Our Data holders from the fragment
    private List<String> mTitles;
    private List<String> mDesc;


    // Get the Context and the layoutInflater from the Fragment
    private LayoutInflater mInflater = null;


    public ImportExportListAdapter(Activity context, List<String> titles, List<String> description) {
        this.mTitles = titles;
        this.mDesc = description;

        this.mInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // Create our DataHolder
            Holder dh = new Holder();

            // Inflate the our row layout for the Import/Export listView
            parent = null;
            convertView = mInflater.inflate(R.layout.adapter_row_ie_list, parent);

            // Get our Objects from the view
            dh.dh_Title = (TextView) convertView.findViewById(R.id.l_ie_title);
            dh.dh_Desc = (TextView) convertView.findViewById(R.id.l_ie_desc);

            // Set the value to the objects
            dh.dh_Title.setText(mTitles.get(position));
            dh.dh_Desc.setText(mDesc.get(position));

        }

        return convertView;
    }


    /**
     * This Holder class allow us to set the model for our listView Adapter
     */
    private class Holder {

        private TextView dh_Title;
        private TextView dh_Desc;

        Holder() {
            // Leave it empty
        }

    }
}
