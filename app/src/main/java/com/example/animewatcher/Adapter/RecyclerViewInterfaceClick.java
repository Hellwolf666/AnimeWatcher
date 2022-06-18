package com.example.animewatcher.Adapter;

import com.google.firebase.database.DataSnapshot;

public interface RecyclerViewInterfaceClick {
    void onItemClick(int position, String type, DataSnapshot dataSnapshot);

}
