package com.emc.queryBuilder;

public class QueryBuilder implements AbstractQueryBuilder {
	
	byte[] source;
	
	public QueryBuilder(){
		source = new byte[0];
	}
	
	
	public QueryBuilder(byte[] source){
		this.source = source;
	}

	@Override
	public String toXQuery() {
		// TODO Auto-generated method stub
		return null;
	}

}
