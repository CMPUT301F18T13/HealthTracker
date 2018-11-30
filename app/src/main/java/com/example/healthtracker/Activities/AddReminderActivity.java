package com.example.healthtracker.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.AlarmReceiver;
import com.example.healthtracker.R;
import com.example.healthtracker.View.AddProblemView;
import com.example.healthtracker.View.AddorEditRecordView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddReminderActivity extends AppCompatActivity {


    //private EditText reminderTime;
    private Spinner repeatChoices;
    private String time = "";
    private String r_choice = "";
    private int r_hour;
    private int r_minute;

    private TimePicker timePicker;
    private Calendar calendar;
    private int currentHour;
    private int currentMin;
    private String amPm;

    private String idea;

    private int notificationId = 1;
    private AlarmManager alarm;
    private PendingIntent alarmIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        // Set Onclick Listener
        //delete_r = findViewById(R.id.delete_reminder);//.setOnClickListener(this);
        //save_r = findViewById(R.id.save_reminder);//.setOnClickListener(this);

        timePicker = findViewById(R.id.r_timepicker);

        // Set notificationId & mode.
        Intent intent = new Intent(AddReminderActivity.this, AlarmReceiver.class);
        intent.putExtra("notificationID", notificationId);
        intent.putExtra("todo", "Hello!!!");

        //getBroadcast(context, requestCode, intent, flags)
        alarmIntent = PendingIntent.getBroadcast(AddReminderActivity.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);


        /*
        //When choose repeat method
        repeatChoices = findViewById(R.id.choose_repeat);
        ArrayList<String> choices = new ArrayList<>();
        choices.add(0,"Choose one mode");
        choices.add("Once");
        choices.add("Every Monday");
        choices.add("Every Tuesday");
        choices.add("Every Wednesday");
        choices.add("Every Thursday");
        choices.add("Every Friday");
        choices.add("Every Saturday");
        choices.add("Every Sunday");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,choices);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatChoices.setAdapter(dataAdapter);


        repeatChoices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //String[] choices = getResources().getStringArray(R.array.repeat_choice);
                if (parent.getItemAtPosition(pos).equals("Choose one mode")) {
                }
                else {
                    Toast.makeText(AddReminderActivity.this, "You choose " + parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
                    r_choice = parent.getItemAtPosition(pos).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddReminderActivity.this, "Please choose one mode!" , Toast.LENGTH_SHORT).show();
                // TODO Auto-generated method stub
            }
        });
*/

/*
        //When choose repeat time
        reminderTime = findViewById(R.id.reminder_time);

        reminderTime.setText("");

        reminderTime.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMin = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(AddReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(hourOfDay >= 12){
                            amPm = "PM";
                        }else{
                            amPm = "AM";
                        }
                        reminderTime.setText(String.format(":%02d%02d", hourOfDay + ":" + minutes) + amPm);
                    }
                }, currentHour, currentMin, false);

                timePickerDialog.show();

            }
        });


        //time = reminderTime.getText().toString();

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        mCalendar.set(Calendar.HOUR_OF_DAY, r_hour);
        mCalendar.set(Calendar.MINUTE, r_minute);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);

        long systemTime = System.currentTimeMillis();
        long selectTime = mCalendar.getTimeInMillis();

        if(systemTime > selectTime) {
            mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }


        //CITATION: https://blog.csdn.net/fan7983377/article/details/51993793
        Intent intent = new Intent(AddReminderActivity.this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(AddReminderActivity.this, 0, intent, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

        switch(r_choice){
            case "Once":
                am.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);//remind Once
                //break;
            case "Every Day":
                am.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), (1000 * 60 * 60 * 24), pi);//Repeat every day
                //break;
            case "Every Monday":
            case "Every Tuesday":
            case "Every Wednesday":
            case "Every Thursday":
            case "Every Friday":
            case "Every Saturday":
            case "Every Sunday":
                am.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), (1000 * 60 * 60 * 24), pi);//Repeat every day


        }

*/


    }

    @Override
    public void onBackPressed() {
        if (time != "" || r_choice != "") {
            AlertDialog.Builder ab = new AlertDialog.Builder(AddReminderActivity.this);
            ab.setMessage("Warning. Changes have been made to the reminder." + "\n" + "Returning to the last screen will not save changes.");
            ab.setCancelable(true);
            // Set a button to return to the Home screen and don't save changes
            ab.setNeutralButton("Exit And Lose Changes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            // set a button which will close the alert dialog
            ab.setNegativeButton("Return to Reminder", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            // show the alert dialog on the screen
            ab.show();
        } else {
            finish();
        }
    }

/*
    @Override
    public void onClick(View v) {

        //repeatChoices = findViewById(R.id.reminder_time);
        timePicker = findViewById(R.id.r_timepicker);

        // Set notificationId & mode.
        Intent intent = new Intent(AddReminderActivity.this, AlarmReceiver.class);
        intent.putExtra("notificationID", notificationId);
        intent.putExtra("todo", "Hello!!!");

        //getBroadcast(context, requestCode, intent, flags)
        PendingIntent alarmIntent = PendingIntent.getBroadcast(AddReminderActivity.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent backIntent = new Intent(AddReminderActivity.this, AddProblemView.class);
        Intent backToEditIntent = new Intent(AddReminderActivity.this, EditProblem.class);



        switch (v.getId()){
            case R.id.save_reminder:


                break;

            case R.id.delete_reminder:

                break;
        }

    }
  */

    public void saveButton(View view){
        int h = timePicker.getCurrentHour();
        if(h == 0){
            h = 12;
        }
        else if(h == 12){
            h = 24;
        }
        int min = timePicker.getCurrentMinute();

        Log.d("!!!!!!!!!!!1HOUR IS :",String.valueOf(h));
        Log.d("!!!!!!!!!!!1MINUTE IS :",String.valueOf(min));


        //Create time
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, h);
        startTime.set(Calendar.MINUTE, min);
        startTime.set(Calendar.SECOND, 0);
        long alarmStartTime = startTime.getTimeInMillis();



        // Set alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarm.setExact(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
        } else {
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime, (1000 * 60 * 60 * 24),alarmIntent);
        }

        //alarm.setExact(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
        Boolean alarmUp = (PendingIntent.getBroadcast(this,0,new Intent()))
        Toast.makeText(this, "Reminder Done!", Toast.LENGTH_SHORT).show();
        AddReminderActivity.this.finish();

    }

    public void deleteButton(View view){
        alarm.cancel(alarmIntent);
        Toast.makeText(this, "Reminder Delete!", Toast.LENGTH_SHORT).show();
        AddReminderActivity.this.finish();
    }

}
