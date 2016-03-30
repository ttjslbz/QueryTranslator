package com.emc.query;

import java.io.IOException;

import com.emc.queryBuilder.EmptyQueryBuilder;
import com.emc.queryBuilder.JsonXContentParser;
import com.emc.queryBuilder.ParseFieldMatcher;
import com.emc.queryBuilder.ParsingException;
import com.emc.queryBuilder.XContentParser;
import com.emc.queryBuilder.XContentParser.Token;
import com.emc.xquery.XQueryBuilder;
import com.emc.xquery.XQueryParser;


public class QueryParseContext {
	
	boolean isDebug;
	XContentParser parser;
	IndicesQueriesRegistry indicesQueriesRegistry;
	
	ParseFieldMatcher parseFieldMatcher = new ParseFieldMatcher();

	
	
    public QueryParseContext(IndicesQueriesRegistry indicesQueriesRegistry) {
		this.indicesQueriesRegistry = indicesQueriesRegistry;
	}

    
    public void setIsDebug(boolean isDebug){
    	this.isDebug = isDebug;
    }

	/**
     * Parses a query excluding the query element that wraps it
     * @throws ParsingException 
     */
    public QueryBuilder parseInnerQueryBuilder() throws IOException, ParsingException {
        // move to START object
        XContentParser.Token token;
        if (parser.currentToken() != XContentParser.Token.START_OBJECT) {
            token = parser.nextToken();
            if (token != XContentParser.Token.START_OBJECT) {
                throw new ParsingException(parser.getTokenLocation(), "[_na] query malformed, must start with start_object");
            }
        }
        token = parser.nextToken();
        
        if(isDebug){
        	outPutCurrentTokenValue((JsonXContentParser) parser,token);
        }
        
        
        if (token == XContentParser.Token.END_OBJECT) {
            // empty query
            return new EmptyQueryBuilder();
        }
        if (token != XContentParser.Token.FIELD_NAME) {
            throw new ParsingException(parser.getTokenLocation(), "[_na] query malformed, no field after start_object");
        }
        String queryName = parser.currentName();
        // move to the next START_OBJECT
        token = parser.nextToken();
        if (token != XContentParser.Token.START_OBJECT && token != XContentParser.Token.START_ARRAY) {
            throw new ParsingException(parser.getTokenLocation(), "[_na] query malformed, no field after start_object. Current token type is "+parser.contentType()+". And current token value is"+parser.toString());
        }

        QueryParser queryParser = queryParser(queryName);
        if (queryParser == null) {
            throw new ParsingException(parser.getTokenLocation(), "No query registered for [" + queryName + "]");
        }

        QueryBuilder result = queryParser.fromXContent(this);
        if (parser.currentToken() == XContentParser.Token.END_OBJECT || parser.currentToken() == XContentParser.Token.END_ARRAY) {
            // if we are at END_OBJECT, move to the next one...
            parser.nextToken();
        }
        return result;
    }
    
    
    /**
     * Parses a top level query including the query element that wraps it
     * @throws ParsingException 
     */
    public QueryBuilder parseTopLevelQueryBuilder() throws ParsingException {
        try {
            QueryBuilder queryBuilder = null;
            for (XContentParser.Token token = parser.nextToken(); token != XContentParser.Token.END_OBJECT; token = parser.nextToken()) {
                if (token == XContentParser.Token.FIELD_NAME) {
                    String fieldName = parser.currentName();
                    if ("query".equals(fieldName)) {
                        queryBuilder = parseInnerQueryBuilder();
                    } else {
                        throw new ParsingException(parser.getTokenLocation(), "request does not support [" + parser.currentName() + "]");
                    }
                }
            }
            if (queryBuilder == null) {
                throw new ParsingException(parser.getTokenLocation(), "Required query is missing");
            }
            return queryBuilder;
        } catch (ParsingException e) {
            throw e;
        } catch (Throwable e) {
        	System.out.println(e.getMessage());
            throw new ParsingException(parser == null ? null : parser.getTokenLocation(), "Failed to parse");
        }
    }
    
    public void reset(XContentParser jp) {
    	parser = jp;
        
    }
    
    
    
    public XQueryBuilder parseInnerXQueryBuilder() throws IOException, ParsingException {
        // move to START object
        XContentParser.Token token;
        if (parser.currentToken() != XContentParser.Token.START_OBJECT) {
            token = parser.nextToken();
            if (token != XContentParser.Token.START_OBJECT) {
                throw new ParsingException(parser.getTokenLocation(), "[_na] query malformed, must start with start_object");
            }
        }
        token = parser.nextToken();
        
        if(isDebug){
        	outPutCurrentTokenValue((JsonXContentParser) parser,token);
        }
        
    
        if (token != XContentParser.Token.FIELD_NAME) {
            throw new ParsingException(parser.getTokenLocation(), "[_na] query malformed, no field after start_object");
        }
        String queryName = parser.currentName();
        // move to the next START_OBJECT
        token = parser.nextToken();
        if (token != XContentParser.Token.START_OBJECT && token != XContentParser.Token.START_ARRAY) {
            throw new ParsingException(parser.getTokenLocation(), "[_na] query malformed, no field after start_object. Current token type is "+parser.contentType()+". And current token value is"+parser.toString());
        }

        XQueryParser queryParser = new TermQueryParser();
        if (queryParser == null) {
            throw new ParsingException(parser.getTokenLocation(), "No query registered for [" + queryName + "]");
        }

        XQueryBuilder result = queryParser.fromXContent(this);
        if (parser.currentToken() == XContentParser.Token.END_OBJECT || parser.currentToken() == XContentParser.Token.END_ARRAY) {
            // if we are at END_OBJECT, move to the next one...
            parser.nextToken();
        }
        return result;
    }
    
    
    
    /*
     * get QueryParser by name
     */
    private QueryParser<?> queryParser(String name) {
    	
		return indicesQueriesRegistry.queryParsers().get(name);
    }
    
    public XContentParser parser(){
    	return this.parser;
    }
    
    
    private void outPutCurrentTokenValue(XContentParser parser , Token token) throws IOException{
    	String outputValue = "";
    	if(token.isValue()){
    		outputValue = parser.text();
    	}
    	if(token == Token.START_OBJECT){
    		outputValue ="{";
    	}else if(token == Token.END_OBJECT){
    		outputValue = "}";
    	}
    	System.out.println(outputValue);
    }


	public ParseFieldMatcher parseFieldMatcher() {
		// TODO Auto-generated method stub
		return parseFieldMatcher;
	}


}
