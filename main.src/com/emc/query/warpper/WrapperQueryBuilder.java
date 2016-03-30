package com.emc.query.warpper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.emc.queryBuilder.AbstractQueryBuilder;
import com.emc.queryBuilder.XContentBuilder;



public class WrapperQueryBuilder extends AbstractQueryBuilder {
	
	final static String NAME = "wrapper";
	static final WrapperQueryBuilder PROTOTYPE = new WrapperQueryBuilder((byte[]) new byte[]{0});
	final byte[] source;
	
	public WrapperQueryBuilder(byte[] source){
		this.source = source;
	}
	
	
	public WrapperQueryBuilder(String source){
		  if (source == null || source.equals("")) {
	          throw new IllegalArgumentException("query source string cannot be null or empty");
	      }
	      this.source = source.getBytes(StandardCharsets.UTF_8);
		}


	@Override
	public String toXQuery() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void toXContent(XContentBuilder builder, String[] params) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void doXContent(XContentBuilder builder, String[] args)
			throws IOException {
		// TODO Auto-generated method stub
		
	}





}
