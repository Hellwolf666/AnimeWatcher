package com.example.animewatcher.Model;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class Anime {
    String title, desc, image, uid ;
    List<Episode> episodes;

    public Anime() {
    }

    public Anime(String title, String desc, String image, String uid, List<Episode> episodes) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.uid = uid;
        this.episodes = episodes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void createEpisodeList() {
        this.episodes = new ArrayList<>();
    }
}
