package crawler;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Place;
import twitter4j.User;

public class Pojo {

	
	private String title;
	private String created_at;
	private int favoriteCount;
	private GeoLocation geoLocation;
	private String[] url;
	private HashtagEntity[] hashtag;
	private long id;
	private int retweetCount;
	private String text;
	private Place place;
	private User user;

	public String getCreated_at() {
		return created_at;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public GeoLocation getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}

	public String getText() {
		return text;
	}

	public void setText(String string) {
		this.text = string;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public String[] getUrl() {
		return url;
	}

	public void setUrl(String[] url) {
		this.url = url;
	}

	public HashtagEntity[] getHashtag() {
		return hashtag;
	}

	public void setHashtag(HashtagEntity[] hashtag) {
		this.hashtag = hashtag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
