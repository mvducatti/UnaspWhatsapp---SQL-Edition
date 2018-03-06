package com.example.marcos.unaspwhatsapp_sqledition.Adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcos.unaspwhatsapp_sqledition.Model.Noticia;
import com.example.marcos.unaspwhatsapp_sqledition.Model.User;
import com.example.marcos.unaspwhatsapp_sqledition.R;

import java.util.List;

/**
 * Created by Marcos on 02/03/2018.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.UserViewHolder> {

    private List<Noticia> listNews;

    public NewsRecyclerAdapter(List<Noticia> listNews) {
        this.listNews = listNews;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_recyler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewNewsTitle.setText(listNews.get(position).getNewsTitle());
        holder.textViewNewsPost.setText(listNews.get(position).getNewsMessage());
    }

    @Override
    public int getItemCount() {
        Log.v(NewsRecyclerAdapter.class.getSimpleName(),""+listNews.size());
        return listNews.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewNewsTitle;
        public AppCompatTextView textViewNewsPost;

        public UserViewHolder(View view) {
            super(view);
            textViewNewsTitle = view.findViewById(R.id.textViewNewsTitle);
            textViewNewsPost = view.findViewById(R.id.textViewNewstPost);
        }
    }

    public void addItems(List<Noticia> listNews) {
        this.listNews.addAll(listNews);
    }

}


