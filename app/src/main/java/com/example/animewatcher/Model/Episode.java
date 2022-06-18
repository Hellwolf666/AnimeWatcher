package com.example.animewatcher.Model;



import java.io.Serializable;

public class Episode implements Serializable {
    String ep_no,ep_url,uid;

    public Episode(String ep_no, String ep_url, String uid) {
        this.ep_no = ep_no;
        this.ep_url = ep_url;
        this.uid = uid;
    }

    public Episode() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEp_no() {
        return ep_no;
    }

    public void setEp_no(String ep_no) {
        this.ep_no = ep_no;
    }

    public String getEp_url() {
        return ep_url;
    }

    public void setEp_url(String ep_url) {
        this.ep_url = ep_url;
    }

}
