package com.emc.jsonParser;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.emc.jsonParser.User.Name;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;




/**
 * http://wiki.fasterxml.com/JacksonInFiveMinutes 
 */

public class JacksonTest {
	
	
	@Test
	public void testJacksonStreammingAPI() throws JsonParseException, IOException{
		URL file = this.getClass().getClassLoader().getResource("user.json");
		JsonFactory f= new JsonFactory();
		
		JsonParser jp = f.createJsonParser(new File(file.getPath()));
		User user = new User();
		jp.nextToken();
		while(jp.nextToken() != JsonToken.END_OBJECT){
			
			String fieldName = jp.getCurrentName();
			jp.nextToken();
			
			if ("name".equals(fieldName)) { // contains an object
				    Name name = new Name();
				       while (jp.nextToken() != JsonToken.END_OBJECT) {
				        String namefield = jp.getCurrentName();
				        jp.nextToken(); // move to value
				      if ("first".equals(namefield)) {
			        name.setFirst(jp.getText());
				      } else if ("last".equals(namefield)) {
				         name.setLast(jp.getText());
				      } else {
				        throw new IllegalStateException("Unrecognized field '"+fieldName+"'!");
				       }
				     }
				     user.setName(name);
				  } else if ("gender".equals(fieldName)) {
				      user.setGender(User.Gender.valueOf(jp.getText()));
				  } else if ("verified".equals(fieldName)) {
				     user.setVerified(jp.getCurrentToken() == JsonToken.VALUE_TRUE);
				  } else if ("userImage".equals(fieldName)) {
				     user.setUserImage(jp.getBinaryValue());
				    } else {
				       throw new IllegalStateException("Unrecognized field '"+fieldName+"'!");
				     }
				  }
				   jp.close();
			
			
		}
		
	}


