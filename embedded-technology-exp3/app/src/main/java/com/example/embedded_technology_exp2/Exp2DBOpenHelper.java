package com.example.embedded_technology_exp2;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Exp2DBOpenHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "exp2.db";
	private static final int VERSION = 1;

	public Exp2DBOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table user(name varchar(10) primary key,pwd varchar(10))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}