package com.emc.service.search;

import com.emc.query.QueryBuilder;
import com.emc.query.QueryParseContext;
import com.emc.queryBuilder.JsonXContentParser;
import com.emc.queryBuilder.XContentParser;
import com.emc.xquery.XQueryBuilder;

public class SearchService {

	QueryBuilder<?> queryBuilder;
	XQueryBuilder<?> xQueryBuilder;

	public QueryBuilder<?> getQueryBuilder() {
		return queryBuilder;
	}
	
	
	public XQueryBuilder<?> getXQueryBuilder(){
		return xQueryBuilder;
	}

	public void parseSource(QueryParseContext context, JsonXContentParser parser)
			throws Exception {
		XContentParser.Token token = parser.currentToken();
		String currentFieldName = null;

		if (token != XContentParser.Token.START_OBJECT
				&& (token = parser.nextToken()) != XContentParser.Token.START_OBJECT) {
			// TODO organise the exception message.
			throw new Exception(
					"json content not well formate, not start by start array");
		}

		while ((token = parser.nextToken()) != XContentParser.Token.END_OBJECT) {

			if (token == XContentParser.Token.FIELD_NAME) {

				// process just a single field
				currentFieldName = parser.currentName();
			} else if (token.isValue()) {
				// process a single value object
			} else if (token == XContentParser.Token.START_OBJECT) {
				// parse query
				if (currentFieldName.equalsIgnoreCase("query")) {

					queryBuilder = context.parseInnerQueryBuilder();
				}

			}}

		}
		
		
		public void parseSourceTox(QueryParseContext context, JsonXContentParser parser)
				throws Exception {
			XContentParser.Token token = parser.currentToken();
			String currentFieldName = null;

			if (token != XContentParser.Token.START_OBJECT
					&& (token = parser.nextToken()) != XContentParser.Token.START_OBJECT) {
				// TODO organise the exception message.
				throw new Exception(
						"json content not well formate, not start by start array");
			}

			while ((token = parser.nextToken()) != XContentParser.Token.END_OBJECT) {

				if (token == XContentParser.Token.FIELD_NAME) {

					// process just a single field
					currentFieldName = parser.currentName();
				} else if (token.isValue()) {
					// process a single value object
				} else if (token == XContentParser.Token.START_OBJECT) {
					// parse query
					if (currentFieldName.equalsIgnoreCase("query")) {

						xQueryBuilder = context.parseInnerXQueryBuilder();
					}

				}

			}

	}

}
