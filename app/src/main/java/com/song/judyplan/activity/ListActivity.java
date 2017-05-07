package com.song.judyplan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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

import java.util.Calendar;

import static com.song.judyplan.entity.PlanDao.Properties.Date;

public class ListActivity extends AppCompatActivity implements View.OnClickListener, PlanAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRvList;
    private FloatingActionButton mFabAdd;
    private PlanAdapter mPlanAdapter;
    private PlanDao mPlanDao;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initContent();

        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

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
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void initContent() {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("year")) {
            mYear = bundle.getInt("year");
            mMonth = bundle.getInt("month");
            mDayOfMonth = bundle.getInt("dayOfMonth");
        }
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
        Bundle bundle = new Bundle();
        bundle.putInt("year", mYear);
        bundle.putInt("month", mMonth);
        bundle.putInt("dayOfMonth", mDayOfMonth);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void updatePlanList() {
        Query<Plan> planQuery = mPlanDao.queryBuilder().
                where(PlanDao.Properties.DayOfMonth.eq(mDayOfMonth)).
                orderAsc(Date).build();
        mPlanAdapter.setPlanList(planQuery.list());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_date:
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return false;
    }

    @Override
    public void onLeftItemClick(Plan plan) {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("plan", plan);
        startActivity(intent);
    }

    @Override
    public void onRightItemClick(Plan plan) {

    }
}
