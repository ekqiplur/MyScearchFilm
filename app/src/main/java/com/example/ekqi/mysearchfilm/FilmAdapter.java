package com.example.ekqi.mysearchfilm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmAdapter extends BaseAdapter {

    private ArrayList<FilmItem> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    private String mdescripsi;

    public FilmAdapter(Context context){
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<FilmItem>items){
        mData = items;
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
    }

    public void onAdditem(final FilmItem item){
        mData.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    public FilmItem getItem(int position){
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        String API_KEY = "a798d108dce8a686f6cb3a29ac251992";
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.film_item, null);
            holder.textViewNamaFilm= (TextView)convertView.findViewById(R.id.txtNamaFilm);
            holder.textViewDescription = (TextView)convertView.findViewById(R.id.txtDesc);
            holder.textViewImage = (ImageView)convertView.findViewById(R.id.imgFilmPic);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewNamaFilm.setText(mData.get(position).getTitle());
        holder.textViewDescription.setText(mData.get(position).getOverview());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + mData.get(position).getPoster_path()).placeholder(context.getResources().getDrawable(R.drawable.gmbr)).error(context.getResources().getDrawable(R.drawable.gmbr)).into(holder.textViewImage);

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewNamaFilm;
        TextView textViewDescription;
        ImageView textViewImage;
    }
}
