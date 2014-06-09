package ch.moledor.exec;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.joda.time.DateTime;

import ch.moledor.model.DocumentDetail;
import ch.moledor.model.IndexNames;

public class SearchForQuery {
	
    public static List<DocumentDetail> searchFiles(String indexPath, String queryStr, String authors, String sort, int maxHits, List<String> dateStrings, String size) throws IOException, ParseException {

        List<DocumentDetail> found = new LinkedList<DocumentDetail>();

        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
        
        Query query = null;
        Query authorsQuery = null;
        Query modifiedQuery = null;
        Query sizeQuery = null;
        QueryParser parser = new QueryParser(Version.LUCENE_47, IndexNames.CONTENT, analyzer);
        
        if(StringUtils.isNotBlank(authors)) {
        	System.out.println("blubb");
    		String[] autorlist = authors.split(" ");
        	StringBuilder sb = new StringBuilder();
        	sb.append("+(");
        	boolean firstAutorDone = false;
        	for(String s : autorlist) {
        		if(firstAutorDone) {
        			sb.append(" OR ");
        		}
        		
        		sb.append(s);
        		firstAutorDone = true;
        	}
        	sb.append(")");
    		
            QueryParser authorParser = new QueryParser(Version.LUCENE_47, IndexNames.AUTHOR,analyzer);
            authorsQuery = authorParser.parse(sb.toString());
            System.out.println(authorsQuery);
        }
        if(dateStrings != null && dateStrings.size() > 0) {

        	StringBuilder sb = new StringBuilder();
        	sb.append("+(");
        	for(String date : dateStrings) {
    			boolean isExact = true;
    			String compDate = null;
    			if(date.startsWith(">")) {
    				compDate = date.substring(1);
    				isExact = false;
    			}
    			else {
    				compDate = date;
    			}

    			String[] dat = compDate.split("-");
    			String fromStr = dat[0]+String.format("%02d", Integer.valueOf(dat[1]))+String.format("%02d", Integer.valueOf(dat[2]));
    			if(isExact) {
    				sb.append(fromStr).append(" ");
    			}
    			else {
    				DateTime dt = DateTime.now();
    				String toStr = dt.getYear()+String.format("%02d", dt.getMonthOfYear())+String.format("%02d", dt.getDayOfMonth());
    				sb.append("["+fromStr + " TO " + toStr+"] ");
    			}
        	}
        	sb.append(")");
        	
        	QueryParser modParser = new QueryParser(Version.LUCENE_47, IndexNames.MODIFIED,analyzer);
        	modifiedQuery = modParser.parse(sb.toString());
        }
//        if(date != null) {
//            QueryParser modParser = new QueryParser(Version.LUCENE_47, IndexNames.MODIFIED,analyzer);
//            String fromStr = startDate.getYear()+String.format("%02d", startDate.getMonthOfYear())+String.format("%02d", startDate.getDayOfMonth());
//            if(endDate != null) {
//	            String toStr = endDate.getYear()+String.format("%02d", endDate.getMonthOfYear())+String.format("%02d", endDate.getDayOfMonth());
//	            System.out.println(toStr);
//	            String dateString = "+["+fromStr + " TO " + toStr+"]";
//	            modifiedQuery = modParser.parse(dateString);
//            }
//            else {
//            	modifiedQuery = modParser.parse("+"+fromStr);
//            }
//        }
        if(size != null) {
            QueryParser sizeParser = new QueryParser(Version.LUCENE_47, IndexNames.SIZE,analyzer);
        	sizeQuery = sizeParser.parse(IndexNames.SIZE_RANGES.get(size));
        }

        if(queryStr != null || authorsQuery != null || modifiedQuery != null || sizeQuery != null) {
        	query = parser.parse((queryStr != null ? queryStr : "")+" "+(authorsQuery != null ? authorsQuery : "")+" "+(modifiedQuery != null ? modifiedQuery : "")+ " "+ (sizeQuery != null ? sizeQuery : ""));
        	
        	System.out.println("Abgesetzte Query: "+query);
            
        	TopDocs topDocs = searcher.search(query, maxHits);
            ScoreDoc[] hits = topDocs.scoreDocs;
            for (int i = 0; i < hits.length; i++) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                
                DocumentDetail doc = new DocumentDetail(sort, d);
                found.add(doc);	                
            }
            if(StringUtils.isNotEmpty(sort)) {
            	Collections.sort(found);
            }
        }
        return found;
 
    }

}
