package com.emc.query.warpper;

import java.io.IOException;

import com.emc.query.QueryParseContext;
import com.emc.query.QueryParser;
import com.emc.queryBuilder.ParsingException;
import com.emc.queryBuilder.XContentParser;
import com.emc.queryBuilder.XContentParser.Token;

/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * Query parser for JSON Queries.
 */
public class WrapperQueryParser implements QueryParser<WrapperQueryBuilder> {

	//public static final ParseField QUERY_FIELD = new ParseField("query");

	@Override
	public String[] names() {
		return new String[] { WrapperQueryBuilder.NAME };
	}

	@Override
	public WrapperQueryBuilder fromXContent(QueryParseContext parseContext) throws IOException {
		XContentParser parser = parseContext.parser();

		XContentParser.Token token = parser.nextToken();
		if (token != XContentParser.Token.FIELD_NAME) {
			try {
				throw new ParsingException(parser.getTokenLocation(),
						"[wrapper] query malformed");
			} catch (ParsingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String fieldName = parser.currentName();
//		if (!parseContext.parseFieldMatcher().match(fieldName, QUERY_FIELD)) {
//			throw new ParsingException(parser.getTokenLocation(),
//					"[wrapper] query malformed, expected `query` but was"
//							+ fieldName);
//		}
		parser.nextToken();

		byte[] source = parser.binaryValue();

		parser.nextToken();

		if (source == null) {
			try {
				throw new ParsingException(parser.getTokenLocation(),
						"wrapper query has no [query] specified");
			} catch (ParsingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new WrapperQueryBuilder(source);
	}

	@Override
	public WrapperQueryBuilder getBuilderPrototype() {
		return WrapperQueryBuilder.PROTOTYPE;
	}
}
