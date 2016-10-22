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



