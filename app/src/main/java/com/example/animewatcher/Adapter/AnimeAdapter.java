package com.example.animewatcher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animewatcher.Activity.AnimeDataPage;
import com.example.animewatcher.Activity.MainScreen;
import com.example.animewatcher.Activity.VideoPlayer;
import com.example.animewatcher.Model.Anime;
import com.example.animewatcher.Model.Episode;
import com.example.animewatcher.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.recycleViewHolder> {
    List<Anime> animeList;
    String id;
    List<Episode> episodeList;
    public AnimeAdapter(List<Anime> anime) {
        this.animeList = anime;
    }


    @NonNull
    @Override
    public recycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.anime_card_view, parent, false);
        return new recycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        Query query = FirebaseDatabase.getInstance().getReference().child("anime").child(String.valueOf(position)).child("episodes").orderByChild("id");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                Map<String,Episode> episodeMap = new HashMap<>();
                for (DataSnapshot child : children) {
                    Episode value = child.getValue(Episode.class);
                    value.setUid(child.getKey());
                    episodeMap.put(child.getKey(),value);
                }
                episodeList = new ArrayList<>(episodeMap.values());
                holder.title.setText(anime.getTitle());
                holder.desc.setText(anime.getDesc());
                id=anime.getUid();
                Glide.with(holder.thumbnail.getContext()).load(anime.getImage()).into(holder.thumbnail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }


    class recycleViewHolder extends RecyclerView.ViewHolder {
    ImageView thumbnail;
    TextView title,desc;
    CardView cardView;

        public recycleViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.anime_card_view);
            title = itemView.findViewById(R.id.anime_title);
            desc = itemView.findViewById(R.id.desc_anime);
            thumbnail = itemView.findViewById(R.id.anime_image);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                Anime anime = animeList.get(position);
                anime.setEpisodes(episodeList);
                Context context = view.getContext();
                Intent intent = new Intent(context,AnimeDataPage.class);
                intent.putExtra("id",anime.getUid());
                intent.putExtra("title",anime.getTitle());
                intent.putExtra("desc",anime.getDesc());
                intent.putExtra("image",anime.getImage());
                intent.putParcelableArrayListExtra("episodes", (ArrayList) anime.getEpisodes());
                context.startActivity(intent);
            });
        }
    }

}
