package crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.databind.ObjectMapper;

import twitter4j.Status;
import twitter4j.URLEntity;

public class Processor {
	private StringBuffer sb=new StringBuffer();  
	private ObjectMapper mapper = new ObjectMapper();
	private int tweetCount=0;
	private int fileCount=0;
	
	 Set<Long> myset = new HashSet<Long>(3000);
	
	public void processTweet(Status status) {
		if(tweetCount==1500000){return;}
		System.out.println(tweetCount);
		if(tweetCount == 3000){myset.clear();}
		
		if(!myset.contains(status.getId())){
		myset.add(status.getId());
		try {
		Pojo tweet=getPojo(status);
		
			bufferAndFile(tweet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		}
		
	}
	private Pojo getPojo(Status status) {
		Pojo pojo= new Pojo();
		
		DateFormat df = new SimpleDateFormat("MM:dd:yyyy HH:mm:ss");
		 
		pojo.setCreated_at(df.format(status.getCreatedAt()));
		pojo.setFavoriteCount(status.getFavoriteCount());
		pojo.setGeoLocation(status.getGeoLocation());
		pojo.setId(status.getId());
		URLEntity[] urls = status.getURLEntities();
		String[] URL = new String[urls.length];
		for(int i = 0; i < urls.length; ++i){
			URL[i] = urls[i].getURL(); 
			
			if(URL[i] != null){
		//	Document doc = Jsoup.connect(URL[i]).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36").get();
				//Document doc = Jsoup.connect(URL[i]).userAgent("Mozilla/5.0 (X11; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0").get();
				
				Document doc;
				try {
					doc = Jsoup.connect(URL[i]).ignoreHttpErrors(true).get();
				
				
				pojo.setTitle(doc.title()); 
				pojo.setTitle(doc.title()); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
//			if(doc.title()!=null){
//				System.out.println(doc.title().toString());
//			}
			}
			
			}
		pojo.setUrl(URL);
		
//		if(status.getGeoLocation()!=null){
//			System.out.println(status.getGeoLocation().toString());
//		}
		
//		if(status.getURLEntities()!=null){
//			System.out.println(status.getURLEntities().toString());
//		}
	
		pojo.setHashtag(status.getHashtagEntities());
//		if(status.getHashtagEntities()!=null){
//			System.out.println(status.getHashtagEntities().toString());
//		}
	
		pojo.setRetweetCount(status.getRetweetCount());
		pojo.setText(status.getText());
		pojo.setUser(status.getUser());
		pojo.setPlace(status.getPlace());
		
		return pojo;
		
	}
	public void bufferAndFile(Pojo tweet) throws IOException{
		
		
		
		
		tweetCount++; 
		//System.out.println(tweetCount);
		sb.append(mapper.writeValueAsString(tweet)+"\n");
		
		if(tweetCount%3000==0){
		
			fileCount++;
		File file = new File("file"+fileCount+".txt");
		FileWriter fw = new FileWriter(file);
		fw.write(sb.toString()); 
	    fw.flush();
	    fw.close();
	    //sb.setLength(0);
	    sb = new StringBuffer();
	    
		}
		

	}
}
