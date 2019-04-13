package com.example.andoid_image_app;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RecyclerView rv_img;
    EditText searchEditText;
    Button searchButton;
    ArrayList<ImageData> imageData = new ArrayList<>();
    ImageAdapter adapter;

    public void getData(String terms) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String API_KEY = "562d4066f1d17762ddb6d6a6512b26445a14b8ffa19d67d6d946af88d2e265a1";
        String url = String.format("https://api.unsplash.com/search?client_id=%1$s&query=%2$s", API_KEY, terms);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject photos = response.getJSONObject("photos");
                            JSONArray images = photos.getJSONArray("results");

                            for (int i = 0; i < images.length(); i++) {
                                JSONObject image = images.getJSONObject(i);
                                JSONObject imgUrls = image.getJSONObject("urls");

                                String id = image.getString("id");
                                String src = imgUrls.getString("regular");
                                String desc = image.getString("description");

                                ImageData data = new ImageData(id, src, desc);
                                imageData.add(data);
                            }

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void searchImages(View v) {
        String terms;
        terms = searchEditText.getText().toString();

        getData(terms);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        rv_img = findViewById(R.id.rv_img);

        adapter = new ImageAdapter(imageData);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rv_img.setLayoutManager(layoutManager);
        rv_img.setItemAnimator(new DefaultItemAnimator());

        rv_img.setAdapter(adapter);
        rv_img.setHasFixedSize(true);

        rv_img.addOnItemTouchListener(new ImageAdapter.RecyclerTouchListener(getApplicationContext(), rv_img, new ImageAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", imageData);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
