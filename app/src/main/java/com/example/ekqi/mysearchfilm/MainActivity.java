package com.example.ekqi.mysearchfilm;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cz.msebera.android.httpclient.util.TextUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<FilmItem>> {
    ListView listview ;
    FilmAdapter adapter;
    EditText edtCari;
    Button btnCari;
    ProgressBar progressbar;
    TextView tvResult;

    String result = "Showing For ";

    static final String EXTRAS_FILM = "EXTRAS_FILM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new FilmAdapter(this);
        adapter.notifyDataSetChanged();
        listview = (ListView)findViewById(R.id.listView);

        tvResult = (TextView)findViewById(R.id.tv_result);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilmItem item = (FilmItem)parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                intent.putExtra(DetailActivity.EXTRA_TITLE, item.getTitle());
                intent.putExtra(DetailActivity.EXTRA_OVERVIEW, item.getOverview());
                intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, item.getRelease_date());
                intent.putExtra(DetailActivity.EXTRA_LANGUAGE, item.getLang());
                intent.putExtra(DetailActivity.EXTRA_POSTER_JPG, item.getPoster_path());
                intent.putExtra(DetailActivity.EXTRA_BACKCOVER, item.getBackdrop_path());
                intent.putExtra(DetailActivity.EXTRA_RATE_COUNT, item.getRate_count());
                intent.putExtra(DetailActivity.EXTRA_RATE, item.getVote_average());

                startActivity(intent);
            }
        });

        edtCari = (EditText)findViewById(R.id.edt_cari);
        btnCari = (Button)findViewById(R.id.btn_cari);

        progressbar = (ProgressBar)findViewById(R.id.progressbar);

        btnCari.setOnClickListener(myListener);

        String film = edtCari.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM,film);
        getLoaderManager().initLoader(0, bundle, this);

    }

    @Override
    public Loader<ArrayList<FilmItem>> onCreateLoader(int id, Bundle args) {
        String mfilm = "";
        if (args != null){
            mfilm = args.getString(EXTRAS_FILM);
            tvResult.setText(result +"Result : " + mfilm);  // jika variable Movietitle memiliki value,
            if (mfilm.isEmpty()){
                tvResult.setText(result +": Film terpopuler"); // jika  variable Movietitle tidak memiliki value (null)
            }
        }

        progressbar.setVisibility(View.VISIBLE);
        if (progressbar.getVisibility() == View.VISIBLE){
            listview.setVisibility(View.GONE);
            tvResult.setVisibility(View.GONE);
            btnCari.setVisibility(View.GONE);
            edtCari.setVisibility(View.GONE);
        }
        return new MyAsyncTaskLoader(this,mfilm);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilmItem>> loader, ArrayList<FilmItem> data) {
        adapter.setData(data);
        progressbar.setVisibility(View.GONE);
        if (progressbar.getVisibility() == View.GONE){
            listview.setVisibility(View.VISIBLE);
            tvResult.setVisibility(View.VISIBLE);
            btnCari.setVisibility(View.VISIBLE);
            edtCari.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmItem>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String film = edtCari.getText().toString();

            if (TextUtils.isEmpty(film))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_FILM,film);
            getLoaderManager().restartLoader(0,bundle,MainActivity.this);
        }
    };
}
