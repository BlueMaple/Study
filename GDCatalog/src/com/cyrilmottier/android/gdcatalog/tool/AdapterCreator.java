package com.cyrilmottier.android.gdcatalog.tool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import com.cyrilmottier.android.gdcatalog.R;
import greendroid.widget.ItemAdapter;
import greendroid.widget.item.*;
import greendroid.widget.item.SeparatorItem;
import android.content.Context;
import android.util.Log;

public class AdapterCreator {
	private Context context;
	private ItemAdapter adapter;
	private Map<String , String> dictionary;
	
	public AdapterCreator(Context c){
		context = c;
	}
	
	public ItemAdapter create() throws XmlPullParserException, IOException{
		createDictionary();
		createItemAdapter();
		return adapter;
	}
	
	private void createItemAdapter() throws XmlPullParserException, IOException{
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		XmlPullParser parser = context.getResources().getXml(R.xml.erm02);
		int type = parser.getEventType();
		while(type != XmlPullParser.END_DOCUMENT){
			if(type == XmlPullParser.START_TAG){
				String dataElementSign = parser.getName();
				String dataElementName = dictionary.get(dataElementSign);
				String[] array = dataElementName.split("&");
				
				int dataElementType = Integer.parseInt(array[0]);
				Item item = null;
				switch(dataElementType){
				case 0:
					item = new SeparatorItem(array[1]);
					break;
				case 1:
					item = new SeparatorItem(array[1]);
					break;
				case 2:
					parser.next();
					String dataElementValue = parser.getText();
					item = new SubtitleItem(dataElementValue , array[1]);
					break;
				}
				itemList.add(item);
			}
			type = parser.next();
		}
		
		
		adapter = new ItemAdapter(context , itemList);
	}

	private void createDictionary() throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		dictionary = new HashMap<String , String>();	
		XmlPullParser parser = context.getResources().getXml(R.xml.dictionary);
				
		int type = parser.getEventType();
		while(type != XmlPullParser.END_DOCUMENT){
			if(type == XmlPullParser.START_TAG){
				String dataElementSign = parser.getName();
				if(!dataElementSign.equals("Dictionary")){
					String dataElementType = parser.getAttributeValue(0);
					parser.next();
					String dataElementValue = parser.getText();
					
					Log.v("TestDictionary", dataElementSign+"&"+dataElementType+"&"+dataElementValue);
					dictionary.put(dataElementSign, dataElementType+"&"+dataElementValue);
				}
			}
			type = parser.next();
		}
	}
}
