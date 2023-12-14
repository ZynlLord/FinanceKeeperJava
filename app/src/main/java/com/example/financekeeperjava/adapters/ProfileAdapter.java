package com.example.financekeeperjava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.presentation.main.profile.ProfileItem;

import java.util.ArrayList;

public class ProfileAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ProfileItem> profileItems;

    public ProfileAdapter(Context context, ArrayList<ProfileItem> profileItems) {
        this.context = context;
        this.profileItems = profileItems;
    }

    @Override
    public int getCount() {
        return profileItems.size();
    }

    @Override
    public Object getItem(int position) {
        return profileItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.profile_item_card, parent, false);
        }

        ImageView iconImageView = convertView.findViewById(R.id.iconImageView);
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView subtitleTextView = convertView.findViewById(R.id.subtitleTextView);

        ProfileItem item = profileItems.get(position);

        iconImageView.setImageResource(item.getIconResId());
        titleTextView.setText(item.getTitle());
        subtitleTextView.setText(item.getSubtitle());

        return convertView;
    }
}

