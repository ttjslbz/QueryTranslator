package com.emc.query;

import java.io.IOException;

import org.apache.lucene.search.Query;

import com.emc.queryBuilder.QueryRewriteContext;
import com.emc.queryBuilder.XContentBuilder;

public interface QueryBuilder<QB extends QueryBuilder<QB>> {

	public String toXQuery();

	public Query toQuery();

	void doXContent(XContentBuilder builder, String[] args) throws IOException;

	public void toXContent(XContentBuilder builder, String[] params);
	
	
	 /**
     * Rewrites this query builder into its primitive form. By default this method return the builder itself. If the builder
     * did not change the identity reference must be returned otherwise the builder will be rewritten infinitely.
	 * @throws IOException 
     */
	public QueryBuilder<?> rewrite(QueryRewriteContext context) throws IOException;
	
	
	 /**
     * Rewrites the given query into its primitive form. Queries that for instance fetch resources from remote hosts or
     * can simplify / optimize itself should do their heavy lifting during {@link #rewrite(QueryRewriteContext)}. This method
     * rewrites the query until it doesn't change anymore.
     * @throws IOException if an {@link IOException} occurs
     */
    static QueryBuilder<?> rewriteQuery(QueryBuilder<?> original, QueryRewriteContext context) throws IOException {
        QueryBuilder builder = original;
        for (QueryBuilder rewrittenBuilder = builder.rewrite(context); rewrittenBuilder != builder;
             rewrittenBuilder = builder.rewrite(context)) {
            builder = rewrittenBuilder;
        }
        return builder;
    }

	QB boost(float boost);

	float boost();

	String queryName(String queryName);

}
