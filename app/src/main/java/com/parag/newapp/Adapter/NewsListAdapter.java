package com.parag.newapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parag.newapp.Activity.WebViewActivity;
import com.parag.newapp.Model.NewListModel;
import com.parag.newapp.R;

import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private List<NewListModel.Article> marketList;
    Context context;


    public NewsListAdapter(Context context) {
        this.context = context;
        marketList = new ArrayList<>();
    }

    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_layout, parent, false);

        NewsListAdapter.ViewHolder viewHolder = new NewsListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsListAdapter.ViewHolder holder, int position) {
        NewListModel.Article article = marketList.get(position);
        holder.tvTittle.setText(article.getTitle());
        holder.tvDescription.setText(article.getDescription());
        holder.tvTime.setText(article.getPublishedAt());

        Glide.with(context)
                .load(article.getUrlToImage())
                .into(holder.ivImage);


        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(context, WebViewActivity.class);
                x.putExtra("url",article.getUrl());
                context.startActivity(x);
            }
        });

//        if (market.coinName.equalsIgnoreCase("eth")) {
//            holder.cardView.setCardBackgroundColor(Color.GRAY);
//        } else {
//            holder.cardView.setCardBackgroundColor(Color.GREEN);
//        }
    }

    @Override
    public int getItemCount() {
        return marketList.size();
    }

    public void setData(List<NewListModel.Article> data) {
        this.marketList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llMain;
        TextView tvTittle, tvDescription, tvTime;
        ImageView ivImage;

        public ViewHolder(View view) {
            super(view);

            llMain = view.findViewById(R.id.llMain);
            tvTittle = view.findViewById(R.id.tvTittle);
            tvDescription = view.findViewById(R.id.tvDescription);
            tvTime = view.findViewById(R.id.tvTime);
            ivImage = view.findViewById(R.id.ivImage);
        }
    }
}