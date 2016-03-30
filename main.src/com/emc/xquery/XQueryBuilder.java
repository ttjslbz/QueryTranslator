package com.emc.xquery;

import java.io.IOException;

import org.apache.lucene.search.Query;

import com.emc.query.QueryBuilder;
import com.emc.queryBuilder.QueryRewriteContext;

public interface XQueryBuilder<XQB extends XQueryBuilder> {


	/**
	 * Returns the query name for the query.
	 * 
	 * @param queryName
	 */
	String queryName(String queryName);

	/**
	 * Returns the boost for this query.
	 */
	float boost();

	/**
	 * Sets the boost for this query. Documents matching this query will (in
	 * addition to the normal weightings) have their score multiplied by the
	 * boost provided.
	 */
	@SuppressWarnings("unchecked")
	XQB boost(float boost);

	/**
	 * 
	 * @return xquery  represent string.
	 */
	String toXQuery();
	
	
	/**
	 * @return is compound query 
	 */
	boolean isCompound();
	
	
	boolean isFullTextQueries();
	
	boolean isTermLevelQueries();
}
