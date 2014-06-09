package ch.moledor.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;

public class DocumentDetail implements Comparable<DocumentDetail> {
	
	private String path;
	private String docName;
	private int folderCount = 0;
	private String sort;
	private int size = 0;
	private int modified = 0;

	public DocumentDetail(String sort, Document d) {

		this.sort = sort;
		
		this.path = d.get(IndexNames.PATH);
		String[] pSplit = this.path.split("\\\\");
		folderCount = pSplit.length-1;
		docName = pSplit[pSplit.length-1];
		this.size = Integer.valueOf(d.get(IndexNames.SIZE));
		this.modified = Integer.valueOf(d.get(IndexNames.MODIFIED));
	}
	

	public DocumentDetail() {
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getDocName() {
		return docName;
	}


	public void setDocName(String docName) {
		this.docName = docName;
	}


	public int getFolderCount() {
		return folderCount;
	}


	public void setFolderCount(int folderCount) {
		this.folderCount = folderCount;
	}


	public String getSort() {
		return sort;
	}


	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public int getModified() {
		return modified;
	}


	public void setModified(int modified) {
		this.modified = modified;
	}


	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof DocumentDetail)) {
			return false;
		}
		
		DocumentDetail comp = (DocumentDetail) obj;
		return StringUtils.equals(this.path.toLowerCase(), comp.getPath().toLowerCase());

	}

	@Override
	public int compareTo(DocumentDetail comp) {
		
		if(IndexNames.SORT_ALPHA.toLowerCase().equals(sort.toLowerCase())) {
			return String.CASE_INSENSITIVE_ORDER.compare(this.docName, comp.getDocName());
		}
		else if(IndexNames.SORT_ALPHADESC.toLowerCase().equals(sort.toLowerCase())) {
			return String.CASE_INSENSITIVE_ORDER.compare(comp.getDocName(), this.docName);
		}
		else if(IndexNames.SORT_PATH.equals(sort)) {
			if(this.folderCount == comp.getFolderCount()) {
				return 0;
			}
			return this.folderCount > comp.getFolderCount() ? 1 : -1;
		}
		else if(IndexNames.SORT_MODIFIED.equals(sort)) {
			if(this.modified == comp.getModified()) {
				return 0;
			}
			return this.modified > comp.getModified() ? 1 : -1; 
		}
		else if(IndexNames.SORT_SIZE.equals(sort)) {
			if(this.size == comp.getSize()) {
				return 0;
			}
			return this.size > comp.getSize() ? 1 : -1; 
		}
		return 0;
	}

}
