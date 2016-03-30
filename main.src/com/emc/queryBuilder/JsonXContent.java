package com.emc.queryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;






import org.apache.lucene.util.BytesRef;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;


/**
 * a json based xcontent;
 * @author chenc12
 *
 */

public class JsonXContent implements XContent{
	
	
	 public static XContentBuilder contentBuilder() throws IOException {
	        return XContentBuilder.builder(jsonXContent);
	    }

	    private final static JsonFactory jsonFactory;
	    public final static JsonXContent jsonXContent;

	    static {
	        jsonFactory = new JsonFactory();
	        jsonFactory.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	        jsonFactory.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
	        jsonFactory.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
	        jsonFactory.configure(JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW, false); // this trips on many mappings now...
	        jsonXContent = new JsonXContent();
	    }

	    private JsonXContent() {
	    }

	    @Override
	    public XContentType type() {
	        return XContentType.JSON;
	    }

	    @Override
	    public byte streamSeparator() {
	        return '\n';
	    }


	    @Override
	    public XContentParser createParser(byte[] data) throws IOException {
	        return new JsonXContentParser(jsonFactory.createParser(data));
	    }

		@Override
		public XContentParser createParser(String content)
				throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public XContentParser createParser(Reader reader)
				throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public XContentParser createParser(BytesRef bytes)
				throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	
	

}
