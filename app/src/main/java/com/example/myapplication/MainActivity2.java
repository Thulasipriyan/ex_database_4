package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    Button b1, b2, b3, b4;
    TextView tName, tAge, tGender, tEmail, tPhone, tAddress;
    SQLiteDatabase db;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Linking UI components
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);

        tName = findViewById(R.id.tName);
        tAge = findViewById(R.id.tAge);
        tGender = findViewById(R.id.tGender);
        tEmail = findViewById(R.id.tEmail);
        tPhone = findViewById(R.id.tPhone);
        tAddress = findViewById(R.id.tAddress);

        // Open database
        db = openOrCreateDatabase("BioData_DB", MODE_PRIVATE, null);

        // Fetch records
        c = db.rawQuery("SELECT * FROM BioData", null);

        if (c.getCount() == 0) {
            Toast.makeText(this, "No Records Found", Toast.LENGTH_SHORT).show();
        } else {
            c.moveToFirst();
            displayRecord();
        }

        // First Button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    displayRecord();
                }
            }
        });

        // Last Button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c.getCount() > 0) {
                    c.moveToLast();
                    displayRecord();
                }
            }
        });

        // Next Button
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c.getCount() > 0) {
                    if (!c.isLast()) {
                        c.moveToNext();
                    } else {
                        Toast.makeText(MainActivity2.this, "This is Last Record", Toast.LENGTH_SHORT).show();
                    }
                    displayRecord();
                }
            }
        });

        // Previous Button
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c.getCount() > 0) {
                    if (!c.isFirst()) {
                        c.moveToPrevious();
                    } else {
                        Toast.makeText(MainActivity2.this, "This is First Record", Toast.LENGTH_SHORT).show();
                    }
                    displayRecord();
                }
            }
        });
    }

    // Method to display current record
    private void displayRecord() {
        if (c != null && c.getCount() > 0) {
            tName.setText("Name: " + c.getString(0));
            tAge.setText("Age: " + c.getString(1));
            tGender.setText("Gender: " + c.getString(2));
            tEmail.setText("Email: " + c.getString(3));
            tPhone.setText("Phone: " + c.getString(4));
            tAddress.setText("Address: " + c.getString(5));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (c != null) c.close();
        if (db != null) db.close();
    }
}
