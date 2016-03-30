package com.emc.service.search;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.emc.query.IndicesQueriesRegistry;
import com.emc.query.QueryParseContext;
import com.emc.query.QueryParser;
import com.emc.query.bool.BoolQueryParser;
import com.emc.queryBuilder.JsonXContentParser;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

public class SearchServiceTest {
	
	SearchService service;
	QueryParseContext context;
	
	@Before
	public void setUp(){
		service = new SearchService();
		Map<String, QueryParser<?>> queryParserSet = new HashMap<String, QueryParser<?>>();
		queryParserSet.put("bool", new BoolQueryParser());
		IndicesQueriesRegistry registry = new IndicesQueriesRegistry(
				queryParserSet);
		context = new QueryParseContext(registry);
		
	}

	@Test
	public void testParseJsonSource() throws JsonParseException, IOException {
		String source ="{"+
							"\"query\": {"+
										"\"bool\": { \"tweet\": \"search\" }"+								
								"}"+
							"}"+
						"}";		
		// Assemble json data:
		JsonFactory f = new JsonFactory();
		JsonParser jp = f.createParser(source.getBytes());
		JsonXContentParser xcontentParser = new JsonXContentParser(jp);
		context.reset(xcontentParser);
		try {
			service.parseSource(context, xcontentParser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(service.getQueryBuilder());

	}
	
	
	@Test
	public void testParseJsonSourceToXQuery() throws JsonParseException, IOException {
		String source ="{"+
							"\"query\": {"+
										"\"bool\": { \"tweet\": \"search\" }"+								
								"}"+
							"}"+
						"}";		
		// Assemble json data:
		JsonFactory f = new JsonFactory();
		JsonParser jp = f.createParser(source.getBytes());
		JsonXContentParser xcontentParser = new JsonXContentParser(jp);
		context.reset(xcontentParser);
		try {
			service.parseSourceTox(context, xcontentParser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = service.getXQueryBuilder().toXQuery();
		System.out.println(s);
	}
}
