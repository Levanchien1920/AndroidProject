package com.qhung.contact;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Contact> implements Filterable {
    private Context context;
    private int resource;
    private List<Contact> arrContact;
    List<Contact> sContact;
    ValueFilter valueFilter;
    LayoutInflater inflater;

    public CustomAdapter(Context context, int resource, ArrayList<Contact> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
        sContact = arrContact;
    }

    @Override
    public int getCount(){
        return arrContact.size();
    }

    @Override
    public Contact getItem(int i){
        return arrContact.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.imgAvatar = (ImageView) convertView.findViewById(R.id.imgView);
            viewHolder.callButton = (ImageButton) convertView.findViewById(R.id.call_btn);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Contact contact = arrContact.get(position);
        viewHolder.tvName.setText(contact.getName());
        viewHolder.imgAvatar.setImageResource(contact.getAvatar());
        viewHolder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contact.getPhone(), null));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView tvName;
        ImageView imgAvatar;
        ImageButton callButton;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Contact> filterList = new ArrayList<Contact>();
                for (int i = 0; i < sContact.size(); i++) {
                    if ((sContact.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        Contact contact = new Contact(sContact.get(i).getName(),
                                sContact.get(i).getPhone(),
                                sContact.get(i).getAvatar(),
                                sContact.get(i).getId());
                        filterList.add(contact);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = sContact.size();
                results.values = sContact;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            arrContact = (ArrayList<Contact>) results.values;
            notifyDataSetChanged();
        }
    }
}
