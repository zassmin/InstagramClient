package com.example.zassmin.instagramclient;

import android.text.format.DateUtils;

/**
 * Created by zassmin on 9/19/15.
 */
public class InstagramPhoto {
    public String created_time;
    public String username;
    public String profilePictureUrl;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;

    public String countString() {
        return this.likesCount + " likes";
    }

    // TODO: move these into a different file that's just meant for parsing
    public String prettyTime() {
        Long longCreatedTime = new Long(this.created_time);
        String relativeTime = DateUtils.getRelativeTimeSpanString(
                longCreatedTime * 1000,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS).toString();
        return shortenTime(relativeTime);
    }

    private String shortenTime(String timeString) {
        String arr[] = timeString.split(" ");
        return arr[0] + arr[1].charAt(0);
    }
}
