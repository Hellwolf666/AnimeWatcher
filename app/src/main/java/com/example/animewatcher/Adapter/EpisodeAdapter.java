package com.example.animewatcher.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animewatcher.Activity.VideoPlayer;
import com.example.animewatcher.Model.Episode;
import com.example.animewatcher.R;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {
    List<Episode> episodeList;
    Context context;

    public EpisodeAdapter(Context context,List<Episode> episodes) {
        this.context =context;
        this.episodeList = episodes;
    }
    @NonNull
    @Override
    public EpisodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.episode_item,parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Episode episode = episodeList.get(position);
        holder.episodeButton.setText("Play Episode:" +episode.getEp_no());
        holder.position = position;
        holder.episode = episode;
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        Button episodeButton;
        int position;
        Episode episode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeButton= itemView.findViewById(R.id.episode_button);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),VideoPlayer.class);
                    intent.putExtra("ep_url",episode.getEp_url());
                    view.getContext().startActivity(intent);
                }
            });
        }


    }
 }
