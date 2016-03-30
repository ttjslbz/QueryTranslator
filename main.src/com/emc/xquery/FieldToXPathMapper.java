package com.emc.xquery;

import java.util.HashMap;
import java.util.Map;

public class FieldToXPathMapper {

	String name;
	
	public Map<String, String> mapper = new HashMap<String, String>();
	
	public void updatePath(String fieldName, String xPath){
		mapper.put(fieldName, xPath);
	}
	
	
	public void deleteField(String fieldName){
		mapper.remove(fieldName);
	}
	
	
	public String getXPath(String fieldName) throws Exception{
		if(fieldName == null|| fieldName.equals("")){
			throw new Exception("error, can't not get fieldName by empty String");
		}
		String xPath = mapper.get(fieldName);
		if(xPath == null){
			throw new  Exception("Can't resolve fieldName:"+fieldName +" in current type:"+name);
		}
		return xPath;
	}
	
}
