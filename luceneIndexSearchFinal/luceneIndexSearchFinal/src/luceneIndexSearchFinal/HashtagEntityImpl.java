package luceneIndexSearchFinal;

import twitter4j.HashtagEntity;

public class HashtagEntityImpl implements HashtagEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8503009043640563906L;
	private int end;
	private int start;
	private String text;

	public HashtagEntityImpl() {

	}
	public HashtagEntityImpl(HashtagEntity hashtagEntity) {
		this.end = hashtagEntity.getEnd();
		this.start = hashtagEntity.getStart();
		this.text = hashtagEntity.getText();
	}
	
	public static HashtagEntityImpl[] getHashtagEntityImpl(HashtagEntity[] hashtagEntity){
		int len = hashtagEntity.length;
		HashtagEntityImpl[] entityImpl = new HashtagEntityImpl[len];
		for (int i=0; i<len; ++i)
			entityImpl[i] = new HashtagEntityImpl(hashtagEntity[i]);
		return entityImpl;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public int getEnd() {
		return this.end;
	}

	public void setStart(int start) {
		this.start = start;
	}

	@Override
	public int getStart() {
		return this.start;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return this.text;
	}

}

