package ch.moledor.exec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.joda.time.DateTime;

import ch.moledor.model.IndexNames;

public class IndexingFolder {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
//			File direct = new File("D:\\testFolderDataRetrieval\\pdfFolders");
//			File idxDir = new File(IndexNames.IDXDIRPAHT);
			File direct = new File("D:\\workspace_java\\LuceneTutorial\\testDataSet");
			File idxDir = new File("D:\\workspace_java\\LuceneTutorial\\indexingTests");
			
			indexFolder(direct, idxDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void indexFolder(File direct, File idxDir) throws IOException {
		DateTime dt = DateTime.now();
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, new StandardAnalyzer(Version.LUCENE_47));
		
//		if(true) {
	        iwc.setOpenMode(OpenMode.CREATE);
//	    } else {
//	        // update index
//	        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
//	    }
	    iwc.setRAMBufferSizeMB(256.0);

		Directory idxDirectory = FSDirectory.open(idxDir);
		IndexWriter indexWriter = new IndexWriter(idxDirectory, iwc);
		

        indexDocs(indexWriter, direct);
        // writer.forceMerge();
        indexWriter.close();
        
        System.out.println("Dauer: "+DateTime.now().minus(dt.getMillis()) + " ms");
       

		
	}

	private static void indexDocs(IndexWriter writer, File file) throws IOException {
		
        if (file.canRead()) {
            if (file.isDirectory()) {
                String[] files = file.list();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        indexDocs(writer, new File(file, files[i]));
                    }
                }
            } else {
                FileInputStream fis = null;
        		String pdfText = null;
        		String author = null;
                
                try {
                	
                    fis = new FileInputStream(file);

                	if(FilenameUtils.isExtension(file.getName(), "pdf")) {
                		PDFParser parser = new PDFParser(fis);
                		parser.parse();
                		COSDocument cd = parser.getDocument();
                		PDFTextStripper stripper = new PDFTextStripper();
                		PDDocument pddoc = new PDDocument(cd);
                		pdfText = stripper.getText(pddoc);

                		PDDocumentInformation pdfInfo = pddoc.getDocumentInformation();
                		author = pdfInfo.getAuthor();
                        
                		pddoc.close();
                	}
                } catch (FileNotFoundException fnfe) {
                    fnfe.printStackTrace();
                }
                try {
                                                                              // creating the Document object to store in the index
                    Document doc = new Document();
                    doc.add(new StringField(IndexNames.PATH, file.getPath(),Field.Store.YES));
                    DateTime dt = new DateTime(file.lastModified());
                    String dateStr = dt.getYear()+String.format("%02d", dt.getMonthOfYear())+String.format("%02d", dt.getDayOfMonth());
                    doc.add(new StringField(IndexNames.MODIFIED, dateStr,Field.Store.YES));
                    doc.add(new StringField(IndexNames.SIZE, String.format("%09d",file.length()>999999999 ? 999999999 : file.length()),Field.Store.YES));

                	if(!FilenameUtils.isExtension(file.getName(), "pdf")) {
                		doc.add(new TextField(IndexNames.CONTENT, new BufferedReader(new InputStreamReader(fis, "UTF-8"))));
                	}
                	else {
                		doc.add(new TextField(IndexNames.CONTENT, new BufferedReader(new StringReader(pdfText))));
                		if(author != null) {
	                        doc.add(new StringField(IndexNames.AUTHOR, author.toLowerCase().replaceAll(" ", ""), Store.YES));
                		}
                	}
                    if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                                                                                                 // for creating the index
//                        System.out.println("adding " + file);
                        writer.addDocument(doc);
                    } else {
                                                                                               // for updating the index’s
                        System.out.println("updating " + file);
                        writer.updateDocument(new Term(IndexNames.PATH, file.getPath()),doc);
                    }
                }finally {
                    fis.close();
                }
            }
        }
    }

}
