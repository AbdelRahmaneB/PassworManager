package ub.passwordmanager.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import ub.passwordmanager.R;

/**
 * Created by UB on 25/08/2016.
 */
public class ImportExportListAdapter extends BaseAdapter {

    // Our Data from the fragment
    private String[] mTitles;
    private String[] mDesc;


    // Get the Context and the layoutInflater from the Fragment
    private Context mContext;
    private LayoutInflater inflater = null;

    public ImportExportListAdapter(Activity context, String[] titles, String[] description) {
        this.mTitles = titles;
        this.mDesc = description;

        this.mContext = context;

        this.inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mTitles.length;
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

        // Create our DataHolder
        Holder dh = new Holder();

        // Inflate the our row layout for the Import/Export listView
        View view = inflater.inflate(R.layout.adapter_row_ie_list, null);

        // Get our Objects from the view
        dh.dh_Title = (TextView) view.findViewById(R.id.l_ie_title);
        dh.dh_Desc = (TextView) view.findViewById(R.id.l_ie_desc);

        // Set the value to the objects
        dh.dh_Title.setText(mTitles[position]);
        dh.dh_Desc.setText(mDesc[position]);

        // Create a listener for the those objects
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mTitles[position], Toast.LENGTH_SHORT).show();
            }
        });


        return view;
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
