package com.example.wudfilm.wudfilm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.AsyncTask;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new ListFragment())
                        .commit();
            }
            new Title().execute();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
   public class MovieObj{
        String title;
        int runtime;
        String synopsis;
        int showtime;

        //For iteration 2
        String MPAArating;
        String linkM, linkR, linkI, linkYT;
        String poster;
        boolean interested;

        //constructor
        public MovieObj(String title, int runtime, int showtime,String synopsis, String MPAArating, String linkM,
                        String linkR, String linkI, String linkYT, String poster, boolean interested) {
            this.title = title;
            this.runtime = runtime;
            this.synopsis = synopsis;
            this.showtime = showtime;

            //iteration 2 & 3 things
            this.MPAArating = "";
            this.linkM = "";
            this.linkR= "";
            this.linkI = "";
            this.linkYT = "";
            this.poster= "";
            this.interested = false;

        }

        //getters
        public String getTitle(){return title;}
        public int getRuntime(){return runtime;}
        public String getSynopsis(){return synopsis;}
        public int getShowtime(){return showtime;}
        //iteration 2 & 3
        public String getMPAArating(){return MPAArating;}
        public String getLinkM(){return linkM;}
        public String getLinkR(){return linkR;}
        public String getLinkI(){return linkI;}
        public String getLinkYT(){return linkYT;}
        public boolean getInterested(){return interested;}

        //setters
        public void setTitle(String title){this.title = title;}
        public void setRuntime(int runtime){this.runtime = runtime;}
        public void setSynopsis(String synopsis){this.synopsis = synopsis;}
        public void setShowtime(int showtime){this.showtime = showtime;}

        //iteration 2 & 3
        public void setMPAArating(String MPAArating) {this.MPAArating = MPAArating;}
        public void setLinkM(String linkM){this.linkM = linkM;}
        public void setLinkR(String linkR){this.linkR = linkR;}
        public void setLinkI(String linkI){this.linkI = linkI;}
        public void setLinkYT(String linkYT){this.linkYT = linkYT;}
        public void setInterested(boolean interested) {this.interested = interested;}

    }

    MovieObj movie1 = new MovieObj("Nosferatu<3", 183645, 3,"the vampire bae", "", "", "", "", "", "", false);
    MovieObj movie2 = new MovieObj("Dracula", 10,2, "not as good as Nosferatu", "", "", "", "", "", "", false);
    MovieObj movie3 = new MovieObj("Gandalf", 234,5, "Gandalf is old and not fast",  "", "", "", "", "", "", false);
    MovieObj movie4 = new MovieObj("Pompei", 36745,1, "more like pomp-ayyy, amirite",  "", "", "", "", "", "", false);
    MovieObj movie5 = new MovieObj("Smurfs", 374,4, "blue",  "", "", "", "", "", "", false);


    ///////////////////////////////////////////////////////////////////////////////////////////
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ListFragment extends Fragment {

        public ListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //compiler issues

            String [] movieArray = {
                    "Up",
                    "Room",
                    "Finding Nemo",
                    "13 Going On Thirty",
                    "Pulp Fiction",
                    "Harry Potter",
                    "Fast and Furious",
                    "Harry Potter","Harry Potter","Harry Potter","Harry Potter","Harry Potter"
            };

            List<String> movieList = new ArrayList<String>(
                    Arrays.asList(movieArray));
            //List<MovieObj>movieList = new ArrayList<MovieObj>(movieArray);
            /*
            ArrayList<MovieObj> movieArray = new ArrayList<MovieObj>();
            movieArray.add(movie1);
            movieArray.add(movie2);
            movieArray.add(movie3);
            movieArray.add(movie4);
            movieArray.add(movie5);
            */

            ArrayAdapter<String> mMovieAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.list_item_movie,
                    R.id.list_item_movie_textview,
                    movieList
            );

            ListView listView = (ListView) rootView.findViewById(
                    R.id.listview_movie);
            listView.setAdapter(mMovieAdapter);
            return rootView;

        }
    }

    // Description AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {

        // URL Address
        String url = "http://www.androidbegin.com";
        ProgressDialog mProgressDialog;
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect("https://www.google.com/").get();
                // Using Elements to get the Meta data
                //title = document.title();
                Elements description = document
                        .select("a.gb_P");
                // Locate the content attribute
                desc = description.text();
                System.out.print(desc);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
           // TextView txttitle = (TextView) findViewById(R.id.titletxt);
            //txttitle.setText(title);
            System.out.print(desc);
            mProgressDialog.dismiss();
        }
    }


}



