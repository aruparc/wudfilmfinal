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

/**
 * A movie list fragment
 */
public class MovieListFragment extends Fragment {

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

        Movie movie1 = new Movie("Nosferatu<3", 183645, 3, "the vampire bae", "", "", "", "", "", "", false);
        Movie movie2 = new Movie("Dracula", 10, 2, "not as good as Nosferatu", "", "", "", "", "", "", false);
        Movie movie3 = new Movie("Gandalf", 234, 5, "Gandalf is old and not fast", "", "", "", "", "", "", false);
        Movie movie4 = new Movie("Pompei", 36745, 1, "more like pomp-ayyy, amirite", "", "", "", "", "", "", false);
        Movie movie5 = new Movie("Smurfs", 374, 4, "blue", "", "", "", "", "", "", false);

        ArrayList<Movie> movieArray = new ArrayList<Movie>();
        movieArray.add(movie1);
        movieArray.add(movie2);
        movieArray.add(movie3);
        movieArray.add(movie4);
        movieArray.add(movie5);

        ArrayList<String> movies = new ArrayList<String>();
        movies.add("Movie: " + movie1.title + "\nRuntime: " + movie1.runtime + "\nSynopsis: " + movie1.synopsis + "\nShowtime: " + movie1.showtime);
        movies.add("Movie: " + movie2.title + "\nRuntime: " + movie2.runtime + "\nSynopsis: " + movie2.synopsis + "\nShowtime: " + movie2.showtime);
        movies.add("Movie: " +movie3.title + "\nRuntime: " + movie3.runtime + "\nSynopsis: " + movie3.synopsis + "\nShowtime: " + movie3.showtime);
        movies.add("Movie: " +movie4.title +"\nRuntime: " + movie4.runtime + "\nSynopsis: "+ movie4.synopsis + "\nShowtime: " + movie4.showtime);
        movies.add("Movie: " +movie5.title + "\nRuntime: "+ movie5.runtime + "\nSynopsis: " + movie5.synopsis + "\nShowtime: " + movie5.showtime);


        ArrayAdapter<String> mMovieAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_movie,
                R.id.list_item_movie_textview,
                movies
        );

        ListView listView = (ListView) rootView.findViewById(
                R.id.listview_movie);
        listView.setAdapter(mMovieAdapter);
        return rootView;

    }
}