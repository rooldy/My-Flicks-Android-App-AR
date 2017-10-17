package smy.sinattechnologie.flicks_apps.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alfonso on 09-Oct-17.
 */

public class Config {
    String imageBaseUrl;
    String posterSize;
    String backdropSize;


    public Config(JSONObject object) throws JSONException {
        JSONObject images=object.getJSONObject("images");
        imageBaseUrl=images.getString("secure_base_url");
        JSONArray posterSizeOptions=images.getJSONArray("poster_sizes");
        posterSize =posterSizeOptions.optString(3,"w342");
        JSONArray backdropSizeOptions=images.getJSONArray("backdrop_sizes");
        backdropSize= backdropSizeOptions.optString(1,"w780");
    }
    public  String getImageUrl(String size, String path)
    {
        return  String.format("%s%s%s",imageBaseUrl, size, path);
    }

    public String getPosterSize() {

        return posterSize;
    }

    public String getImageBaseUrl() {

        return imageBaseUrl;
    }

    public String getBackdropSize() {

        return backdropSize;
    }
}
