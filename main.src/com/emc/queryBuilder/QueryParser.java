package com.emc.queryBuilder;

import java.io.IOException;



public interface QueryParser<QB extends QueryBuilder> {

    /**
     * The names this query parser is registered under.
     */
    String[] names();

    /**
     * Creates a new {@link QueryBuilder} from the query held by the {@link QueryShardContext}
     * in {@link org.elasticsearch.common.xcontent.XContent} format
     *
     * @param parseContext
     *            the input parse context. The state on the parser contained in
     *            this context will be changed as a side effect of this method
     *            call
     * @return the new QueryBuilder
     */
    QB fromXContent(QueryParseContext parseContext) throws IOException;

    /**
     * @return an empty {@link QueryBuilder} instance for this parser that can be used for deserialization
     */
    QB getBuilderPrototype();
}

