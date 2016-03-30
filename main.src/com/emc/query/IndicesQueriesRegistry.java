package com.emc.query;

import java.util.Map;



public class IndicesQueriesRegistry {
	
	
	
	
    private Map<String, QueryParser<?>> queryParsers;

    public IndicesQueriesRegistry(Map<String, QueryParser<?>> queryParsers) {
        this.queryParsers = queryParsers;
    }

    /**
     * Returns all the registered query parsers
     */
    public Map<String, QueryParser<?>> queryParsers() {
        return queryParsers;
    }


}
