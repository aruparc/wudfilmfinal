package com.example.wudfilm.wudfilm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.wudfilm.wudfilm.Movie;
import com.example.wudfilm.wudfilm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



///////////////////////////////////////////////////////////////////////////////////////////
/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListFragment extends Fragment {

    public MovieListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Movie movie1 = new Movie("Nosferatu<3", 183645, 3,"the vampire bae", "", "", "", "", "", "", false);
        Movie movie2 = new Movie("Dracula", 10,2, "not as good as Nosferatu", "", "", "", "", "", "", false);
        Movie movie3 = new Movie("Gandalf", 234,5, "Gandalf is old and not fast",  "", "", "", "", "", "", false);
        Movie movie4 = new Movie("Pompei", 36745,1, "more like pomp-ayyy, amirite",  "", "", "", "", "", "", false);
        Movie movie5 = new Movie("Smurfs", 374,4, "blue",  "", "", "", "", "", "", false);

            ArrayList<Movie> movieArray = new ArrayList<Movie>();
            movieArray.add(movie1);
            movieArray.add(movie2);
            movieArray.add(movie3);
            movieArray.add(movie4);
            movieArray.add(movie5);


        ArrayAdapter<Movie> mMovieAdapter = new ArrayAdapter<Movie>(
                getActivity(),
                R.layout.list_item_movie,
                R.id.list_item_movie_textview,
                movieArray
        );

        ListView listView = (ListView) rootView.findViewById(
                R.id.listview_movie);
        listView.setAdapter(mMovieAdapter);
        return rootView;

    }

    public class fetchMovieInfoTask{

    }
}
