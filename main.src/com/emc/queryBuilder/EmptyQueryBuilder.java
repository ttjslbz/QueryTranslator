package com.emc.queryBuilder;

import java.io.IOException;

import com.emc.query.QueryBuilder;

public class EmptyQueryBuilder extends AbstractQueryBuilder {
	
	byte[] source;
	
	public EmptyQueryBuilder(){}

	public EmptyQueryBuilder(byte[] source) {
	
		// TODO Auto-generated constructor stub
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
