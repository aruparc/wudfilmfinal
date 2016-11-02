package com.example.wudfilm.wudfilm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieListFragment())
                    .commit();
        }
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

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.email:
                setContentView(R.layout.activity_main);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new EmailFragment())
                        .commit();
                return true;
            case R.id.home:
                setContentView(R.layout.activity_main);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new MovieListFragment())
                        .commit();
                return true;
            case R.id.settings:
                setContentView(R.layout.activity_main);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new SettingsFragment())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}







