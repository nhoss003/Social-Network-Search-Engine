package crawler;


import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Crawler {

	public static void main(String[] args) {
		
		
		
//		ConfigurationBuilder cb = new ConfigurationBuilder();
//		cb.setDebugEnabled(true)
//		  .setOAuthConsumerKey("rfuJJYcjqaZhStHv0BdkPw8sP")
//		  .setOAuthConsumerSecret("dXrnbJQC0L7x0tO7vkwbB0d2SoPEMrZXrMfj0PyUn8avjvoTYB")
//		  .setOAuthAccessToken("785209648253054976-bwmogezgrb594Bo8XqYBA483RpqI3qD")
//		  .setOAuthAccessTokenSecret("N9xjs6T9h69e3to2PgJfiJBMa7ydeOWx2pWoMBR5pbmWa");
//
//		Configuration config = cb.build();
		//new
		//28JtU1yrC4biXOiDevilm0Igu
		//	zyUg2cprQ0AXkSSFjoGYmxCcyBgVyIZhYn8aiDH0mZatqnwnT0
		//	1861370514-ko9UxaC11rgpYiow7VieQrbmdmXAxvssFEGFK8l
		//	PPgy6eBYrwVOiN1IYp9WnWGnFhkRg1ZkGFkR5THlDjkI2
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		  cb.setOAuthConsumerKey(args[0]);
		  cb.setOAuthConsumerSecret(args[1]);
		  cb.setOAuthAccessToken(args[2]);
		  cb.setOAuthAccessTokenSecret(args[3]);

		Configuration config = cb.build();

	    
		TwitterStream twitterStream = new TwitterStreamFactory(config).getInstance();
		TweetListener listener = new TweetListener();
		twitterStream.addListener(listener);
	  
	   
	    double[][] boundingbox = {{-117.075593,33.313946},{-71.108801,43.732952}};
	    FilterQuery filter = new FilterQuery();
	    filter.locations(boundingbox);
	    twitterStream.filter(filter);
	    
	
		
		
		

	}

}
