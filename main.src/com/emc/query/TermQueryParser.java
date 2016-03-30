package com.emc.query;

import java.io.IOException;

import org.apache.lucene.queryparser.xml.builders.TermQueryBuilder;

import com.emc.queryBuilder.AbstractQueryBuilder;
import com.emc.queryBuilder.ParseField;
import com.emc.queryBuilder.ParsingException;
import com.emc.queryBuilder.XContentParser;
import com.emc.xquery.AbstractXQueryBuilder;
import com.emc.xquery.TermXQueryBuilder;
import com.emc.xquery.XQueryBuilder;
import com.emc.xquery.XQueryParser;

public class TermQueryParser <XQB extends XQueryBuilder> implements XQueryParser{
	
	
	  public static final ParseField TERM_FIELD = new ParseField("term");
	    public static final ParseField VALUE_FIELD = new ParseField("value");

	@Override
	public String[] names() {
		return new String[]{TermXQueryBuilder.NAME};
	}

	@Override
	public TermXQueryBuilder fromXContent(QueryParseContext parseContext)
			throws IOException, ParsingException {
		XContentParser parser = parseContext.parser();
		
		
		
		String queryName = null;
		String fieldName = null;
		Object value = null;
		
		
		float boost = AbstractXQueryBuilder.DEFAULT_BOOST;
		
		
		String currentFieldName = null;
		XContentParser.Token token;
		
		
		while((token = parser.nextToken()) != XContentParser.Token.END_OBJECT){
            if (token == XContentParser.Token.FIELD_NAME) {
                currentFieldName = parser.currentName();
            }  else if (token == XContentParser.Token.START_OBJECT) {
                // also support a format of "term" : {"field_name" : { ... }}
                if (fieldName != null) {
                    throw new ParsingException(parser.getTokenLocation(), "[term] query does not support different field names, use [bool] query instead");
                }
                fieldName = currentFieldName;
                while ((token = parser.nextToken()) != XContentParser.Token.END_OBJECT) {
                    if (token == XContentParser.Token.FIELD_NAME) {
                        currentFieldName = parser.currentName();
                    } else {
                        if (parseContext.parseFieldMatcher().match(currentFieldName, TERM_FIELD)) {
                            value = parser.objectBytes();
                        } else if (parseContext.parseFieldMatcher().match(currentFieldName, VALUE_FIELD)) {
                            value = parser.objectBytes();
                        } else if (parseContext.parseFieldMatcher().match(currentFieldName, AbstractQueryBuilder.NAME_FIELD)) {
                            queryName = parser.text();
                        } else if (parseContext.parseFieldMatcher().match(currentFieldName, AbstractQueryBuilder.BOOST_FIELD)) {
                            boost = parser.floatValue();
                        } else {
                            throw new ParsingException(parser.getTokenLocation(), "[term] query does not support [" + currentFieldName + "]");
                        }
                    }
                }
            } else if (token.isValue()) {
                if (fieldName != null) {
                    throw new ParsingException(parser.getTokenLocation(), "[term] query does not support different field names, use [bool] query instead");
                }
                fieldName = currentFieldName;
                value = parser.objectBytes();
            } else if (token == XContentParser.Token.START_ARRAY) {
                throw new ParsingException(parser.getTokenLocation(), "[term] query does not support array of values");
            }
        }
		

        TermXQueryBuilder termQuery = new TermXQueryBuilder(fieldName, value.toString());
        termQuery.boost(boost);
        if (queryName != null) {
            termQuery.queryName(queryName);
        }
		return termQuery;
	}

	@Override
	public XQueryBuilder getBuilderPrototype() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
