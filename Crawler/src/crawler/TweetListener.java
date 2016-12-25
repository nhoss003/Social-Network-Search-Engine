package crawler;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class TweetListener implements StatusListener{
private Processor processor= new Processor() ;
	
	@Override
	public void onStatus(Status status) {
		 
		
		processor.processTweet(status);
		
	}
	
	@Override
	public void onStallWarning(StallWarning warning) {
		// TODO Auto-generated method stub
		System.out.println("Stall Warning :" + warning);
	}


	
	@Override
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
