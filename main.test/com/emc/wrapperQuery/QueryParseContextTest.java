package com.emc.wrapperQuery;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.emc.query.IndicesQueriesRegistry;
import com.emc.query.QueryParseContext;
import com.emc.query.QueryParser;
import com.emc.query.warpper.WrapperQueryParser;

public class QueryParseContextTest {
	
	
	QueryParseContext context;
	IndicesQueriesRegistry registry;
	
	
	 public void setUp(){
		 
		 Map<String,QueryParser<?>>  map = new HashMap<String,QueryParser<?>>();
		 registry = new IndicesQueriesRegistry(map);
		 QueryParseContext context = new QueryParseContext(registry);
		 
	 }
	
	public static final String jsonContent = " {\"query\" : {\"matchAll\" : {}}}'";	
	
	
	@Test
	public void parseJsonContentToXQuery() {
		
		
		WrapperQueryParser parser = new WrapperQueryParser();

		
	}

}
