package ch.moldeor.firstTests;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Assert;
import org.junit.Test;

import ch.moledor.exec.SearchForQuery;
import ch.moledor.model.DocumentDetail;
import ch.moledor.model.IndexNames;

public class TestSort {
	private final static String IDX_TEST_PATH = "D:/workspace_java/LucenePdfSuche/indexingTests";

	@Test
	public void testAuthorIsNullOrderByAlph() throws IOException, ParseException {

		DocumentDetail docDet = new DocumentDetail();
		docDet.setDocName("westberlin.pdf");
		docDet.setSort(null);
		docDet.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\westberlin.pdf");
		docDet.setFolderCount(3);
		DocumentDetail docDet2 = new DocumentDetail();
		docDet2.setDocName("Silberleiste.pdf");
		docDet2.setSort(IndexNames.SORT_ALPHA);
		docDet2.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\zzzz\\Silberleiste.pdf");
		docDet2.setFolderCount(4);

		DocumentDetail[] exp = new DocumentDetail[]{docDet2, docDet};
		
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, "(Anlegegoniometer && blubb) Bikinis", null, IndexNames.SORT_ALPHA, 400, null, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);
				
		Assert.assertArrayEquals(exp, res.toArray());
	}
	
	@Test
	public void testAuthorIsNullOrderByAlphDesc() throws IOException, ParseException {

		DocumentDetail docDet = new DocumentDetail();
		docDet.setDocName("westberlin.pdf");
		docDet.setSort(null);
		docDet.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\westberlin.pdf");
		docDet.setFolderCount(3);
		DocumentDetail docDet2 = new DocumentDetail();
		docDet2.setDocName("Silberleiste.pdf");
		docDet2.setSort(IndexNames.SORT_ALPHA);
		docDet2.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\zzzz\\Silberleiste.pdf");
		docDet2.setFolderCount(4);

		DocumentDetail[] exp = new DocumentDetail[]{docDet, docDet2};

		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, "(Anlegegoniometer && blubb) Bikinis", null, IndexNames.SORT_ALPHADESC, 400, null, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);
				
		Assert.assertArrayEquals(exp, res.toArray());
	}
	
	@Test
	public void testAuthorIsNullOrderByPath() throws IOException, ParseException {

		DocumentDetail docDet = new DocumentDetail();
		docDet.setDocName("westberlin.pdf");
		docDet.setSort(null);
		docDet.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\westberlin.pdf");
		docDet.setFolderCount(3);
		DocumentDetail docDet2 = new DocumentDetail();
		docDet2.setDocName("Silberleiste.pdf");
		docDet2.setSort(IndexNames.SORT_ALPHA);
		docDet2.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\zzzz\\Silberleiste.pdf");
		docDet2.setFolderCount(4);

		DocumentDetail[] exp = new DocumentDetail[]{docDet, docDet2};
		
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, "(Anlegegoniometer && blubb) Bikinis", null, IndexNames.SORT_PATH, 400, null, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);
				
		Assert.assertArrayEquals(exp, res.toArray());
	}
	
	@Test
	public void testSortBySize() throws IOException, ParseException {
		DocumentDetail docDet = new DocumentDetail();
		docDet.setDocName("Blablibla blubb oki doki wusch.pdf");
		docDet.setSort(null);
		docDet.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\Blablibla blubb oki doki wusch.pdf");
		docDet.setFolderCount(3);
		DocumentDetail docDet2 = new DocumentDetail();
		docDet2.setDocName("empty.pdf");
		docDet2.setSort(IndexNames.SORT_ALPHA);
		docDet2.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\empty.pdf");
		docDet2.setFolderCount(4);
		DocumentDetail[] exp = new DocumentDetail[]{docDet2, docDet};
		
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, "   ", "MARIUS", IndexNames.SORT_SIZE, 400, null, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);
		
		Assert.assertArrayEquals(exp, res.toArray());
	}

	
	@Test
	public void testSortByModified() throws IOException, ParseException {
		DocumentDetail docDet = new DocumentDetail();
		docDet.setDocName("Blablibla blubb oki doki wusch.pdf");
		docDet.setSort(null);
		docDet.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\Blablibla blubb oki doki wusch.pdf");
		docDet.setFolderCount(3);
		DocumentDetail docDet2 = new DocumentDetail();
		docDet2.setDocName("empty.pdf");
		docDet2.setSort(IndexNames.SORT_ALPHA);
		docDet2.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\empty.pdf");
		docDet2.setFolderCount(4);
		DocumentDetail[] exp = new DocumentDetail[]{docDet, docDet2};
		
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, "   ", "MARIUS", IndexNames.SORT_MODIFIED, 400, null, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);
		
		Assert.assertArrayEquals(exp, res.toArray());
	}
}
