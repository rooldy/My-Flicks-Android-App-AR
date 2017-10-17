package smy.sinattechnologie.flicks_apps;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import smy.sinattechnologie.flicks_apps.Models.Config;
import smy.sinattechnologie.flicks_apps.Models.Movie;

/**
 * Created by Alfonso on 09-Oct-17.
 */

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    ArrayList<Movie> movies;
    Config config;
    Context context;

    public MovieAdapter(ArrayList<Movie> movies) {

        this.movies = movies;
    }

    public Config getConfig() {

        return config;
    }

    public void setConfig(Config config) {

        this.config = config;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View movieView=inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        Movie movie=movies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverView.setText(movie.getOverview());
        boolean isPortrait=context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT;
        String imageUrl=null;

        if (isPortrait)
        {
            imageUrl=config.getImageUrl(config.getPosterSize(),movie.getPosterPath());
        }
        else {
                imageUrl=config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());
        }

        int placeholderId=isPortrait? R.drawable.flicks_movie_placeholder: R.drawable.flicks_backdrop_placeholder;
        ImageView imageView=isPortrait ? holder.ivMovie: holder.ivBackdropImage;

        Glide.with(context).load(imageUrl).bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                .placeholder(placeholderId)
                .error(placeholderId).into(imageView);
             }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public  static class  ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivMovie;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverView;


        public ViewHolder(View itemView) {
            super(itemView);
            ivMovie=(ImageView)itemView.findViewById(R.id.ivPosterImage);
            ivBackdropImage=(ImageView)itemView.findViewById(R.id.ivBackdropimage);
            tvTitle=(TextView)itemView.findViewById(R.id.tvTitle);
            tvOverView=(TextView)itemView.findViewById(R.id.tvOverView);
        }
    }
}
