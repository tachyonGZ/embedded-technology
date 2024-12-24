package com.example.embedded_technology_exp2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
	SharedPreferences sp;
	TextView welcome_text;
	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		sp = this.getSharedPreferences("username", MODE_PRIVATE);
		welcome_text = findViewById(R.id.welcome_text);
		welcome_text.setText("欢迎！尊敬的"+sp.getString("name",""));
	}
}