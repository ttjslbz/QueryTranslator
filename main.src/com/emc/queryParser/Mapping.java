package com.emc.queryParser;

import java.util.HashMap;
import java.util.Map;

public class Mapping {
	
	Map<String,Element> map = new HashMap<String,Element>();
	
	public Mapping(){
		
	}
	
	public void addElement(Element element){
		map.put(element.getFieldName(), element);
	}
	
	
	public class XMLElement implements Element{
		String name;
		String path;
		boolean isAnalyzed;
		String type;
		
		@Override
		public String getFieldName() {
			// TODO Auto-generated method stub
			return name;
		}
		@Override
		public String getPath() {
			// TODO Auto-generated method stub
			return path;
		}
		@Override
		public boolean isAnalyzed() {
			// TODO Auto-generated method stub
			return false;
		}
	}
}
