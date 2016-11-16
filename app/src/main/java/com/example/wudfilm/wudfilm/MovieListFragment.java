package com.example.wudfilm.wudfilm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A movie list fragment
 */
public class MovieListFragment extends Fragment {

    ArrayList<Movie> movies;
    public MovieListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Add this line in oder for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // autonmatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ArrayList<String> displayM = new ArrayList<String>();
        for(Movie m : MainActivity.movies){
            displayM.add("Movie: " + m.title + "\nShowtime: " + m.date + " " + m.showtime + "\nRuntime: " + m.runtime);
        }

        ArrayAdapter<String> mMovieAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_movie,
                R.id.list_item_movie_textview,
                displayM
        );

        ListView listView = (ListView) rootView.findViewById(
                R.id.listview_movie);
        listView.setAdapter(mMovieAdapter);
        return rootView;
    }
}