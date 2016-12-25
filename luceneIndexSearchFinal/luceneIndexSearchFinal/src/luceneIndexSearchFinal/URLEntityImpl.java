package luceneIndexSearchFinal;

import twitter4j.URLEntity;

public class URLEntityImpl implements URLEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -628359237689620804L;
	private int end;
	private int start;
	private String expandedURL;
	private String displayURL;
	private String text;
	private String url;
	
	public URLEntityImpl(){
		
	}
	public URLEntityImpl(URLEntity urlEntity){
		this.end = urlEntity.getEnd();
		this.start = urlEntity.getStart();
		this.expandedURL = urlEntity.getExpandedURL();
		this.displayURL = urlEntity.getDisplayURL();
		this.text = urlEntity.getText();
		this.url = urlEntity.getURL();
	}
	
	public static URLEntityImpl[] getURLEntityImpl(URLEntity[] urlEntity){
		int len = urlEntity.length;
		URLEntityImpl[] entityImpl = new URLEntityImpl[len];
		for (int i=0; i<len; ++i)
			entityImpl[i] = new URLEntityImpl(urlEntity[i]);
		return entityImpl;
	}

	public void setDisplayURL(String displayURL)
	{
		this.displayURL = displayURL;
	}
	@Override
	public String getDisplayURL() {
		return this.displayURL;
	}

	public void setEnd(int end){
		this.end = end;
	}
	@Override
	public int getEnd() {
		return this.end;
	}

	public void setExpandedURL(String expandedURL){
		this.expandedURL = expandedURL;
	}
	@Override
	public String getExpandedURL() {
		return this.expandedURL;
	}

	public void setStart(int start){
		this.start = start;
	}
	@Override
	public int getStart() {
		return this.start;
	}

	public void setText(String text){
		this.text = text;
	}
	@Override
	public String getText() {
		return this.text;
	}

	public void setURL(String url){
		this.url = url;
	}
	@Override
	public String getURL() {
		return this.url;
	}
	
}
