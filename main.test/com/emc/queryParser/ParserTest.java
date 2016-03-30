package com.emc.queryParser;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.junit.Test;

public class ParserTest {
	
	@Test
	public void testComplexQuery(){
		
		Analyzer analyzer = new StandardAnalyzer();
		SimpleQueryParser parser = new SimpleQueryParser(analyzer, "field");
		parser.setDefaultOperator(Occur.MUST);
		String text = "star wars | empire | strikes back";
		BooleanQuery query = (BooleanQuery) parser.parse(text);
		
		System.out.println(query.toString());
				
		StringQueryToXQueryParser s2xParser = new StringQueryToXQueryParser();
		String result = s2xParser.toString(query);	
		
		System.out.println(result);
	}
	
	
	@Test
	public void testParseSimpleOrQuery(){
		
		Analyzer analyzer = new StandardAnalyzer();
		SimpleQueryParser parser = new SimpleQueryParser(analyzer, "field");
		parser.setDefaultOperator(Occur.MUST);
		String text = "star|wars";
		BooleanQuery query = (BooleanQuery) parser.parse(text);
				
		StringQueryToXQueryParser s2xParser = new StringQueryToXQueryParser();
		String result = s2xParser.toString(query);	
	}
	
	@Test
	public void testParseSimpleAndQuery(){
		
		Analyzer analyzer = new StandardAnalyzer();
		SimpleQueryParser parser = new SimpleQueryParser(analyzer, "field");
		parser.setDefaultOperator(Occur.MUST);
		String text = "star+wars";
		BooleanQuery query = (BooleanQuery) parser.parse(text);
				
		StringQueryToXQueryParser s2xParser = new StringQueryToXQueryParser();
		String result = s2xParser.toString(query);	
	}

}
