package com.retor.p000001;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Антон on 07.02.14.
 */

public class myAdapter extends BaseAdapter {
    Activity activity;
    Context cont;
    ArrayList<massToView> ar;
    int res;

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param objects            The objects to represent in the ListView.
     */
    public myAdapter(Activity act, Context context, int resource, ArrayList<massToView> titles) {
        //super(context, resource, titles);
        activity = act;
        cont = context;
        res = resource;
        ar = titles;
        Log.d("Adapter", String.valueOf(getCount()));
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */

    public void add(massToView mass)
    {
        ar.add(mass);
    }

    @Override
    public int getCount()
    {
        return ar.size();
    }

    @Override
    public Object getItem(int position)
    {
        return ar.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    static class ViewHolder{
       TextView title;
       TextView author;
       TextView date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myv = convertView;
        ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// activity.getLayoutInflater();
            myv = inflater.inflate(res, parent, false);
            holder = new ViewHolder();
            //Get widgets by Id
            holder.title = (TextView) myv.findViewById(R.id.title);
            holder.author = (TextView) myv.findViewById(R.id.author);
            holder.date = (TextView) myv.findViewById(R.id.date);
            //Get inf to strings
            String tit = ar.get(position).getTitle().toString();
            String aut = String.valueOf(ar.get(position).getAuthor());
            String dat = String.valueOf(ar.get(position).getDate());
            //Fill ListView
            holder.title.setText(tit);
            holder.author.setText(aut);
            holder.date.setText(dat);
            //Logging
            Log.d("Adapter", tit);
        }
        return myv;
    }
}
