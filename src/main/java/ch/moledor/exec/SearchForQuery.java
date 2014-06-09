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
	
    public static List<DocumentDetail> searchFiles(String indexPath, String queryStr, String authors, String sort, int maxHits, DateTime startDate, DateTime endDate, String size) throws IOException, ParseException {

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
            QueryParser authorParser = new QueryParser(Version.LUCENE_47, IndexNames.AUTHOR,analyzer);
            authorsQuery = authorParser.parse(authors);
        }
        if(startDate != null) {
            QueryParser modParser = new QueryParser(Version.LUCENE_47, IndexNames.MODIFIED,analyzer);
            String fromStr = startDate.getYear()+String.format("%02d", startDate.getMonthOfYear())+String.format("%02d", startDate.getDayOfMonth());
            String toStr = endDate.getYear()+String.format("%02d", endDate.getMonthOfYear())+String.format("%02d", endDate.getDayOfMonth());
            String dateString = "+["+fromStr + " TO " + toStr+"]";
            modifiedQuery = modParser.parse(dateString); 
        }
        if(size != null) {
            QueryParser sizeParser = new QueryParser(Version.LUCENE_47, IndexNames.SIZE,analyzer);
        	sizeQuery = sizeParser.parse(IndexNames.SIZE_RANGES.get(size));
        }

        if(queryStr != null || authorsQuery != null || modifiedQuery != null || sizeQuery != null) {
        	query = parser.parse((queryStr != null ? queryStr : "")+" "+(authorsQuery != null ? authorsQuery : "")+" "+(modifiedQuery != null ? modifiedQuery : "")+ " "+ (sizeQuery != null ? sizeQuery : ""));
System.out.println(query);
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
