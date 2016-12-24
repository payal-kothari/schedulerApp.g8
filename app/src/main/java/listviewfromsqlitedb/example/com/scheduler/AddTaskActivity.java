package listviewfromsqlitedb.example.com.scheduler;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by payalkothari on 12/22/16.
 */

public class AddTaskActivity extends Activity {
    DatabaseManager adapter;
    DatabaseHelper helper;
    public TextView startTime, endTime, taskName;
    Button btnSubmit, btnReset;
    private static final int  REQUEST_CODE_START_TIME = 1;
    private static final int  REQUEST_CODE_END_TIME = 2;
    private String format = "";
    private Calendar calendar;
    TableRow startTimeRow, endTimeRow;
    int currentHour;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        startTime = (TextView) findViewById(R.id.tx_startTime);
        endTime = (TextView) findViewById(R.id.tx_endTime);
        startTimeRow = (TableRow) findViewById(R.id.startTimeRow);
        endTimeRow = (TableRow) findViewById(R.id.endTimeRow);
        taskName = (EditText) findViewById(R.id.et_taskName);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        adapter = new DatabaseManager(this);

        startTimeRow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        showStartTime(selectedHour, selectedMinute);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        endTimeRow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        showEndTime(selectedHour, selectedMinute);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String start = startTime.getText().toString();
                String end = endTime.getText().toString();
                String task = taskName.getText().toString();
                long val = adapter.insertDetails(start, end, task);
                finish();
            }
        });
    }

    public void showStartTime(int hour, int min) {
        String minstr;
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        if(min >= 10){
            minstr = String.valueOf(min);
        }else {
            minstr = "0" + String.valueOf(min);
        }
        startTime.setText(new StringBuilder().append(hour).append(" : ").append(minstr)
                .append(" ").append(format));
    }

    public void showEndTime(int hour, int min) {
        String minstr;
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        if(min >= 10){
            minstr = String.valueOf(min);
        }else {
            minstr = "0" + String.valueOf(min);
        }
        endTime.setText(new StringBuilder().append(hour).append(" : ").append(minstr)
                .append(" ").append(format));
    }

}