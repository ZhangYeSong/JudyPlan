package com.song.judyplan;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class ListActivity extends AppCompatActivity {
    private RecyclerView mRvList;
    private FloatingActionButton mFabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
    }
}
