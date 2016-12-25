package luceneIndexSearchFinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LatLonPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TweetIndexer {

	private final String PATH_TWEETS;
	private final String PATH_INDEX;
	private IndexWriter indexWriter = null;

	public static final Version luceneVersion = Version.LUCENE_6_3_0;

	public TweetIndexer(String pathTweetFile, String pathIndexFile) {
		this.PATH_TWEETS = pathTweetFile;
		this.PATH_INDEX = pathIndexFile;
	}

	private int indexTweets(int startTweetFile, int endTweetFile) {
		int countIndexed = 0;

		Map<String, Analyzer> analyzerPerField = new HashMap<>();
		analyzerPerField.put(TweetFields.GEOLOCATION, new KeywordAnalyzer());
		analyzerPerField.put(TweetFields.CONTENT, new StandardAnalyzer());
		analyzerPerField.put(TweetFields.PLACE, new KeywordAnalyzer());
		analyzerPerField.put(TweetFields.USER, new KeywordAnalyzer());
		analyzerPerField.put(TweetFields.TIME, new KeywordAnalyzer());
		analyzerPerField.put(TweetFields.URLS, new KeywordAnalyzer());
		analyzerPerField.put(TweetFields.TITLEPAGE, new KeywordAnalyzer());
		analyzerPerField.put(TweetFields.HASHTAGS, new KeywordAnalyzer());

		PerFieldAnalyzerWrapper aWrapper = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), analyzerPerField);

		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(aWrapper);
		indexWriterConfig.setOpenMode(OpenMode.CREATE);
		Directory indexDir;
		try {
			indexDir = FSDirectory.open(new File(this.PATH_INDEX).toPath());
			indexWriter = new IndexWriter(indexDir, indexWriterConfig);
		} catch (IOException e) {
			System.err.println("Error in opening the index: " + e.getMessage());
		}

		for (int fileCount = startTweetFile; fileCount <= endTweetFile; ++fileCount) {
			ObjectMapper mapper = new ObjectMapper();
			try (BufferedReader bfr = new BufferedReader(
					new FileReader(this.PATH_TWEETS + "/titleTweetMultiThreadProcess" + fileCount + ".json"))) {
				JsonParser jp = new JsonFactory().createParser(bfr);
				jp.setCodec(mapper);
				jp.nextToken();
				while (jp.hasCurrentToken()) {
					Document doc = new Document();

					TweetGetSet tweet = jp.readValueAs(TweetGetSet.class);
					doc.add(new LatLonPoint(TweetFields.GEOLOCATION, tweet.getLocation()[0], tweet.getLocation()[1]));
					doc.add(new StoredField(TweetFields.GEOLOCATION, tweet.getLocation()[0]));
					doc.add(new StoredField(TweetFields.GEOLOCATION, tweet.getLocation()[1]));
					
					doc.add(new TextField(TweetFields.CONTENT, tweet.getText(), Field.Store.YES));
					doc.add(new StringField(TweetFields.PLACE, tweet.getPlace(), Field.Store.YES));
					doc.add(new StringField(TweetFields.USER, tweet.getUser(), Field.Store.YES));
					
					doc.add(new LongPoint(TweetFields.TIME, tweet.getTime().getTime()));
					doc.add(new StoredField(TweetFields.TIME, tweet.getTime().getTime()));
	
					String[] urls = tweet.getUrls();
					if (null != urls && 0 < urls.length) {
						for (String url : urls) {
							if(null != url)
								doc.add(new StringField(TweetFields.URLS, url, Field.Store.YES));
						}

						String[] titles = tweet.getTitleUrls();
						if (null != titles && 0 < titles.length) {
							for (String title : titles) {
								if(null != title)
									doc.add(new StringField(TweetFields.TITLEPAGE, title, Field.Store.YES));
							}
						}
					}

					HashtagEntityImpl[] hashtags = tweet.getHashtags();
					if (null != hashtags && 0 < hashtags.length) {
						for (HashtagEntityImpl hashtag : hashtags) {
							if(null != hashtag && null != hashtag.getText())
								doc.add(new StringField(TweetFields.HASHTAGS, hashtag.getText(), Field.Store.YES));
						}
					}


					indexWriter.addDocument(doc);
					++countIndexed;

					jp.nextToken();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			indexWriter.commit();
		} catch (IOException e1) {
			System.err.println("Error in writing the index: " + e1.getMessage());
		}
		try {
			indexWriter.close();
		} catch (IOException e) {
			System.err.println("Error in closing the index: " + e.getMessage());
		}
		return countIndexed;
	}

	public static void main(String[] args) {
		final int startTweetFile = Integer.parseInt(args[0]);
		final int endTweetFile = Integer.parseInt(args[1]);
		final String pathTweetFile = args[2];
		final String pathIndexFile = args[3];

		TweetIndexer indexer = new TweetIndexer(pathTweetFile, pathIndexFile);
		long startTime = System.currentTimeMillis();
		int countIndexed = indexer.indexTweets(startTweetFile, endTweetFile);
		long endTime = System.currentTimeMillis();
		long timeTaken = (endTime - startTime) / 1000;
		System.out.println("Time taken to index " + countIndexed + " tweets = " + timeTaken + " seconds");
	}

}
