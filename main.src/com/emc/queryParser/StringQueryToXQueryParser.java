package com.emc.queryParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

/**
 * 
 * @author chenc12 Translate an simple string query to XQuery
 */

public class StringQueryToXQueryParser {

	static final Occur MUST = Occur.MUST;
	static final Occur Filter = Occur.FILTER;
	static final Occur MUST_NOT = Occur.MUST_NOT;
	static final Occur SHOULD = Occur.SHOULD;
	
	
	Mapping mapping;

	Map<String, Float> weights = new HashMap<String, Float>();
	SimpleQueryParser parser = new SimpleQueryParser(new StandardAnalyzer(),
			weights);
	

	public String toString(Query query) {
		StringBuffer target = new StringBuffer();

		if (query instanceof TermQuery) {
			consumeLeafNodeQuery(query, target);
		} else if (query instanceof BooleanQuery) {
			target = consumeSubClauses(((BooleanQuery) query).getClauses());
		}

		return target.toString();

	}

	private void consumeLeafNodeQuery(Query query, StringBuffer target) {
		String type = null;

		if (query instanceof TermQuery) {
			type = "termQuery";
		} else if (query instanceof PhraseQuery) {
			type = "phraseQuery";
		} else if (query instanceof WildcardQuery) {
			type = "wildcard";
		}

		switch (type) {
		case "termQuery":
			consumeTermQuery(query);
			break;
		case "phraseQuery":
			// consumePhraseQuery(query, target);
			break;
		case "wildCard":
			// consumeWildCardQuery(query, target);
			break;
		}

		// TODO Auto-generated method stub

	}

	private StringBuffer consumeTermQuery(Query query) {
		StringBuffer result = new StringBuffer();
		Term term = ((TermQuery) query).getTerm();
		String field = term.field();
		String text = term.text();
		result = result.append(getPathByFieldName(field) + " ftcontains "
				+ text);
		return result;

	}

	/**
	 * @param subClauses
	 *            of boolean Query
	 * @param target
	 */

	private StringBuffer consumeSubClauses(BooleanClause[] clauses) {
		StringBuffer result = new StringBuffer();
		Occur currentOccur = null;
		Query subQuery;

		for (int i = 0; i < clauses.length; i++) {
			currentOccur = clauses[i].getOccur();
			subQuery = clauses[i].getQuery();
			StringBuffer buffer = new StringBuffer();

			if (subQuery instanceof BooleanQuery) {
				buffer.append(getCondition(currentOccur.name()));
				buffer.append("(");
				buffer.append(consumeSubClauses(((BooleanQuery) subQuery)
						.getClauses()));
				buffer.append(")");
			} else {
				buffer.append(getCondition(currentOccur.name())+" ");
				buffer.append(consumeTermQuery(clauses[i].getQuery()));
				currentOccur = clauses[i].getOccur();
			}	
			result.append(buffer);
		}
		return result;
	}

	// mapping a subpath with alise, as its field
	private String getPathByFieldName(String field) {
		return field;
	}

	private String getShould(BooleanClause clause) {

		StringBuilder sb = new StringBuilder();

		sb.append("OR (");

		if (hasSub(clause)) {

		}

		return null;
	}

	private String getMustNot(BooleanClause clause) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getFilter(BooleanClause clause) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getMust(BooleanClause clause) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean hasSub(BooleanClause clause) {
		return false;
	}

	private BooleanClause[] buildQueryTree(String queryText) {
		Query query = parser.parse(queryText);
		assert (query.getClass().equals(BooleanQuery.class));
		BooleanClause[] clauses = ((BooleanQuery) query).getClauses();
		return clauses;
	}
	
	
	

	public String parseToXMLQuery(String query){
		StringBuffer result = new StringBuffer();
		Iterator<Entry<String, Element>> ite = mapping.map.entrySet().iterator();
		
		while(ite.hasNext()){
			Entry<String, Element> entry  = ite.next();
			result.append("let "+entry.getValue().getFieldName()+" =:"+entry.getValue().getPath()+"\n");
		}
		return null;
	}

	private String getCondition(String occur) {
		String result = "";
		switch (occur) {
		case "SHOULD":
			result = "or";
			break;
		case "MUST":
			result = "and";
			break;
		}
		return result;
	}
}
