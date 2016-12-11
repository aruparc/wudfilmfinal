package com.example.wudfilm.wudfilm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;



public class EmailFormWebView extends AppCompatActivity {

    private WebView mWebview;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mWebview = new WebView(this);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });

        mWebview.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSceCox-VyW7BiECCkaK3oWAtjLyNQGfrP_MNdFfJL_6jphJ1Q/formResponse");
        setContentView(mWebview);
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
                getSupportFragmentManager().beginTransaction();
                Intent intent = new Intent(this, EmailFormWebView.class);
                this.startActivity(intent);
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