package com.example.wudfilm.wudfilm;

/**
 * Created by susanyang on 10/22/16.
 */

public class Movie {

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
        public Movie(String title, int runtime, int showtime,String synopsis, String MPAArating, String linkM,
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


