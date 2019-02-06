package com.example.ekqi.mysearchfilm;


import org.json.JSONObject;

public class FilmItem {

 private String title;
 private String overview;
 private String release_date;
 private String vote_average;
 private String rate_count;
 private String poster_path;
 private String backdrop_path;
 private String lang;


 public FilmItem(JSONObject object){
     try {
         String judul = object.getString("title");
         String descripsi = object.getString("overview");
         String relis = object.getString("release_date");
         String rating = object.getString("vote_average");
         String rate_count   = object.getString("vote_count");
         String image        = object.getString("poster_path");
         String lang = object.getString("original_language");
         String background = object.getString("backdrop_path");
         this.title = judul;
         this.overview = descripsi;
         this.poster_path = image;

         this.release_date = relis;
         this.lang = lang;
         this.vote_average = rating;
         this.rate_count = rate_count;
         this.backdrop_path = background;
     }catch (Exception e){
         e.printStackTrace();
     }
 }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRate_count() {
        return rate_count;
    }

    public void setRate_count(String rate_count) {
        this.rate_count = rate_count;
    }


    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
