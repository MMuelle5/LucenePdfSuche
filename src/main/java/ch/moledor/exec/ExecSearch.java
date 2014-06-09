package ch.moledor.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.joda.time.DateTime;

import ch.moledor.model.DocumentDetail;
import ch.moledor.model.IndexNames;

public class ExecSearch {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		System.out.println("=================================================");
		System.out.println("Moegliche Suchoptionen:");
		System.out.println("Parameter ohne Naming --> Suche nach Content");
		System.out.println(IndexNames.AUTHOR+":(+)<<Wert>> --> Suche nach Author, optional oder als Pflicht");
		System.out.println(IndexNames.MODIFIED+":<<Wert>> --> Suche nach letzem Aenderungsdatum, genauer Tag (Format: yyyy-mm-dd)");
		System.out.println(IndexNames.MODIFIED+":><<Wert>> --> Suche nach letzem Aenderungsdatum, neuer als (Format: yyyy-mm-dd)");
		System.out.println(IndexNames.SIZE+":<<Wert>> --> Groesse-Range; moegliche Werte:leer, sklein, klein, mittel, gross, sgross, riesig)");
		System.out.println(IndexNames.SORT_ALPHA +"  --> Alphabetische Sortierung der Filenamen");
		System.out.println(IndexNames.SORT_ALPHADESC +"  --> Alphabetische Sortierung in umgekehrter Reihenfolge der Filenamen");
		System.out.println(IndexNames.SORT_PATH +"  --> Dokumente nach Ordnerstruktur sortieren");
		System.out.println(IndexNames.SORT_SIZE +"  --> Dokumente nach der Dokumentengroesse sortieren");
		System.out.println(IndexNames.SORT_MODIFIED +"  --> Dokumente nach dem Bearbeitungsdatum sortieren");
		System.out.println(IndexNames.MAXHITS+":<<Wert>> --> Maximale Anzahl der zu findenden Dokumente");
		System.out.println("exit um das Programm zu beenden");
		System.out.println("=================================================");
	
		String input = "";
		
		while(input != null && !input.equals("exit")) {
			System.out.println("Bitte geben Sie ihre Suchanfrage ein");
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			input = bufferRead.readLine();
			
			if(input.equals("exit")) {
				System.out.println("Programm wird beendet");
				System.exit(1);
			}
			System.out.println("======================");
			System.out.println("");
		
			StringBuilder contents = new StringBuilder();
			StringBuilder authors = new StringBuilder();
			List<String> dateStrings = new ArrayList<String>();
			DateTime startDate = null;
			DateTime endDate = null;
			String size = null;
			int maxHits = 0;
			String sort = null;
			
			for(String arg : input.split(" ")) {
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
						IndexNames.SORT_ALPHADESC.toLowerCase().equals(arg.toLowerCase()) ||
						IndexNames.SORT_PATH.toLowerCase().equals(arg.toLowerCase()) ||
						IndexNames.SORT_SIZE.toLowerCase().equals(arg.toLowerCase()) ||
						IndexNames.SORT_MODIFIED.toLowerCase().equals(arg.toLowerCase())) {
					sort = arg;
				}
				else if(arg.toLowerCase().contains(IndexNames.MODIFIED)) {
	
					String[] s = arg.split(":");
					dateStrings.add(s[1]);
					
				}
				else if(arg.toLowerCase().startsWith(IndexNames.SIZE+":")) {
					String[] s = arg.split(":");
					size = s[1];
				}
				else {
					contents.append(arg).append(" ");
				}
			}
			
			List<DocumentDetail> hits = null;
			try {
				maxHits = maxHits > 0 ? maxHits : 999;
				hits = SearchForQuery.searchFiles(IndexNames.IDXDIRPAHT, contents.toString(), authors.toString(), sort, maxHits, dateStrings, size);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
				System.exit(0);
			}
	
			System.out.println("");
			System.out.println("Resultate:");
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

}
