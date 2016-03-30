package com.emc.xquery;

import java.io.IOException;

import com.emc.query.QueryBuilder;
import com.emc.query.bool.BoolQueryBuilder;
import com.emc.queryBuilder.ParseField;
import com.emc.queryBuilder.QueryRewriteContext;
import com.emc.queryBuilder.XContentBuilder;

public abstract class AbstractXQueryBuilder<XQB extends AbstractXQueryBuilder>
		implements XQueryBuilder {

	byte[] source;

	public static final ParseField NAME_FIELD = new ParseField("_name");
	public static final ParseField BOOST_FIELD = new ParseField("boost");

	public static final float DEFAULT_BOOST = 1.00f;
	String name;

	private float boost;

	public abstract void doXContent(XContentBuilder builder, String[] args)
			throws IOException;

	public String getWriteableName() {
		// TODO Auto-generated method stub
		return name;
	}

	protected int doHashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	protected boolean doEquals(BoolQueryBuilder other) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns the query name for the query.
	 * 
	 * @param queryName
	 */
	@Override
	public final String queryName(String queryName) {
		return name;
	}

	/**
	 * Returns the boost for this query.
	 */
	@Override
	public final float boost() {
		return this.boost;
	}

	/**
	 * Sets the boost for this query. Documents matching this query will (in
	 * addition to the normal weightings) have their score multiplied by the
	 * boost provided.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final XQB boost(float boost) {
		this.boost = boost;
		return (XQB) this;
	}

	/**
	 * Rewrites this query builder into its primitive form. By default this
	 * method return the builder itself. If the builder did not change the
	 * identity reference must be returned otherwise the builder will be
	 * rewritten infinitely.
	 */
	public XQueryBuilder<?> rewrite(QueryRewriteContext queryShardContext)
			throws IOException {
		return this;
	}

	/**
	 * Rewrites the given query into its primitive form. Queries that for
	 * instance fetch resources from remote hosts or can simplify / optimize
	 * itself should do their heavy lifting during
	 * {@link #rewrite(QueryRewriteContext)}. This method rewrites the query
	 * until it doesn't change anymore.
	 * 
	 * @throws IOException
	 *             if an {@link IOException} occurs
	 */
	static QueryBuilder<?> rewriteQuery(QueryBuilder<?> original,
			QueryRewriteContext context) throws IOException {
		QueryBuilder builder = original;
		for (QueryBuilder rewrittenBuilder = builder.rewrite(context); rewrittenBuilder != builder; rewrittenBuilder = builder
				.rewrite(context)) {
			builder = rewrittenBuilder;
		}
		return builder;
	}

	/**
	 * @return is compound query
	 */
	public boolean isCompound() {
		return false;
	}

	public boolean isFullTextQueries() {
		return false;
	}

	public boolean isTermLevelQueries() {
		return false;
	}

	abstract String doToXQuery();

	@Override
	public final String toXQuery() {
		String query = doToXQuery();

		if (query != null) {
			if (boost != DEFAULT_BOOST) {
				// TODO add boost factor
			}
		}
		return query;
	}

}
