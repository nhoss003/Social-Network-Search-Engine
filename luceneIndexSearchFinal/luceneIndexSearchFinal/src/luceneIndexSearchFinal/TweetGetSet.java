package luceneIndexSearchFinal;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TweetGetSet {
	private long id;
	private Date timestamp;
	private double[] location;
	private String text;
	private String lang;
	private String userName;
	private String screenName;
	private String source;
	private long[] contributors;
	private String[] withheldCountries;		//list of country codes where the tweet is withheld
	private String place;
	private boolean isFavorite;
	private int favoriteCount;
	private boolean isSensitive;
	private boolean isRetweet;
	private int retweetCount;
	private boolean isTruncated;
	private int accessLevel;
	@JsonProperty("urlentity")
	private URLEntityImpl[] urlEntity;
	private String[] urls;
	private String[] titleUrls;
	@JsonProperty("hashtags")
	private HashtagEntityImpl[] hashtags;
	
    public void setID(long id){
		this.id = id;
	}
	public long getID(){
		return this.id;
	}
	
	public void setTime(Date time){
		this.timestamp = time;
	}
	public Date getTime(){
		return this.timestamp;
	}
	
	public void setLocation(double[] location){
		this.location = location;
	}
	public double[] getLocation(){
		return this.location;
	}
	
	public void setText(String text){
		this.text = text;
	}
	public String getText(){
		return this.text;
	}
	
	public void setLang(String lang){
		this.lang = lang;
	}
	public String getLang(){
		return this.lang;
	}
	
	public void setUser(String userName){
		this.userName = userName;
	}
	public String getUser(){
		return this.userName;
	}
	
	public void setScreenName(String screenName){
		this.screenName = screenName;
	}
	public String getScreenName(){
		return this.screenName;
	}
	
	public void setSource(String source){
		this.source = source;
	}
	public String getSource(){
		return this.source;
	}
	
	public void setContributors(long[] contributors){
		this.contributors = contributors;
	}
	public long[] getContributors(){
		return this.contributors;
	}
	
	public void setWithheldCountries(String[] withheldCountries){
		this.withheldCountries = withheldCountries;
	}
	public String[] getWithheldCountries(){
		return this.withheldCountries;
	}
	
	public void setPlace(String place){
		this.place = place;
	}
	public String getPlace(){
		return this.place;
	}
	
	public void setIsFavorite(boolean isFavorite){
		this.isFavorite = isFavorite;
	}
	public boolean getIsFavorite(){
		return this.isFavorite;
	}
	
	public void setFavoriteCount(int favoriteCount){
		this.favoriteCount = favoriteCount;
	}
	public int getFavoriteCount(){
		return this.favoriteCount;
	}
	
	public void setIsSensitive(boolean isSensitive){
		this.isSensitive = isSensitive;
	}
	public boolean getIsSensitive(){
		return this.isSensitive;
	}
	
	public void setIsRetweet(boolean isRetweet){
		this.isRetweet = isRetweet;
	}
	public boolean getIsRetweet(){
		return this.isRetweet;
	}
	
	public void setRetweetCount(int retweetCount){
		this.retweetCount = retweetCount;
	}
	public int getRetweetCount(){
		return this.retweetCount;
	}
	
	public void setIsTruncated(boolean isTruncated){
		this.isTruncated = isTruncated;
	}
	public boolean getIsTruncated(){
		return this.isTruncated;
	}
	
	public void setAccessLevel(int accessLevel){
		this.accessLevel = accessLevel;
	}
	public int getAccessLevel(){
		return this.accessLevel;
	}
	
	
	public void setUrlEntity(URLEntityImpl[] urlEntity){
		this.urlEntity = urlEntity;
	}
	public URLEntityImpl[] getURLEntity(){
		return this.urlEntity;
	}
	
	public void setUrls(String[] urls){
		this.urls = urls;
	}
	public String[] getUrls(){
		return this.urls;
	}
	
	public void setTitleUrls(String[] titleUrls){
		this.titleUrls = titleUrls;
	}
	public String[] getTitleUrls(){
		return this.titleUrls;
	}
	
	public void setHashtags(HashtagEntityImpl[] hashtags){
		this.hashtags = hashtags;
	}
	public HashtagEntityImpl[] getHashtags(){
		return this.hashtags;
	}
	
}
