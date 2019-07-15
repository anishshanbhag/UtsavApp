package com.example.utsav;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



public class CashOrOnline extends AppCompatActivity {
    public String e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_or_online);
        Intent i1 = getIntent();
        e = i1.getStringExtra("email");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    public void onCash1(View v)
    {
        Intent i2 = new Intent(this, CashActivity.class);
        i2.putExtra("email",e);
        startActivity(i2);

    }
}
