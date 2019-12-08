package com.example.contactv1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
            viewHolder.tvCall = (FloatingActionButton) convertView.findViewById(R.id.fab_call);
            viewHolder.tvCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", arrContact.get(position).getPhone(), null));
                    context.startActivity(intent);
                }
            });
            viewHolder.tvSms = (FloatingActionButton) convertView.findViewById(R.id.fab_sms);
            viewHolder.tvSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.putExtra("sms_body", "");
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    context.startActivity(sendIntent);
                }
            });
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
        viewHolder.tvIcon.setImageDrawable(drawable);
        return convertView;
    }

    public class ViewHolder {
        TextView tvName;
        ImageView tvIcon;
        FloatingActionButton tvCall;
        FloatingActionButton tvSms;
    }

}



