package com.cyrilmottier.android.gdcatalog.tool;

import java.io.*;
import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class DBHelper extends SQLiteOpenHelper{
	private BufferedReader reader;
	private ArrayList<String> sqlList;
	private final String SQL_FILE_TAG = "-- Table:";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version, InputStream stream){
		super(context, name, factory, version);
		reader = new BufferedReader(new InputStreamReader(stream));
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		sqlList = new ArrayList<String>();
		StringBuilder sqlBuilder = new StringBuilder();
		String line = null;
		try {
			while((line = reader.readLine()) != null){
				if(TextUtils.isEmpty(line) || line.contains(SQL_FILE_TAG)){
					continue;
				}
				line = line.trim();
				sqlBuilder.append(line);
				int oneSqlTag = line.indexOf(';');
				if(oneSqlTag > 0){
					sqlList.add(new String(sqlBuilder.toString().getBytes() , "utf-8"));
					sqlBuilder = new StringBuilder();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(reader != null)
					reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		db.beginTransaction();
		for(int i = 0 ; i < sqlList.size() ; i++){
			db.execSQL(sqlList.get(i));
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
