package com.emc.jsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.util.BytesRef;
import org.junit.Test;

import com.emc.query.IndicesQueriesRegistry;
import com.emc.query.QueryBuilder;
import com.emc.query.QueryParseContext;
import com.emc.query.QueryParser;
import com.emc.query.warpper.WrapperQueryBuilder;
import com.emc.query.warpper.WrapperQueryParser;
import com.emc.queryBuilder.JsonXContentParser;
import com.emc.queryBuilder.ParseFieldMatcher;
import com.emc.queryBuilder.ParsingException;
import com.emc.queryBuilder.XContentParser;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class TestParseJsonContent {

	@Test
	public void testParseJsonContent() throws JsonParseException, IOException {

		String json = "{\n" + "  \"wrapper\" : {\n"
				+ "    \"query\" : \"e30=\"\n" + "  }\n" + "}";

		JsonFactory f = new JsonFactory();

		JsonParser jp = f.createJsonParser(json.getBytes());
		jp.nextToken();
		while (jp.nextToken() != JsonToken.END_OBJECT) {

			System.out.println("token:" + jp.getText());

		}

	}

	@Test
	public void testCreateQuery() throws JsonParseException, IOException {
		// String json = "{\"query\":{\"wrapper\":{\"term\":{\"name\":\"tom\"}}"
		// + "}}";

		String json = "{\"query\":"
				+ "{"
				+ " \"wrapper\" : {"
				+ "\"query\" : \"ewogICJ0ZXJtIiA6IHsKICAgICJmb28iIDogewogICAgICAidmFsdWUiIDogImJhciIsCiAgICAgICJib29zdCIgOiAxLjAKICAgIH0KICB9Cn0=\""
				+ "}" + "}" + "}";
		new WrapperQueryBuilder(json.getBytes()).toString();

		Map<String, QueryParser<?>> mappers = new HashMap<String, QueryParser<?>>();
		mappers.put("wrapper", new WrapperQueryParser());
		IndicesQueriesRegistry indicesQueriesRegistry = new IndicesQueriesRegistry(
				mappers);
		getQueryContent(new BytesRef(json.getBytes()), indicesQueriesRegistry,
				null);
	}

	private QueryBuilder getQueryContent(BytesRef source,
			IndicesQueriesRegistry indicesQueriesRegistry,
			ParseFieldMatcher parseFieldMatcher) throws JsonParseException,
			IOException {
		QueryParseContext context = new QueryParseContext(
				indicesQueriesRegistry);
		JsonFactory f = new JsonFactory();
		JsonParser jsonParser = null;
		jsonParser = f.createJsonParser(source.bytes);
		try (XContentParser requestParser = new JsonXContentParser(jsonParser)) {
			context.reset(requestParser);

			// context.parseFieldMatcher(parseFieldMatcher);
			return context.parseTopLevelQueryBuilder();
		} catch (ParsingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			context.reset(null);
		}
		return null;
	}
}
