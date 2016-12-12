package com.example.wudfilm.wudfilm;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * A movie list fragment
 */
public class MovieListFragment extends Fragment implements ExpandableListView.OnGroupClickListener{

    List<String> Movies_list;
    List<Object> synopsisRating;
    List<Movie> shownM;
    ExpandableListView Exp_list;
    MoviesAdapter adapter;
    HashMap<String, List<Object>> MoviesDetails;
    View rootView;
    boolean done;

    public MovieListFragment() {
        MoviesDetails = new LinkedHashMap<String, List<Object>>();
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
        //set date bounds of films shown on main screen
        Calendar cal = Calendar.getInstance(tz, loc);
        Calendar cal2 = Calendar.getInstance(tz, loc);
        //OFFSET FOR DEMO
        cal.add(cal.DAY_OF_YEAR, -30);
        cal2.add(cal2.DAY_OF_YEAR, -15);

        //hashmap to translate spreadsheet data to Calendar object
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("Jan", 0); map.put("Feb", 1); map.put("Mar", 2); map.put("Apr", 3);
        map.put("May", 4); map.put("Jun", 5); map.put("Jul", 6); map.put("Aug", 7);
        map.put("Sep", 8); map.put("Oct", 9); map.put("Nov", 10); map.put("Dec", 11);
        //add movies within bounds to list
        for (Movie m : MainActivity.movies) {
            Calendar cal3 = Calendar.getInstance();
            String[] tokens = m.date.split("[-]+");
            //inconsistant listing of month and day (parse by checking if first token is an int)
            if (tokens[1].matches("\\d+")) {
                cal3.set(2016, map.get(tokens[0]), Integer.parseInt(tokens[1]));
            } else {
                cal3.set(2016, map.get(tokens[1]), Integer.parseInt(tokens[0]));
            }
            //OFFSET FOR DEMO
            cal3.add(cal3.DAY_OF_YEAR, -16);
            //if we've already performed the second webcrawl add acquired
            // data to fragment on its creation
            if (cal3.after(cal) && cal3.before(cal2)) {
                synopsisRating = new ArrayList<Object>();
                if(!m.synopsis.equals("")){
                    synopsisRating.add(m.synopsis);
                    synopsisRating.add(m.img);
                    synopsisRating.add(m.linkYT);
                }
                shownM.add(m);
                MoviesDetails.put(m.title + "\n\n" + m.date + " " + m.showtime + " " + m.runtime, synopsisRating);
            }
        }
        //setup fragment view and listen for clicks on each group element
        Exp_list = (ExpandableListView) rootView.findViewById(R.id.exp_list);
        Movies_list = new ArrayList<String>(MoviesDetails.keySet());
        adapter = new MoviesAdapter(getActivity(), MoviesDetails, Movies_list);
        Exp_list.setAdapter(adapter);
        Exp_list.setOnGroupClickListener(this);
        return rootView;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
        new getDets(shownM.get(groupPosition)).execute();
        // wait for webcrawl to complete before proceeding (otherwise list will show
        // before it has updated)
        while(done == false){}
        done = false;
        return false;
    }

    public class getDets extends AsyncTask<Void, View, Void> {
        Movie m;

        public getDets(Movie m){
            this.m = m;
        }

        @Override
        protected Void doInBackground(Void... params) {
            done = false;
            // if we haven't already run the webcrawl then run a parse algorithm to obtain usable
            // URL for JSoup crawl (grab youtube link, poster, and details)
            if(m.synopsis.equals("")) {
                try {
                    String title = m.title;
                    title = title.replaceAll("\\*SUBTITLE SUNDAY\\* - ", "").replaceAll(":", "").replaceAll("'", "");
                    String[] t = title.split("[ ]+");
                    String convert = "";
                    convert = convert.concat(t[0]);
                    for (int i = 1; i < t.length; i++) {
                        if (t[i].charAt(0) == '(') {
                            t[i] = t[i].replaceAll("\\(", "").replaceAll("\\)", "");
                        } else if (t[i].equals("w/")) {
                            break;
                        }
                        convert = convert.concat("-" + t[i]);
                    }
                    String url = "https://union.wisc.edu/events-and-activities/event-calendar/event/" + convert;
                    //JSoup run on created url here
                    Document document = Jsoup.connect(url).get();
                    Elements data = document.select("div[class=vevent] > p > span");
                    m.synopsis = data.text();
                    data = document.select("iframe");
                    m.linkYT = data.attr("src");
                    data = document.select("li[class=remove-bottom] > img");
                    m.img = BitmapFactory.decodeStream(new URL("https://union.wisc.edu" + data.attr("src")).openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                onPostExecute();
            }
            done = true;
            return null;
        }

        protected Void onPostExecute(){
            //add elements grabbed to list for adapter
            MoviesDetails.get(m.title + "\n\n" + m.date + " " + m.showtime + " " + m.runtime).add(m.synopsis);
            MoviesDetails.get(m.title + "\n\n" + m.date + " " + m.showtime + " " + m.runtime).add(m.img);
            MoviesDetails.get(m.title + "\n\n" + m.date + " " + m.showtime + " " + m.runtime).add(m.linkYT);
            return null;
        }
    }
}