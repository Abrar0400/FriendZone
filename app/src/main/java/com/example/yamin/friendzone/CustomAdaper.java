package com.example.yamin.friendzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yamin on 2/5/2017.
 */
public class CustomAdaper extends BaseAdapter implements Filterable {


    private class ViewHolder {
        TextView name4;
    }

    public Context context;
    ArrayList<Contact> contacts;
    ArrayList<Contact> newContacts;
    customFiler custom;
    LayoutInflater inflater;

    public CustomAdaper(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
        this.newContacts = contacts;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int pos) {
        return null;
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    //for listView
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_customadapter, null);
            holder.name4 = (TextView) view.findViewById(R.id.textViewName);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Contact contact = contacts.get(i);
        holder.name4.setText(contact.getName());

        return view;
    }

    @Override
    public Filter getFilter() {
        if(custom==null){
            custom=new customFiler();
        }

        return custom;
    }

    class customFiler extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                //upper
                constraint = constraint.toString().toUpperCase();
                ArrayList<Contact> filter = new ArrayList<Contact>();
                //get specific item
                for (int i = 0; i < newContacts.size(); i++) {
                    if (newContacts.get(i).getName().toUpperCase().contains(constraint)) {
                        Contact c = new Contact(newContacts.get(i).getName());
                        filter.add(c);
                    }
                }
                results.count = filter.size();
                results.values = filter;
            } else {
                results.count = newContacts.size();
                results.values = newContacts;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contacts = (ArrayList<Contact>) results.values;
            notifyDataSetChanged();
        }
    }
}