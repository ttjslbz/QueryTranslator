package com.emc.queryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import org.apache.lucene.util.BytesRef;


public interface XContent {

	XContentType type();

	byte streamSeparator();

	XContentParser createParser(String content) throws IOException;

	XContentParser createParser(Reader reader) throws IOException;

	XContentParser createParser(BytesRef bytes) throws IOException;

	XContentParser createParser(byte[] data) throws IOException;
	
	
	

	
	


}
