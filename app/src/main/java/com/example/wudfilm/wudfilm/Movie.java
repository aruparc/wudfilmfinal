package com.example.wudfilm.wudfilm;

import android.graphics.Bitmap;

/**
 * Created by susanyang on 10/22/16.
 */

public class Movie {

        String title;
        String date;
        String runtime;
        String showtime;
        String synopsis;
        String linkYT;
        Bitmap img;

        //constructor
        public Movie(String title, String date, String runtime, String showtime) {
            this.title = title;
            this.date = date;
            this.runtime = runtime;
            this.showtime = showtime;
            this.synopsis = "";
        }

}


