package com.emc.queryBuilder;



public class WrapperQueryBuilder extends QueryBuilder {
	
	final static String NAME = "wrapper";
	static final WrapperQueryBuilder PROTOTYPE = new WrapperQueryBuilder((byte[]) new byte[]{0});
	
	
	public WrapperQueryBuilder(byte[] source){
		super(source);
	}

}
