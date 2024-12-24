package com.example.embedded_technology_exp2;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	EditText name, pwd;
	Button register, login, forget;
	Exp2DBOpenHelper helper;
	SQLiteDatabase db;
	SharedPreferences sp1,sp2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		name = findViewById(R.id.name);
		pwd = findViewById(R.id.pwd);
		register = findViewById(R.id.reg);
		login = findViewById(R.id.login);
		forget = findViewById(R.id.forget);

		helper = new Exp2DBOpenHelper(MainActivity.this);
		db = helper.getWritableDatabase();

		sp1 = getSharedPreferences("userinfo", MODE_PRIVATE);
		sp2 = getSharedPreferences("username", MODE_PRIVATE);

		name.setText(sp1.getString("name",null));
		pwd.setText(sp1.getString("pwd",null));

		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String sz_name = name.getText().toString();
				String sz_pwd = pwd.getText().toString();
				@SuppressLint("Recycle") Cursor cursor = db.query("user",new String[]{"name","pwd"}," name=? and pwd=?",new String[]{sz_name,sz_pwd},null,null,null);
				int flag = cursor.getCount();
				if(flag == 0){
					Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
				}
				else{
					SharedPreferences.Editor editor = sp2.edit();
					cursor.moveToFirst();
					String name = cursor.getString(0);
					editor.putString("name",name);
					editor.apply();

					Intent intent = new Intent();
					intent.setClass(MainActivity.this,WelcomeActivity.class);
					startActivity(intent);
				}

			}
		});

		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});


		forget.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,ForgetActivity.class);
				startActivity(intent);
			}
		});
	}
}
