package com.song.judyplan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

import com.song.judyplan.R;
import com.song.judyplan.utils.Constants;

/**
 *  An activity show calendar
 *  Create by song at 2017.4.19
 */
public class CalendarActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener {
    private CalendarView mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mCalendar = findViewById(R.id.calendar);
        mCalendar.setOnDateChangeListener(this);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("token", Constants.token_date);
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("dayOfMonth", dayOfMonth);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
