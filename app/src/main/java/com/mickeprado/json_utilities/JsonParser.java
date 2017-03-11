package com.mickeprado.json_utilities;

import android.content.Context;

import com.mickeprado.models.Movie;
import com.mickeprado.pelischidasxd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alumno on 11/03/2017.
 */

public class JsonParser
{
    public static ArrayList<Movie> getMovies(Context context, String JsonString)
    {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            JSONObject mainObject = new JSONObject(JsonString);
            JSONArray arrayOfMovies = mainObject.getJSONArray(context.getString(R.string.results_json_param));
            for (int i = 0; i < arrayOfMovies.length(); i++)
            {
                JSONObject movie = arrayOfMovies.getJSONObject(i);
                int id = movie.getInt(context.getString(R.string.id_json_param));
                String title = movie.getString(context.getString(R.string.title_json_param));
                String description = movie.getString(context.getString(R.string.description_json_param));
                String poster_path = movie.getString(context.getString(R.string.poster_path_json_param));
                poster_path = context.getString(R.string.base_url_image_api) +
                        context.getString(R.string.image_size_default) + poster_path;
                Movie movieInfo = new Movie(id,title,description,poster_path);
                movies.add(movieInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
