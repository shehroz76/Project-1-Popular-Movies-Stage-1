package com.shehroz.msk.p1_popular_movies.util;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shehroz.msk.p1_popular_movies.Activities.MainActivity;
import com.shehroz.msk.p1_popular_movies.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by DELL on 8/11/2016.
 */
public class FetchMovieTask extends AsyncTask<String , Void , String> {
    @Override
    protected String doInBackground(String... params) {
        if(params.length ==0){
            return null;
        }
        String sortingCriteria = params[0];

        Uri builtUri = Uri.parse(ApiConstants.API_URL).buildUpon()
                .appendQueryParameter("sort_by", sortingCriteria + ".desc")
                .appendQueryParameter("api_key", ApiConstants.API_KEY)
                .build();
        String response;
        try {
            response  = getJSON(builtUri);
            return response;
        }catch (Exception e){
            MainActivity.toast.setText("Connection Error");
            MainActivity.toast.setDuration(Toast.LENGTH_SHORT);
            MainActivity.toast.show();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        if (response != null) {
            loadInfo(response);
        } else {
            MainActivity.toast.setText("No Internet Conection");
            MainActivity.toast.setDuration(Toast.LENGTH_SHORT);
            MainActivity.toast.show();
        }

    }

    public  static void loadInfo (String jsonString) {
        MainActivity.images.clear();
        MainActivity.moviesList.clear();

        try {
            if (jsonString != null) {
                JSONObject moviesObject = new JSONObject(jsonString);
                JSONArray moviesArray = moviesObject.getJSONArray("results");


                for (int i = 0; i <= moviesArray.length(); i++) {
                    JSONObject movie = moviesArray.getJSONObject(i);
                    Movie movieItem = new Movie();
                    movieItem.setTitle(movie.getString("title"));

                    movieItem.setId(movie.getInt("id"));

                    movieItem.setOriginal_title(movie.getString("original_title"));

                    if (movie.getString("overview") == "null") {
                        movieItem.setOverview("No Overview was Found");
                    } else {
                        movieItem.setOverview(movie.getString("overview"));
                    }
                    if (movie.getString("release_date") == "null") {
                        movieItem.setRelease_date("Unknown Release Date");
                    } else {
                        movieItem.setRelease_date(movie.getString("release_date"));
                    }

                    movieItem.setVote_average(movie.getString("vote_average"));

                    movieItem.setPoster_path(movie.getString("poster_path"));
                    if (movie.getString("poster_path") == "null") {
                        MainActivity.images.add(ApiConstants.IMAGE_NOT_FOUND);
                        movieItem.setPoster_path(ApiConstants.IMAGE_NOT_FOUND);
                    } else {
                        MainActivity.images.add(ApiConstants.IMAGE_URL + ApiConstants.IMAGE_SIZE_185 + movie.getString("poster_path"));
                    }
                    MainActivity.moviesList.add(movieItem);
                    MainActivity.posterAdapter.notifyDataSetChanged();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static String getJSON(Uri builtUri)
    {
        InputStream inputStream;
        StringBuffer buffer;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJson = null;

        try {
            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            inputStream = urlConnection.getInputStream();
            buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            moviesJson = buffer.toString();
        } catch (IOException e) {
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {

                }
            }
        }


        return moviesJson;
    }

}
