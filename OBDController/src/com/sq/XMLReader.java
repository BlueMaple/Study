package com.sq;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLReader {
	private Document document;
	
	public XMLReader(){
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(new File("OBD_0008.xml"));
			parse();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void parse(){
		Element root = document.getRootElement();
		Iterator<Element> iter = root.elementIterator();
		while(iter.hasNext()){
			Element t = iter.next();
			System.out.println(t.attributeValue("id"));
			System.out.println(t.getStringValue());
			System.out.println("------------");
		}
	}
}
