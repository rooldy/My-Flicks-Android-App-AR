package smy.sinattechnologie.flicks_apps.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alfonso on 08-Oct-17.
 */

public class Movie {
    private String title;
    private String overview;
    private  String posterPath;
    private  String backdropPath;


    public  Movie(JSONObject object) throws JSONException {
        title=object.getString("title");
        overview=object.getString("overview");
        posterPath=object.getString("posterpath");
        backdropPath=object.getString("backdrop_path");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
}
