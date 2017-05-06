package com.song.judyplan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.song.judyplan.Application.App;
import com.song.judyplan.R;
import com.song.judyplan.adapter.PlanAdapter;
import com.song.judyplan.entity.DaoSession;
import com.song.judyplan.entity.Plan;
import com.song.judyplan.entity.PlanDao;

import org.greenrobot.greendao.query.Query;

public class ListActivity extends AppCompatActivity implements View.OnClickListener, PlanAdapter.OnItemClickListener {
    private RecyclerView mRvList;
    private FloatingActionButton mFabAdd;
    private PlanAdapter mPlanAdapter;
    private PlanDao mPlanDao;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        mPlanDao = daoSession.getPlanDao();

        mRvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mPlanAdapter = new PlanAdapter(getApplicationContext());
        mRvList.setAdapter(mPlanAdapter);

        mFabAdd.setOnClickListener(this);
        mPlanAdapter.setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePlanList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add:
                addPlan();
                break;
        }
    }

    private void addPlan() {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        startActivity(intent);
    }

    private void updatePlanList() {
        Query<Plan> planQuery = mPlanDao.queryBuilder().orderAsc(PlanDao.Properties.Date).build();
        mPlanAdapter.setPlanList(planQuery.list());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(Plan plan) {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("plan", plan);
        startActivity(intent);
    }
}
