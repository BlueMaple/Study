package com.cyrilmottier.android.gdcatalog;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.cyrilmottier.android.gdcatalog.tool.AdapterCreator;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import greendroid.app.GDListActivity;
import greendroid.widget.ItemAdapter;

public class DynamicListActivity extends GDListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		SQLiteDatabase test = this.openOrCreateDatabase("ElementFlag.db", MODE_PRIVATE, null);
		test.close();
		
		ItemAdapter adapter = null;
		AdapterCreator creator = new AdapterCreator(this);
		try {
			adapter = creator.create();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(adapter == null)
			adapter = new ItemAdapter(this);
	    setListAdapter(adapter);
	}

}
