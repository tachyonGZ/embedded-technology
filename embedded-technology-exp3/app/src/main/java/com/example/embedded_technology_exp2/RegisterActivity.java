package com.example.embedded_technology_exp2;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
	EditText name, pwd, pwd2;
	Button submit;
	Exp2DBOpenHelper helper;
	SQLiteDatabase db;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		name = findViewById(R.id.name);
		pwd =  findViewById(R.id.pwd);
		pwd2 = findViewById(R.id.pwd2);
		submit = findViewById(R.id.submit);

		helper = new Exp2DBOpenHelper(RegisterActivity.this);
		db = helper.getWritableDatabase();

		sp = getSharedPreferences("userinfo", MODE_PRIVATE);

		submit.setOnClickListener(new View.OnClickListener() {
			boolean flag = true;
			@Override
			public void onClick(View v) {
				String sz_name = name.getText().toString();
				String sz_pwd = pwd.getText().toString();
				String sz_pwd2 = pwd2.getText().toString();
				if(sz_name.isEmpty() || sz_pwd.isEmpty() || sz_pwd2.isEmpty()){
					Toast.makeText(RegisterActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
				}
				else{
					@SuppressLint("Recycle") Cursor cursor = db.query("user",new String[]{"name"},null,null,null,null,null);
					while (cursor.moveToNext()){
						if(cursor.getString(0).equals(sz_name)){
							flag = false;
							break;
						}
					}
					if(!flag){
						Toast.makeText(RegisterActivity.this, "用户已经存在", Toast.LENGTH_LONG).show();
					}
					else{
						if (sz_pwd.equals(sz_pwd2)) {
							ContentValues cv = new ContentValues();
							cv.put("name",sz_name);
							cv.put("pwd",sz_pwd);
							db.insert("user",null,cv);

							SharedPreferences.Editor editor = sp.edit();
							editor.putString("name",sz_name);
							editor.putString("pwd",sz_pwd);
							editor.apply();

							Intent intent = new Intent();
							intent.setClass(RegisterActivity.this,MainActivity.class);
							startActivity(intent);

							Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
						}
						else {
							Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_LONG).show();
						}
					}
				}
			}


		});
	}
}
