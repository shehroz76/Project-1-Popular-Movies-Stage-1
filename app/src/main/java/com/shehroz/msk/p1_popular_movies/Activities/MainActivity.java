package com.shehroz.msk.p1_popular_movies.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.shehroz.msk.p1_popular_movies.Adapters.PosterAdapter;
import com.shehroz.msk.p1_popular_movies.Model.Movie;
import com.shehroz.msk.p1_popular_movies.R;
import com.shehroz.msk.p1_popular_movies.util.FetchMovieTask;

import java.util.ArrayList;

/**
 * Created by DELL on 8/11/2016.
 */
public class MainActivity extends ActionBarActivity {


    static public ArrayList<Movie> moviesList;
    static public ArrayList<String> images;
    static public PosterAdapter posterAdapter;
    static public String lastSortOrder;
    static GridView gridview;
    public static Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MoviesFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MoviesFragment extends Fragment {

        public MoviesFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle onSavedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            setHasOptionsMenu(true);
            gridview = (GridView) rootView.findViewById(R.id.gridview);
            int ot = getResources().getConfiguration().orientation;
            gridview.setNumColumns(ot == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
            gridview.setAdapter(posterAdapter);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("movie_id", moviesList.get(position).getId());
                    intent.putExtra("movie_position", position);
                    startActivity(intent);
                }
            });

            toast = Toast.makeText(rootView.getContext(),"", Toast.LENGTH_SHORT);
            return rootView;
        }
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.fragment_main, menu);
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();

            if (id == R.id.action_fragment_settings) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
        @Override
        public void onSaveInstanceState(Bundle outState) {
            outState.putParcelableArrayList("movies", MainActivity.moviesList);
            outState.putStringArrayList("images", MainActivity.images);
            super.onSaveInstanceState(outState);
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            if(savedInstanceState != null && savedInstanceState.containsKey("movies")) {
                moviesList = savedInstanceState.getParcelableArrayList("movies");
                images = savedInstanceState.getStringArrayList("images");
            }else{
                moviesList = new ArrayList<Movie>();
                images = new ArrayList<String>();
                posterAdapter = new PosterAdapter(getActivity());
                updateMovies();
            }
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onResume() {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String sortingCriteria = sharedPrefs.getString(getString(R.string.pref_sorting_criteria_key), getString(R.string.pref_sorting_criteria_default_value));

            if(lastSortOrder!= null && !sortingCriteria.equals(lastSortOrder)){
                moviesList = new ArrayList<Movie>();
                images = new ArrayList<String>();
                updateMovies();
            }
            lastSortOrder = sortingCriteria;
            super.onResume();

        }



        public void updateMovies() {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String sortingCriteria = sharedPrefs.getString(getString(R.string.pref_sorting_criteria_key), getString(R.string.pref_sorting_criteria_default_value));
            new FetchMovieTask().execute(sortingCriteria, null);
        }



    }
}