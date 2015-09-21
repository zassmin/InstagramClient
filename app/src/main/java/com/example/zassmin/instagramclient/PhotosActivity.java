package com.example.zassmin.instagramclient;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PhotosActivity extends ActionBarActivity {

    // NOTE: Is there a best practice to how we safely story secret keys?
    public static final String CLIENT_ID = "";
    private static final String ROOT_KEY = "data";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photos = new ArrayList<>();

        // create adapter linking it to it's source
        aPhotos = new InstagramPhotosAdapter(this, photos);
        // find the listview from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // set the adapter
        lvPhotos.setAdapter(aPhotos);

        // SEND OUT API REQUEST - How come it's last?
        fetchPopularPhotos();
    }

    // Trigger network request
    public void fetchPopularPhotos() {

        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        // Create network client
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            // onSuccess 200

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Expecting a JSON object
                JSONArray photosJson = null;
                try {
                    photosJson = response.getJSONArray(ROOT_KEY);
                    for (int i = 0; i < photosJson.length(); i++) {
                        JSONObject photoJson = photosJson.getJSONObject(i);

                        // decode attributes
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.created_time = photoJson.getString("created_time");
                        photo.username = photoJson.getJSONObject("user").getString("username");
                        photo.profilePictureUrl = photoJson.getJSONObject("user").getString("profile_picture");
                        photo.caption = photoJson.getJSONObject("caption").getString("text");
                        photo.imageUrl = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = photoJson.getJSONObject("likes").getInt("count");
                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // callback
                aPhotos.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // do something!
                Log.i("DEBUG", responseString);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
