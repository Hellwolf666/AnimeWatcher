package com.example.animewatcher.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.animewatcher.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VideoPlayer extends AppCompatActivity {
    String episodeUrl;

    private ExoPlayer player;
    PlayerView playerView;

    ProgressBar progressBar;


    View hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        episodeUrl = getIntent().getStringExtra("ep_url");

        hide = getWindow().getDecorView();

        playerView = findViewById(R.id.player);

        Player();

    }

    private void Player() {
        progressBar = findViewById(R.id.player_progress);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        player = new ExoPlayer.Builder(this).setTrackSelector(trackSelector).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(episodeUrl);
        player.setMediaItem(mediaItem);


        player.prepare();
        playerView.setKeepScreenOn(true);
        player.setPlayWhenReady(true);

        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
    }


}
