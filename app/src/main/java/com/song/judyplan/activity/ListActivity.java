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
import android.widget.TextView;

import com.song.judyplan.Application.App;
import com.song.judyplan.R;
import com.song.judyplan.adapter.PlanAdapter;
import com.song.judyplan.entity.DaoSession;
import com.song.judyplan.entity.Plan;
import com.song.judyplan.entity.PlanDao;
import com.song.judyplan.utils.Constants;

import org.greenrobot.greendao.query.Query;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.song.judyplan.entity.PlanDao.Properties.Date;
import static com.song.judyplan.entity.PlanDao.Properties.IsCompleted;

public class ListActivity extends AppCompatActivity implements View.OnClickListener, PlanAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRvList;
    private PlanAdapter mPlanAdapter;
    private PlanDao mPlanDao;
    private DrawerLayout mDrawerLayout;
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private int mToken;
    private TextView mTvEmpty;
    private TextView mToolBarSubTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initContent();

        mRvList = findViewById(R.id.rv_list);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        TextView toolBarTitle = toolbar.findViewById(R.id.tl_title);
        mToolBarSubTitle = toolbar.findViewById(R.id.tl_subtitle);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mTvEmpty = findViewById(R.id.tv_empty);

        String[] planType = getResources().getStringArray(R.array.plan_type);
        String[] yearMonthDay = getResources().getStringArray(R.array.year_month_day);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            if (mToken == Constants.token_date) {
                toolBarTitle.setText(mYear+ yearMonthDay[0]+mMonth+ yearMonthDay[1]+mDayOfMonth+
                        yearMonthDay[2]);
            } else {
                toolBarTitle.setText(planType[mToken]);
            }
        }

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        mPlanDao = daoSession.getPlanDao();

        mRvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mPlanAdapter = new PlanAdapter(getApplicationContext());
        mRvList.setAdapter(mPlanAdapter);

        fabAdd.setOnClickListener(this);
        mPlanAdapter.setOnItemClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

        if (mToken == Constants.token_today || mToken == Constants.token_tomorrow) {
            fabAdd.setVisibility(View.VISIBLE);
        } else {
            fabAdd.setVisibility(View.GONE);
        }
    }

    private void initContent() {
        mToken = getIntent().getIntExtra("token", Constants.token_today);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("year")) {
            mYear = bundle.getInt("year");
            mMonth = bundle.getInt("month");
            mDayOfMonth = bundle.getInt("dayOfMonth");
        } else if (mToken == Constants.token_tomorrow) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(new Date().getTime()+86400000));
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
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


    private void deletePlan(Plan plan) {
        mPlanDao.delete(plan);
        updatePlanList();
    }

    private void updatePlanList() {
        Query<Plan> planQuery;
        switch (mToken) {
            case Constants.token_month:
                planQuery = mPlanDao.queryBuilder().
                        where(PlanDao.Properties.Year.eq(mYear)).
                        where(PlanDao.Properties.Month.eq(mMonth)).
                        orderAsc(IsCompleted).
                        orderAsc(Date).build();
                break;

            case Constants.token_completed:
                planQuery = mPlanDao.queryBuilder().
                        where(PlanDao.Properties.IsCompleted.eq(true)).
                        orderAsc(IsCompleted).
                        orderAsc(Date).build();
                break;

            case Constants.token_uncompleted:
                planQuery = mPlanDao.queryBuilder().
                        where(PlanDao.Properties.IsCompleted.eq(false)).
                        orderAsc(IsCompleted).
                        orderAsc(Date).build();
                break;
            default:
                planQuery = mPlanDao.queryBuilder().
                        where(PlanDao.Properties.Year.eq(mYear)).
                        where(PlanDao.Properties.Month.eq(mMonth)).
                        where(PlanDao.Properties.DayOfMonth.eq(mDayOfMonth)).
                        orderAsc(Date).build();
                break;
        }
        List<Plan> planList = planQuery.list();
        mPlanAdapter.setPlanList(planList);
        if (planList.size() == 0) {
            mRvList.setVisibility(View.GONE);
            mTvEmpty.setVisibility(View.VISIBLE);
            mToolBarSubTitle.setVisibility(View.INVISIBLE);
        } else {
            mRvList.setVisibility(View.VISIBLE);
            mTvEmpty.setVisibility(View.GONE);
            mToolBarSubTitle.setVisibility(View.VISIBLE);
            mToolBarSubTitle.setText(String.valueOf(planList.size()));
        }
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
            case R.id.nav_today:
                intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("token", Constants.token_today);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_tomorrow:
                intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("token", Constants.token_tomorrow);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_month:
                intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("token", Constants.token_month);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_completed:
                intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("token", Constants.token_completed);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_uncompleted:
                intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("token", Constants.token_uncompleted);
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
        deletePlan(plan);
    }

}
