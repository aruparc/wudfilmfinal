package com.example.wudfilm.wudfilm;

import android.app.ProgressDialog;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import java.net.URL;
import java.util.*;

/**
 * A movie list fragment
 */
public class MovieListFragment extends Fragment implements ExpandableListView.OnGroupClickListener {

    List<String> Movies_list;
    List<String> synopsisRating;
    List<Movie> shownM;
    ExpandableListView Exp_list;
    MoviesAdapter adapter;
    HashMap<String, List<String>> MoviesDetails;
    View rootView;
    boolean done;
    ProgressDialog prog;

    public MovieListFragment() {
        MoviesDetails = new LinkedHashMap<String, List<String>>();
        shownM = new ArrayList<Movie>();
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
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Locale loc = Locale.US;
        TimeZone tz = TimeZone.getTimeZone("CST");
        Calendar cal = Calendar.getInstance(tz, loc);
        Calendar cal2 = Calendar.getInstance(tz, loc);
        cal2.add(cal2.DAY_OF_YEAR, 14);

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
        for (Movie m : MainActivity.movies) {
            Calendar cal3 = Calendar.getInstance();
            String[] tokens = m.date.split("[-]+");
            if (tokens[1].matches("\\d+")) {
                cal3.set(2016, map.get(tokens[0]), Integer.parseInt(tokens[1]));
            } else {
                cal3.set(2016, map.get(tokens[1]), Integer.parseInt(tokens[0]));
            }
            if (cal3.after(cal) && cal3.before(cal2)) {
                synopsisRating = new ArrayList<String>();
                //synopsisRating.add("Synopsis: ");
                if(!m.synopsis.equals("")){
                    synopsisRating.add(m.synopsis);
                    //synopsisRating.add(m.linkYT);
                }
                shownM.add(m);
                MoviesDetails.put(m.title + "\n\n" + m.date + " " + m.showtime + " " + m.runtime, synopsisRating);
            }
        }
        Exp_list = (ExpandableListView) rootView.findViewById(R.id.exp_list);
        Movies_list = new ArrayList<String>(MoviesDetails.keySet());
        adapter = new MoviesAdapter(getActivity(), MoviesDetails, Movies_list);
        Exp_list.setAdapter(adapter);
        Exp_list.setOnGroupClickListener(this);
        return rootView;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
        done = false;
        new getDets(shownM.get(groupPosition)).execute();
        while(done == false){}
        done = false;
        Exp_list.setAdapter(adapter);
        return false;
    }

    public class getDets extends AsyncTask<Void, View, Void> {
        Movie m;
        ExpandableListView exp;

        public getDets(Movie m){
            this.m = m;
        }

        @Override
        protected Void doInBackground(Void... params) {
            if(m.synopsis.equals("")) {
                try {
                    String title = m.getTitle();
                    title = title.replaceAll("\\*SUBTITLE SUNDAY\\* - ", "");
                    String[] t = title.split("[ ]+");
                    String convert = "";
                    if (t[0].charAt(t[0].length() - 1) == ':') {
                        t[0] = t[0].replaceAll(":", "");
                    }
                    convert = convert.concat(t[0]);
                    for (int i = 1; i < t.length; i++) {
                        if (t[i].charAt(0) == '(') {
                            t[i] = t[i].replaceAll("\\(", "").replaceAll("\\)", "");
                            convert = convert.concat("-" + t[i]);
                        } else if (t[i].equals("w/")) {
                            break;
                        } else {
                            convert = convert.concat("-" + t[i]);
                        }
                    }
                    String url = "https://union.wisc.edu/events-and-activities/event-calendar/event/" + convert;
                    Document document = Jsoup.connect(url).get();
                    Elements description = document.select("div[class=vevent] > p > span");
                    m.setSynopsis(description.text());
                    Elements img = document.select("li[class=remove-bottom] > img");
                    m.setPoster(img.attr("src"));
                    Elements yt = document.select("iframe");
                    m.setLinkYT(yt.attr("src"));
                    m.img = BitmapFactory.decodeStream(new URL("https://union.wisc.edu" + m.poster).openConnection().getInputStream());
                    adapter.setImg(m.img);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                onPostExecute();
            }
            adapter.setImg(m.img);
            done = true;
            return null;
        }

        protected void onPreExecute(){
            //prog = ProgressDialog.show(getContext(), "loading...", "", true);
        }

        protected Void onPostExecute(){
            MoviesDetails.get(m.title + "\n\n" + m.date + " " + m.showtime + " " + m.runtime).add(m.synopsis);
            //MoviesDetails.get(m.title + "\n\n" + m.date + " " + m.showtime + " " + m.runtime).add(m.linkYT);
            //prog.dismiss();
            return null;
        }
    }
}