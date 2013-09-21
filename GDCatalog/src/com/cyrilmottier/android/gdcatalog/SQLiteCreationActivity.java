package com.cyrilmottier.android.gdcatalog;

import java.io.InputStream;

import com.cyrilmottier.android.gdcatalog.tool.DBHelper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import greendroid.app.GDActivity;

public class SQLiteCreationActivity extends GDActivity{
	private InputStream sqlStream;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		sqlStream = this.getResources().openRawResource(R.raw.element_tag);
		
		DBHelper helper = new DBHelper(this , "sqlTest.db" , 
				null , 1 , sqlStream);
		SQLiteDatabase sqliteDatabase = helper.getWritableDatabase();
	}
}
