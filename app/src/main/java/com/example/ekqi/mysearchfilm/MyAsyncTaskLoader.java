package com.example.ekqi.mysearchfilm;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<FilmItem>> {
    private ArrayList<FilmItem> mData;
    private boolean mHasResult = false;

    private String mMovie_name;
    public MyAsyncTaskLoader(final Context context, String movieName) {
        super(context);
        onContentChanged();
        this.mMovie_name = movieName;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<FilmItem> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private static final String API_KEY = "a798d108dce8a686f6cb3a29ac251992";

    @Override
    public ArrayList<FilmItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<FilmItem> filmItemses = new ArrayList<>();
        String url = "";
        String url1= "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" +mMovie_name;
        String url2 = "https://api.themoviedb.org/3/discover/movie?api_key=" +API_KEY+ "&sort_by=popularity.desc";

        if (TextUtils.isEmpty(mMovie_name)){
            url = url2;
        }else {
            url = url1;
        }

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject results = list.getJSONObject(i);
                        FilmItem filmItems = new FilmItem(results);
                        filmItemses.add(filmItems);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return filmItemses;

    }

    protected void onReleaseResources(ArrayList<FilmItem> data) {
        //nothing to do.
    }

}
