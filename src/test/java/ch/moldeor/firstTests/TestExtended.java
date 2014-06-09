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

public class TestExtended {
	private final static String IDX_TEST_PATH = "D:/workspace_java/LucenePdfSuche/indexingTests";

	@Test
	public void testMultiAutor() throws IOException, ParseException {
		DocumentDetail docDet = new DocumentDetail();
		docDet.setDocName("Blablibla blubb oki doki wusch.pdf");
		docDet.setSort(null);
		docDet.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\Blablibla blubb oki doki wusch.pdf");
		docDet.setFolderCount(3);
		DocumentDetail docDet2 = new DocumentDetail();
		docDet2.setDocName("Irgendwas blubb.pdf");
		docDet2.setSort(IndexNames.SORT_ALPHA);
		docDet2.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\Irgendwas blubb.pdf");
		docDet2.setFolderCount(4);
		DocumentDetail docDet3 = new DocumentDetail();
		docDet3.setDocName("empty.pdf");
		docDet3.setSort(IndexNames.SORT_ALPHA);
		docDet3.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\empty.pdf");
		docDet3.setFolderCount(4);
		DocumentDetail[] exp = new DocumentDetail[]{docDet2, docDet, docDet3};
		
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, "   ", "MARIUS jemand", null, 400, null, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);
		
		Assert.assertArrayEquals(exp, res.toArray());
	}
	
	@Test
	public void testMultiExactDate() throws IOException, ParseException {
		DocumentDetail docDet = new DocumentDetail();
		docDet.setDocName("Blablibla blubb oki doki wusch.pdf");
		docDet.setSort(null);
		docDet.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\Blablibla blubb oki doki wusch.pdf");
		docDet.setFolderCount(3);
		DocumentDetail docDet2 = new DocumentDetail();
		docDet2.setDocName("Irgendwas blubb.pdf");
		docDet2.setSort(IndexNames.SORT_ALPHA);
		docDet2.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\Irgendwas blubb.pdf");
		docDet2.setFolderCount(4);
		DocumentDetail docDet3 = new DocumentDetail();
		docDet3.setDocName("empty.pdf");
		docDet3.setSort(IndexNames.SORT_ALPHA);
		docDet3.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\empty.pdf");
		docDet3.setFolderCount(4);
		DocumentDetail docDet4 = new DocumentDetail();
		docDet4.setDocName("Silberleiste.pdf");
		docDet4.setSort(IndexNames.SORT_ALPHA);
		docDet4.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\zzzz\\Silberleiste.pdf");
		docDet4.setFolderCount(4);
		DocumentDetail[] exp = new DocumentDetail[]{docDet, docDet3, docDet2, docDet4};
		
		List<String> dateList = new ArrayList<String>();
		dateList.add("2014-6-8");
		dateList.add("2014-6-9");
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, null, null, null, 400, dateList, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);

		Assert.assertArrayEquals(exp, res.toArray());
	}

	@Test
	public void testMultiNotExactDate() throws IOException, ParseException {
		DocumentDetail docDet = new DocumentDetail();
		docDet.setDocName("Blablibla blubb oki doki wusch.pdf");
		docDet.setSort(null);
		docDet.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\Blablibla blubb oki doki wusch.pdf");
		docDet.setFolderCount(3);
		DocumentDetail docDet2 = new DocumentDetail();
		docDet2.setDocName("Irgendwas blubb.pdf");
		docDet2.setSort(IndexNames.SORT_ALPHA);
		docDet2.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\Irgendwas blubb.pdf");
		docDet2.setFolderCount(4);
		DocumentDetail docDet3 = new DocumentDetail();
		docDet3.setDocName("empty.pdf");
		docDet3.setSort(IndexNames.SORT_ALPHA);
		docDet3.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\empty.pdf");
		docDet3.setFolderCount(4);
		DocumentDetail docDet4 = new DocumentDetail();
		docDet4.setDocName("Silberleiste.pdf");
		docDet4.setSort(IndexNames.SORT_ALPHA);
		docDet4.setPath("D:\\workspace_java\\LucenePdfSuche\\testDataSet\\zzzz\\Silberleiste.pdf");
		docDet4.setFolderCount(4);
		DocumentDetail[] exp = new DocumentDetail[]{docDet, docDet4, docDet3, docDet2};
		
		List<String> dateList = new ArrayList<String>();
		dateList.add("2014-6-8");
		dateList.add(">2014-6-9");
		List<DocumentDetail> res = SearchForQuery.searchFiles(IDX_TEST_PATH, null, null, null, 400, dateList, null);
		DocumentDetail[] resArr = new DocumentDetail[res.size()];
		
		resArr = res.toArray(resArr);

		Assert.assertArrayEquals(exp, res.toArray());
	}
}
