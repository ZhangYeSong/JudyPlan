package com.song.judyplan.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.song.judyplan.Application.App;
import com.song.judyplan.R;
import com.song.judyplan.entity.DaoSession;
import com.song.judyplan.entity.Plan;
import com.song.judyplan.entity.PlanDao;

import java.util.Date;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtTitle;
    private EditText mEtContent;
    private FloatingActionButton mFabAdd;
    private PlanDao mPlanDao;
    private boolean isNewPlan = true;
    private Plan mPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);

        mFabAdd.setOnClickListener(this);

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
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add:
                savePlan(mEtTitle.getText().toString(), mEtContent.getText().toString());
                break;
        }
    }

    private void savePlan(String title, String content) {
        if (isNewPlan) {
            Plan plan = new Plan();
            plan.setText(title);
            plan.setContent(content);
            plan.setDate(new Date());
            mPlanDao.insert(plan);
        } else {
            mPlan.setText(title);
            mPlan.setContent(content);
            mPlan.setDate(new Date());
            mPlanDao.update(mPlan);
        }
        finish();
    }
}
