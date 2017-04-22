package com.song.judyplan.activitie;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.song.judyplan.R;
import com.song.judyplan.adapter.PlanAdapter;
import com.song.judyplan.entity.Plan;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView mRvList;
    private FloatingActionButton mFabAdd;
    private List<Plan> mPlanList;
    private PlanAdapter mPlanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);

        mPlanList = new ArrayList();
        mPlanList.add(new Plan("第一条计划", true));
        mPlanList.add(new Plan("第二条计划", false));
        mPlanList.add(new Plan("第一条计划", false));

        mRvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePlanList();
    }

    private void updatePlanList() {
        if (mPlanAdapter == null) {
            mPlanAdapter = new PlanAdapter(getApplicationContext(), mPlanList);
            mRvList.setAdapter(mPlanAdapter);
        } else {
            mPlanAdapter.notifyDataSetChanged();
        }
    }
}
