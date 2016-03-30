package com.emc.queryParser;

public interface Element {
	String getFieldName();
	String getPath();
	boolean isAnalyzed();
}
