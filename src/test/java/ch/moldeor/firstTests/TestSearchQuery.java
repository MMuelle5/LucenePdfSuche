package ch.moldeor.firstTests;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Assert;
import org.junit.Test;

import ch.moledor.exec.SearchForQuery;
import ch.moledor.model.DocumentDetail;
import ch.moledor.model.IndexNames;


public class TestSearchQuery {

	private final static String IDX_TEST_PATH = "D:/workspace_java/LucenePdfSuche/indexingTests";
	@Test
	public void testComplete() throws IOException, ParseException {
		
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
		
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, "(Anlegegoniometer && blubb) Bikinis", "MARIUS", null, 400, null, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);
				
		Assert.assertArrayEquals(exp, res.toArray());
	}
	
	@Test
	public void testAuthorIsNull() throws IOException, ParseException {

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
		
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, "(Anlegegoniometer && blubb) Bikinis", null, null, 400, null, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);
				
		Assert.assertArrayEquals(exp, res.toArray());
	}
	
	@Test
	public void testContentIsNull() throws IOException, ParseException {

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
		
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, null, "MARIUS", null, 400, null, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);
				
		Assert.assertArrayEquals(exp, res.toArray());

	}
	
	@Test
	public void testContentIsBlank() throws IOException, ParseException {
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
		
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, "   ", "MARIUS", null, 400, null, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);
		
		Assert.assertArrayEquals(exp, res.toArray());
	}
	
	@Test
	public void testExactDate() throws IOException, ParseException {
		DocumentDetail docDet = new DocumentDetail();
		docDet.setDocName("Blablibla blubb oki doki wusch.pdf");
		docDet.setSort(null);
		docDet.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\Blablibla blubb oki doki wusch.pdf");
		docDet.setFolderCount(3);
		DocumentDetail docDet2 = new DocumentDetail();
		docDet2.setDocName("Silberleiste.pdf");
		docDet2.setSort(IndexNames.SORT_ALPHA);
		docDet2.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\zzzz\\Silberleiste.pdf");
		docDet2.setFolderCount(4);

		DocumentDetail[] exp = new DocumentDetail[]{docDet, docDet2};
		
		List<String> dateList = new ArrayList<String>();
		dateList.add("2014-6-8");
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, null, null, null, 400, dateList, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);

		Assert.assertArrayEquals(exp, res.toArray());
	}

	@Test
	public void testAllNull() throws IOException, ParseException {

		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, null, null, null, 400, null, null);

		Assert.assertEquals(res.size(), 0);
	}

	@Test
	public void testSize() throws IOException, ParseException {
		DocumentDetail docDet = new DocumentDetail();
		docDet.setDocName("sizilisch.pdf");
		docDet.setSort(null);
		docDet.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\sizilisch.pdf");
		docDet.setFolderCount(3);

		DocumentDetail[] exp = new DocumentDetail[]{docDet};

		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, null, null, null, 400, null, "sklein");
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);

		Assert.assertArrayEquals(exp, res.toArray());

	}

	@Test
	public void testWildChar() throws IOException, ParseException {
		
		DocumentDetail docDet = new DocumentDetail();
		docDet.setDocName("Blablibla blubb oki doki wusch.pdf");
		docDet.setSort(null);
		docDet.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\Blablibla blubb oki doki wusch.pdf");
		docDet.setFolderCount(3);
		DocumentDetail[] exp = new DocumentDetail[]{docDet};
		
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, "Bl?blibla", null, null, 400, null, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);
				
		Assert.assertArrayEquals(exp, res.toArray());
	}
	
}