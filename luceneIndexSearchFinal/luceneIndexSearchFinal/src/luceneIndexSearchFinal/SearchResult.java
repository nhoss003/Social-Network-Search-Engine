package luceneIndexSearchFinal;

public class SearchResult {
	private final String[] geoLocation;
	private final String content;
	private final String place;
	private final String userName;
	private final String time;
	private final String[] urls;
	private final String[] titleURLs;
	private final String[] hashtags;
	
	
	public SearchResult(String[] geoLocation, String content, String place, String userName, String time, String[] urls, String[] titleURLs, String[] hashtags){
		this.geoLocation = geoLocation;
		this.content = content;
		this.place = place;
		this.userName = userName;
		this.time = time;
		this.urls = urls;
		this.titleURLs = titleURLs;
		this.hashtags = hashtags;
	}
	
	public String[] getGeoLocation(){
		return this.geoLocation;
	}
	public String getContent(){
		return this.content;
	}
	public String getPlace(){
		return this.place;
	}
	public String getUserName(){
		return this.userName;
	}
	public String getTime(){
		return this.time;
	}
	public String[] getUrls(){
		return this.urls;
	}
	public String[] getTitleUrls(){
		return this.titleURLs;
	}
	public String[] getHashTags(){
		return this.hashtags;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("GeoLocation: [");
		sb.append(geoLocation[0]);
		sb.append(", ");
		sb.append(geoLocation[1]);
		sb.append("]\n");
		sb.append("Content: ");
		sb.append(content);
		sb.append("\n");
		sb.append("Place: ");
		sb.append(place);
		sb.append("\n");
		sb.append("UserName: ");
		sb.append(userName);
		sb.append("\n");
		sb.append("Time: ");
		sb.append(time);
		sb.append("\n");
		sb.append("URLs: [");
		for (int i = 0; i < urls.length; ++i)
		{
			if (i != 0)
				sb.append(", ");
			sb.append(urls[i]);
		}
		sb.append("]\n");
		sb.append("TitleURLs: [");
		for (int i = 0; i < titleURLs.length; ++i)
		{
			if (i != 0)
				sb.append(", ");
			sb.append(titleURLs[i]);
		}
		sb.append("]\n");
		sb.append("HashTags: [");
		for (int i = 0; i < hashtags.length; ++i)
		{
			if (i != 0)
				sb.append(", ");
			sb.append(hashtags[i]);
		}
		sb.append("]\n");
		
		return sb.toString();
	}
}
