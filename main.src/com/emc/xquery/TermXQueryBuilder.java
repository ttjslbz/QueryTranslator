package com.emc.xquery;

import java.io.IOException;

import com.emc.queryBuilder.XContentBuilder;

public class TermXQueryBuilder extends AbstractXQueryBuilder {
	
	
	
	public TermXQueryBuilder(String fieldName,String term){
		this.fieldName = fieldName;
		this.term = term;
	}

	public static final String NAME = "term";
	FieldToXPathMapper fieldToXPathMapper;
	String fieldName;
	String term;
	String defaultPath = "";
	
	
	
	String name = "term";

	String template = "let $j:= for $i in /dmftdoc[. ftcontains 'aaa' with stemming using stop words default ftand 'bbb' with stemming using stop words default] order by $s descending return";

	@Override
	public void doXContent(XContentBuilder builder, String[] args)
			throws IOException {

	}

	@Override
	String doToXQuery() {
		StringBuilder sb = new StringBuilder();
		String path = "";
		try {
			fieldToXPathMapper = new FieldToXPathMapper();
			fieldToXPathMapper.mapper.put("tweet","dftxml" );
			path = fieldToXPathMapper.getXPath(fieldName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (path == null) {
			path = defaultPath;
		}

		sb.append("let $j:= for $i in /");
		sb.append(path + "[. ftcontains '");
		sb.append(term);
		sb.append("' with stemming using stop words default]order by $s descending return <d> {$i/dmftmetadata//r_object_id}  { $i/dmftmetadata//object_name } { $i/dmftmetadata//r_modifier }</d> return (for $k in subsequence($j,1,200) return $k)");
		return sb.toString();
	}

}
