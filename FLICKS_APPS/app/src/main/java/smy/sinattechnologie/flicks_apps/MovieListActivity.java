package smy.sinattechnologie.flicks_apps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import smy.sinattechnologie.flicks_apps.Models.Config;
import smy.sinattechnologie.flicks_apps.Models.Movie;

public class MovieListActivity extends AppCompatActivity {

    public final static String API_BASE_URL ="https://api.themoviedb.org/3";
    public final static String API_KEY_PARAM ="api_key";

    public final static String TAG = "MovieListActivity";

    AsyncHttpClient client;
    //String imageBaseUrl;
    //String posterSize;
    ArrayList<Movie> movies;
    RecyclerView rvMovies;
    MovieAdapter adapter;
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        client = new AsyncHttpClient();
        movies = new ArrayList<>();

        adapter = new MovieAdapter(movies);
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);


        getConfiguration();


    }

    private void getNowPlaying(){
        String url = API_BASE_URL + "/movie/now_playing";
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));

        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i=0; i<results.length(); i++){
                        Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);
                        adapter.notifyItemInserted(movies.size() -1);
                    }
                    Log.i(TAG, String.format("Loaded %s movies", results.length()));
                } catch (JSONException e) {
                    logError("Failed to parse now playing movies", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get data from now playing", (JSONException) throwable, true);
            }
        });
    }
    private void getConfiguration(){
        String url = API_BASE_URL + "/configuration";
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    config = new Config(response);
                    Log.i(TAG,
                            String.format("Loaded configuration with imageBaseUrl %s and posterSize %s",
                                    config.getImageBaseUrl(),
                                    config.getPosterSize()));
                    adapter.setConfig(config);
                    getNowPlaying();
                } catch (JSONException e) {
                    logError("Failed parsing configuration", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting configuration");
            }
        });

    }

    private void logError(String s, JSONException e, boolean b) {
    }

    private void logError(String s) {
    }

    private  void LogError(String message, Throwable error, boolean alertUser){
        Log.e(TAG, message, error);
        if(alertUser){
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG ).show();

        }
    }


}
