package ch.moledor.exec;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.joda.time.DateTime;

import ch.moledor.model.DocumentDetail;
import ch.moledor.model.IndexNames;

public class ExecSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args == null || args.length == 0 || args[0].equals("info")) {
			System.out.println("Parameter ohne Naming --> Suche nach Content");
			System.out.println(IndexNames.AUTHOR+":(+)<<Wert>> --> Suche nach Author, optional oder als Pflicht");
			System.out.println(IndexNames.MODIFIED+":<<Wert>> --> Suche nach letzem Änderungsdatum, genauer Tag (Format: yyyy-mm-dd)");
			System.out.println(IndexNames.MODIFIED+":><<Wert>> --> Suche nach letzem Änderungsdatum, neuer als (Format: yyyy-mm-dd)");
			System.out.println(IndexNames.SORT_ALPHA +"  --> Alphabetische Sortierung der Filenamen");
			System.out.println(IndexNames.SORT_ALPHADESC +"  --> Alphabetische Sortierung in umgekehrter Reihenfolge der Filenamen");
			System.out.println(IndexNames.SORT_PATH +"  --> Dokumente nach Ordnerstruktur sortieren");
			System.out.println(IndexNames.SIZE+":<<Wert>> --> Grösse-Range; mögliche Werte:leer, sklein, klein, mittel, gross, sgross, riesig)");
			System.out.println(IndexNames.MAXHITS+":<<Wert>> --> Maximale Anzahl der zu findenden Dokumente");
			System.exit(1);
		}
		
		StringBuilder contents = new StringBuilder();
		StringBuilder authors = new StringBuilder();
		DateTime startDate = null;
		DateTime endDate = null;
		String size = null;
		int maxHits = 0;
		String sort = null;
		
		for(String arg : args) {
			if(arg.toLowerCase().contains(IndexNames.AUTHOR)) {
				String[] s = arg.split(":");
				authors.append(s[1]).append(" ");
			}
			else if(arg.toLowerCase().contains(IndexNames.MAXHITS)) {
				String[] s = arg.split(":");
				if(!org.apache.commons.lang3.StringUtils.isNumeric(s[1])) {
					System.out.println("Fehler: MaxHits muss numerisch sein");
					System.exit(0);
				}
				maxHits = Integer.parseInt(s[1]);
			}
			else if(IndexNames.SORT_ALPHA.toLowerCase().equals(arg.toLowerCase()) ||
					IndexNames.SORT_PATH.toLowerCase().equals(arg.toLowerCase()) ||
					IndexNames.SORT_ALPHADESC.toLowerCase().equals(arg.toLowerCase())) {
				sort = arg;
			}
			else if(arg.toLowerCase().contains(IndexNames.MODIFIED)) {

				String[] s = arg.split(":");
				String compDate = null;
				boolean isExact = true;
				if(s[1].startsWith(">")) {
					compDate = s[1].substring(1);
					isExact = false;
				}
				else {
					compDate = s[1];
				}
				String[] dat = compDate.split("-");
				startDate = new DateTime(Integer.parseInt(dat[0]), Integer.parseInt(dat[2]), Integer.parseInt(dat[3]), 0, 0);
				endDate = isExact ? startDate.plusDays(1) : DateTime.now();
				
			}
			else if(arg.toLowerCase().contains(IndexNames.SIZE)) {
				String[] s = arg.split(":");
				size = s[1];
			}
			else {
				contents.append(arg).append(" ");
			}
		}
		
		List<DocumentDetail> hits = null;
		try {
			hits = SearchForQuery.searchFiles(IndexNames.IDXDIRPAHT, contents.toString(), authors.toString(), sort, maxHits, startDate, endDate, size);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			System.exit(0);
		}

		if(hits != null && hits.size() > 0) {
		    for(DocumentDetail doc : hits) {
		    	System.out.println(doc.getPath());
		    }
	       System.out.println("Found " + hits.size());
		}
        else{
        	System.out.println("Keine Angaben zur Suche vorhanden");
        }
	}

}
