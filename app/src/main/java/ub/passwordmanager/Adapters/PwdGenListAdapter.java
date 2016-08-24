package ub.passwordmanager.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import ub.passwordmanager.R;

/**
 * Class Description
 * Created by UB on 24/08/2016.
 */
public class PwdGenListAdapter extends BaseAdapter {

    private String[] mTitles;
    private String[] mDesc;
    private int[] mCb;

    Context context;
    private static LayoutInflater inflater = null;

    public PwdGenListAdapter(Activity mainActivity, String[] titles, String[] desc, int[] cb) {
        mTitles = titles;
        mDesc = desc;
        mCb = cb;

        context = mainActivity;

        inflater = (LayoutInflater) context.
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
        Holder holder = new Holder();
        View view  = inflater.inflate(R.layout.adapter_row_option_list, null);

        holder.title = (TextView) view.findViewById(R.id.l_opt_title);
        holder.desc = (TextView) view.findViewById(R.id.l_opt_sub_item);
        holder.cb = (CheckBox) view.findViewById(R.id.l_opt_check);

        holder.title.setText(mTitles[position]);
        holder.desc.setText(mDesc[position]);
        holder.cb.setChecked(true);

        //holder.cb.setChecked(mCb[position]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked " + mTitles[position], Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }



    public class Holder {
        TextView title;
        TextView desc;
        CheckBox cb;
    }
}
