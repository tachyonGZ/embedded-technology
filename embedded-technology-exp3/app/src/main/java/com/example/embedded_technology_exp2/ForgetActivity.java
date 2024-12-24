package com.example.embedded_technology_exp2;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetActivity extends AppCompatActivity {
	EditText name, pwd, pwd2;
	Button submit;
	Exp2DBOpenHelper helper;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget);

		name = findViewById(R.id.name);
		pwd =  findViewById(R.id.pwd);
		pwd2 = findViewById(R.id.pwd2);
		submit = findViewById(R.id.submit);

		helper = new Exp2DBOpenHelper(ForgetActivity.this);
		db = helper.getWritableDatabase();

		submit.setOnClickListener(new View.OnClickListener() {
			boolean is_exist = false;
			@Override
			public void onClick(View v) {
				String sz_name = name.getText().toString();
				String sz_pwd = pwd.getText().toString();
				String sz_pwd2 = pwd2.getText().toString();
				if(sz_name.isEmpty() || sz_pwd.isEmpty() || sz_pwd2.isEmpty()){
					Toast.makeText(ForgetActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
					return;
				}

				@SuppressLint("Recycle") Cursor cursor = db.query("user",new String[]{"name"},null,null,null,null,null);
				while (cursor.moveToNext()){
					if(cursor.getString(0).equals(sz_name)){
						is_exist = true;
						break;
					}
				}
				if(!is_exist){
					Toast.makeText(ForgetActivity.this, "用户不存在", Toast.LENGTH_LONG).show();
					return;
				}

				if (!sz_pwd.equals(sz_pwd2)) {
					Toast.makeText(ForgetActivity.this, "密码不一致", Toast.LENGTH_LONG).show();
					return;
				}

				ContentValues cv = new ContentValues();
				cv.put("name",sz_name);
				cv.put("pwd",sz_pwd);
				db.update("user", cv, " name=? ", new String[]{sz_name});

				Intent intent = new Intent();
				intent.setClass(ForgetActivity.this,MainActivity.class);
				startActivity(intent);

				Toast.makeText(ForgetActivity.this, "重新设置密码成功", Toast.LENGTH_LONG).show();
			}
		});
	}
}
