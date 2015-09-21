package com.example.zassmin.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zassmin on 9/19/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    // take a piece of data and turn it into a view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item for this position
        InstagramPhoto photo = (InstagramPhoto) getItem(position);

        // check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        // look up the view for populating the data [image and caption]
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvCreatedTime = (TextView) convertView.findViewById(R.id.tvCreatedTime);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfilePhoto = (ImageView) convertView.findViewById(R.id.ivProfilePhoto);

        // insert the item data into each of the items
        tvCaption.setText(photo.caption);
        tvUsername.setText(photo.username);
        tvCreatedTime.setText(photo.prettyTime());
        tvLikes.setText(photo.countString());

        // clear out image view if it was recycled
        ivPhoto.setImageResource(0);
        ivProfilePhoto.setImageResource(0);

        // load image using picasso
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        Picasso.with(getContext()).load(photo.profilePictureUrl).into(ivProfilePhoto);
        // return the created item as a view
        return convertView;
    }
}
