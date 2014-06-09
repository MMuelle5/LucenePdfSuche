package ch.moledor.model;

import java.util.HashMap;
import java.util.Map;


public final class IndexNames {

	public final static String IDXDIRPAHT = "D:/workspace_java/LuceneTutorial/indexing";
	
	
	public final static String CONTENT = "contents";
	public final static String AUTHOR = "author";
	public final static String MODIFIED = "modified";
	public final static String PATH = "path";
	public final static String SIZE = "size";

	public final static String SORT_ALPHA= "alphaSort";
	public final static String SORT_PATH= "pathSort";
	public static final String SORT_ALPHADESC = "alphaSortDesc";
	public static final String MAXHITS = "maxHits";
	
	
	public static final Map<String, String> SIZE_RANGES = new HashMap<String, String>();
	static{
//		SIZE_RANGES.put("leer", "[0 TO 0]");
		SIZE_RANGES.put("sklein", "+[000000001 TO 000010240]");
		SIZE_RANGES.put("klein",  "+[000010241 TO 000102400]");
		SIZE_RANGES.put("mittel", "+[000102401 TO 001024000]");
		SIZE_RANGES.put("gross",  "+[001024001 TO 016384000]");
		SIZE_RANGES.put("sgross", "+[016384001 TO 131072000]");
		SIZE_RANGES.put("riesig", "+[131072001 TO *]");
		
	};
}
