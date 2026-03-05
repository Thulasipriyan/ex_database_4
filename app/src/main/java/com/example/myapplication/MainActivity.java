package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    TextInputEditText etName, etAge, etEmail, etPhone, etAddress;
    RadioGroup rgGender;
    Button bt1, bt2;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        rgGender = findViewById(R.id.rgGender);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);

        db = openOrCreateDatabase("BioData_DB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS BioData(name TEXT, age TEXT, gender TEXT, email TEXT, phone TEXT, address TEXT)");

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String address = etAddress.getText().toString();

                int selectedGenderId = rgGender.getCheckedRadioButtonId();
                RadioButton rbGender = findViewById(selectedGenderId);
                String gender = rbGender != null ? rbGender.getText().toString() : "";

                if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                db.execSQL("INSERT INTO BioData VALUES('" + name + "','" + age + "','" + gender + "','" + email + "','" + phone + "','" + address + "')");
                Toast.makeText(MainActivity.this, "Bio Data Saved Successfully", Toast.LENGTH_SHORT).show();

                // Clear fields
                etName.setText("");
                etAge.setText("");
                rgGender.check(R.id.rbMale);
                etEmail.setText("");
                etPhone.setText("");
                etAddress.setText("");
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
            }
        });
    }
}
