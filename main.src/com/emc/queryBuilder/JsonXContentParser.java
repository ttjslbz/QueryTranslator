package com.emc.queryBuilder;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;

import org.apache.lucene.util.BytesRef;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class JsonXContentParser implements XContentParser {
	
	
	private JsonParser parser;

	public JsonXContentParser(JsonParser jsonParser) {
		this.parser = jsonParser;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public XContentType contentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token nextToken() throws IOException {
		return convertToken(parser.nextToken());
	}

	@Override
	public void skipChildren() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Token currentToken() {
		// TODO Auto-generated method stub
		return convertToken(parser.getCurrentToken());
	}

	@Override
	public String currentName() throws IOException {
		// TODO Auto-generated method stub
		return parser.getCurrentName();
	}

	@Override
	public Map<String, Object> map() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> mapOrdered() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> list() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> listOrderedMap() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String text() throws IOException {
		  if (currentToken().isValue()) {
	            return parser.getText();
	        }
	        throw new IllegalStateException("Can't get text on a " + currentToken() + " at " + getTokenLocation());
	}

	@Override
	public String textOrNull() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BytesRef utf8BytesOrNull() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BytesRef utf8Bytes() throws IOException {
		if (parser.getTextLength() == 0) {
            return new BytesRef();
        }
        return new BytesRef(CharBuffer.wrap(parser.getTextCharacters(), parser.getTextOffset(), parser.getTextLength()));
	}

	@Override
	public Object objectText() throws IOException {
		JsonToken currentToken = parser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_STRING) {
            return text();
        } else if (currentToken == JsonToken.VALUE_NUMBER_INT || currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return parser.getNumberValue();
        } else if (currentToken == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        } else if (currentToken == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        } else if (currentToken == JsonToken.VALUE_NULL) {
            return null;
        } else {
            return text();
        }
	}

	@Override
	public Object objectBytes() throws IOException {
		JsonToken currentToken = parser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_STRING) {
            return text();
        } else if (currentToken == JsonToken.VALUE_NUMBER_INT || currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return parser.getNumberValue();
        } else if (currentToken == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        } else if (currentToken == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        } else if (currentToken == JsonToken.VALUE_NULL) {
            return null;
        } else {
            return utf8Bytes();
        }
	}

	@Override
	public boolean hasTextCharacters() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public char[] textCharacters() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int textLength() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int textOffset() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number numberValue() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberType numberType() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short shortValue(boolean coerce) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int intValue(boolean coerce) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long longValue(boolean coerce) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float floatValue(boolean coerce) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double doubleValue(boolean coerce) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short shortValue() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int intValue() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long longValue() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float floatValue() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double doubleValue() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isBooleanValue() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean booleanValue() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] binaryValue() throws IOException {
		// TODO Auto-generated method stub
		 return parser.getBinaryValue();
	}

	@Override
	public XContentLocation getTokenLocation() {
	      JsonLocation loc = parser.getTokenLocation();
	        if (loc == null) {
	            return null;
	        }
	        return new XContentLocation(loc.getLineNr(), loc.getColumnNr());
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ParseFieldMatcher getParseFieldMatcher() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParseFieldMatcher(ParseFieldMatcher matcher) {
		// TODO Auto-generated method stub

	}
	
	
    private Token convertToken(JsonToken token) {
        if (token == null) {
            return null;
        }
        switch (token) {
            case FIELD_NAME:
                return Token.FIELD_NAME;
            case VALUE_FALSE:
            case VALUE_TRUE:
                return Token.VALUE_BOOLEAN;
            case VALUE_STRING:
                return Token.VALUE_STRING;
            case VALUE_NUMBER_INT:
            case VALUE_NUMBER_FLOAT:
                return Token.VALUE_NUMBER;
            case VALUE_NULL:
                return Token.VALUE_NULL;
            case START_OBJECT:
                return Token.START_OBJECT;
            case END_OBJECT:
                return Token.END_OBJECT;
            case START_ARRAY:
                return Token.START_ARRAY;
            case END_ARRAY:
                return Token.END_ARRAY;
            case VALUE_EMBEDDED_OBJECT:
                return Token.VALUE_EMBEDDED_OBJECT;
        }
        throw new IllegalStateException("No matching token for json_token [" + token + "]");
    }

}
