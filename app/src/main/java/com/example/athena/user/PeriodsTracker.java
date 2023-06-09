package com.example.athena.user;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athena.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PeriodsTracker extends AppCompatActivity {

    private EditText editTextLastPeriod;
    private TextView textViewResult;
    private Button buttonPredictPeriod;
    private Button buttonPredictOvulation;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periods_tracker);

        editTextLastPeriod = findViewById(R.id.editTextLastPeriod);
        textViewResult = findViewById(R.id.textViewResult);
        buttonPredictPeriod = findViewById(R.id.buttonPredictPeriod);
        buttonPredictOvulation = findViewById(R.id.buttonPredictOvulation);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        buttonPredictPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predictPeriodStartDate();
            }
        });

        buttonPredictOvulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predictOvulationDate();
            }
        });


    }

    private void predictPeriodStartDate() {
        String lastPeriod = editTextLastPeriod.getText().toString().trim();
        if (lastPeriod.isEmpty()) {
            Toast.makeText(this, "Please enter the date of your last period.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Date lastPeriodDate = dateFormat.parse(lastPeriod);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lastPeriodDate);
            calendar.add(Calendar.DAY_OF_MONTH, 28); // Assuming a 28-day cycle

            String predictedPeriodStartDate = dateFormat.format(calendar.getTime());
            textViewResult.setText("Predicted Next Period Start Date: " + predictedPeriodStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format.", Toast.LENGTH_SHORT).show();
        }
    }

    private void predictOvulationDate() {
        String lastPeriod = editTextLastPeriod.getText().toString().trim();
        if (lastPeriod.isEmpty()) {
            Toast.makeText(this, "Please enter the date of your last period.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Date lastPeriodDate = dateFormat.parse(lastPeriod);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lastPeriodDate);
            calendar.add(Calendar.DAY_OF_MONTH, 14); // Assuming ovulation occurs 14 days before the next period

            String predictedOvulationDate = dateFormat.format(calendar.getTime());
            textViewResult.setText("Predicted Ovulation Date: " + predictedOvulationDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format.", Toast.LENGTH_SHORT).show();
        }
    }


    public void back(View view){

        startActivity(new Intent(this, UserDashboard.class));
        finish();
    }
}