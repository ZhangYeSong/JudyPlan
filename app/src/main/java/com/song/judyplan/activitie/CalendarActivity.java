package com.song.judyplan.activitie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.Toast;

import com.song.judyplan.R;

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
        mCalendar = (CalendarView) findViewById(R.id.calendar);
        mCalendar.setOnDateChangeListener(this);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Toast.makeText(getApplicationContext(), year + "年" + (month + 1) + "月" + dayOfMonth + "日",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ListActivity.class));
    }
}
