package com.example.contactv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resource;
    private List<Contact> arrContact;
    public CustomAdapter(Context context, int resource, ArrayList<Contact> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvIcon = (ImageView) convertView.findViewById(R.id.image_avatar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = arrContact.get(position);
        viewHolder.tvName.setText(contact.getName());
        String icon1[] = contact.getName().split("[ ]");
        String icon2 = icon1[icon1.length-1];
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(contact);
        TextDrawable drawable = TextDrawable.builder().buildRound(icon2.substring(0,1), color);
        viewHolder.tvName.setTextColor(color);
        viewHolder.tvIcon.setImageDrawable(drawable);
        return convertView;
    }
    public class ViewHolder {
        TextView tvName;
        ImageView tvIcon;
    }
}