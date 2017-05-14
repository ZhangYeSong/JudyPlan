package com.song.judyplan.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.song.judyplan.Application.App;
import com.song.judyplan.R;
import com.song.judyplan.entity.DaoSession;
import com.song.judyplan.entity.Plan;
import com.song.judyplan.entity.PlanDao;

import java.util.Date;

import static com.song.judyplan.R.id.fab_add;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtTitle;
    private EditText mEtContent;
    private FloatingActionButton mFabSave;
    private PlanDao mPlanDao;
    private boolean isNewPlan = true;
    private Plan mPlan;
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mFabSave = (FloatingActionButton) findViewById(fab_add);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mFabSave.setOnClickListener(this);

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        mPlanDao = daoSession.getPlanDao();

        initContent();
    }

    private void initContent() {
        mPlan = getIntent().getParcelableExtra("plan");
        if (mPlan != null) {
            isNewPlan = false;
            mEtTitle.setText(mPlan.getText());
            if (mPlan.getContent() != null) {
                mEtContent.setText(mPlan.getContent());
            }
        } else {
            isNewPlan = true;
            Bundle bundle = getIntent().getExtras();
            mYear = bundle.getInt("year");
            mMonth = bundle.getInt("month");
            mDayOfMonth = bundle.getInt("dayOfMonth");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case fab_add:
                savePlan(mEtTitle.getText().toString(), mEtContent.getText().toString());
                break;
        }
    }

    private void savePlan(String title, String content) {
        if (TextUtils.isEmpty(title)) {
            finish();
            Toast.makeText(getApplicationContext(), "标题为空的计划不会被保存", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isNewPlan) {
            Plan plan = new Plan();
            plan.setText(title);
            plan.setContent(content);
            plan.setDate(new Date());
            plan.setYear(mYear);
            plan.setMonth(mMonth);
            plan.setDayOfMonth(mDayOfMonth);
            mPlanDao.insert(plan);
        } else {
            mPlan.setText(title);
            mPlan.setContent(content);
            mPlan.setDate(new Date());
            mPlanDao.update(mPlan);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mFabSave.performClick();
                break;
        }
        return true;
    }
}
