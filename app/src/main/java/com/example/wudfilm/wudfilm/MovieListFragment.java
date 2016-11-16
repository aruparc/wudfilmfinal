package com.example.wudfilm.wudfilm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * A movie list fragment
 */
public class MovieListFragment extends Fragment{

    HashMap<String, List<String>> Movies_category;
    List<String> Movies_list;
    ExpandableListView Exp_list;
    MoviesAdapter adapter;

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

        HashMap<String, List<String>> MoviesDetails = new HashMap<String, List<String>>();

        Locale loc = Locale.US;
        TimeZone tz = TimeZone.getTimeZone("CST");
        Calendar cal = Calendar.getInstance(tz, loc);
        Calendar cal2 = Calendar.getInstance(tz, loc);
        cal2.add(cal2.DAY_OF_YEAR, 21);

        //creates the hashmap
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("Jan", 0);
        map.put("Feb", 1);
        map.put("Mar", 2);
        map.put("Apr", 3);
        map.put("May", 4);
        map.put("Jun", 5);
        map.put("Jul", 6);
        map.put("Aug", 7);
        map.put("Sep", 8);
        map.put("Oct", 9);
        map.put("Nov", 10);
        map.put("Dec", 11);

        for(Movie m : MainActivity.movies) {
            Calendar cal3 = Calendar.getInstance();
            String[] tokens = m.date.split("[-]+");
            if(tokens[1].matches("\\d+")) {
                cal3.set(2016, map.get(tokens[0]), Integer.parseInt(tokens[1]) + 1);
            }else{
                cal3.set(2016, map.get(tokens[1]), Integer.parseInt(tokens[0]) + 1);
            }
            if (cal3.after(cal) && cal3.before(cal2)) {
                List<String> synopsisRating = new ArrayList<String>();
                synopsisRating.add("Synopsis: " + m.synopsis);
                synopsisRating.add(m.poster);
                synopsisRating.add(m.linkYT);
                MoviesDetails.put(m.title + "\n\n" + m.date + " " + m.showtime + " " + m.runtime, synopsisRating);
            }
        }

        Exp_list = (ExpandableListView) rootView.findViewById(R.id.exp_list);
        Movies_list = new ArrayList<String>(MoviesDetails.keySet());
        adapter = new MoviesAdapter(getActivity(), MoviesDetails, Movies_list);
        Exp_list.setAdapter(adapter);
        //Exp_list.setOnGroupClickListener(this);
        return rootView;
    }


    /*@Override
    public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
        try {
            Document document = Jsoup.connect("https://union.wisc.edu/events-and-activities/event-calendar/event/captain-fantastic-2016").get();
            Elements description = document.select("div[class=vevent] > p > span");
            String desc = description.text();
            Elements img = document.select("li[class=remove-bottom] > img");
            String imgSrc = img.attr("src");
            Elements yt = document.select("iframe");
            String ytSrc = yt.attr("src");
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }*/

}