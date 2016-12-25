package luceneIndexSearchFinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class TweetSearcher {

	public static String PATH_INDEX;
	public static String FIELD;
	public static int NUM_HITS;

	public TweetSearcher(String pathIndexFile, String field, int numHits) {
		PATH_INDEX = pathIndexFile;
		FIELD = field;
		NUM_HITS = numHits;
		System.out.println(PATH_INDEX);
		System.out.println(FIELD);
		System.out.println(NUM_HITS);
	}

	public static String getLatLong(List<SearchResult> results) {
		String str = "[";

		boolean flag = false;
		for (SearchResult result : results) {
			String[] lo = result.getGeoLocation();
			if (!flag) {
				str += "[" + lo[0] + " , " + lo[1] + " ]";
				flag = true;
			} else
				str += ", [ " + lo[0] + " , " + lo[1] + " ]";

		}

		return str = str + "];";
	}

	public static List<SearchResult> uiSearch(String line, String[] fields) throws ParseException {
		IndexReader reader;

		try {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(PATH_INDEX)));
			IndexSearcher indexSearcher = new IndexSearcher(reader);

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

			Map<String, Float> boosts = new HashMap<String, Float>(3);
			boosts.put(TweetFields.HASHTAGS, (float) 5);
			boosts.put(TweetFields.USER, (float) 3);
			boosts.put(TweetFields.URLS, (float) 1);

			QueryParser queryParser = new MultiFieldQueryParser(fields, aWrapper, boosts);

			Query query = queryParser.parse(line);

			TopDocs topDocs = indexSearcher.search(query, NUM_HITS);
			return returnResults(topDocs.scoreDocs, indexSearcher);
		} catch (IOException e) {
			System.err.println("Error in reading the index: " + e.getMessage());
			return new ArrayList<SearchResult>(0);
		}

	}

	public static List<SearchResult> termSearch(String line) throws ParseException {
		IndexReader reader;

		try {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(PATH_INDEX)));
			IndexSearcher indexSearcher = new IndexSearcher(reader);

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

			QueryParser queryParser = new QueryParser(FIELD, aWrapper);

			Query query = queryParser.parse(line);

			TopDocs topDocs = indexSearcher.search(query, NUM_HITS);
			return returnResults(topDocs.scoreDocs, indexSearcher);
		} catch (IOException e) {
			System.err.println("Error in reading the index: " + e.getMessage());
			return new ArrayList<SearchResult>(0);
		}

	}

	public static List<SearchResult> returnResults(ScoreDoc[] scoreDocs, IndexSearcher indexSearcher)
			throws IOException {
		if (null == scoreDocs || 0 == scoreDocs.length) {
			System.out.println("Total tweet returned for given user: 0");
			return new ArrayList<SearchResult>(0);
		}

		System.out.println("Total tweet returned for given user: " + scoreDocs.length);
		List<SearchResult> searchResults = new ArrayList<SearchResult>(scoreDocs.length);
		for (ScoreDoc sDoc : scoreDocs) {
			Document retTweet = indexSearcher.doc(sDoc.doc); // sDoc.doc id
																// docID
			SearchResult result = new SearchResult(retTweet.getValues(TweetFields.GEOLOCATION),
					retTweet.get(TweetFields.CONTENT), retTweet.get(TweetFields.PLACE), retTweet.get(TweetFields.USER),
					retTweet.get(TweetFields.TIME), retTweet.getValues(TweetFields.URLS),
					retTweet.getValues(TweetFields.TITLEPAGE), retTweet.getValues(TweetFields.HASHTAGS));
			searchResults.add(result);
		}
		return searchResults;
	}

	public static void main(String[] args) throws ParseException, IOException {
		String usage = "Usage:\tjava luceneIndexSearch.TweetSearcher [-pathIndex dir] [-field f] [-repeat n] [-queries file] [-query string] [-paging hitsPerPage]\n\nSee http://lucene.apache.org/core/4_1_0/demo/ for details.";
		if (args.length > 0 && ("-h".equals(args[0]) || "-help".equals(args[0]))) {
			System.out.println(usage);
			System.exit(0);
		}
		
		String pathIndex = "./luceneIndexSearchFinal/bin/Index";
		String field = "user";
		String queries = null;
		int repeat = 0;
		String queryString = null;
		int hitsPerPage = 100;

		for (int i = 0; i < args.length; i += 2) {
			if ("-pathIndex".equals(args[i]))
				pathIndex = args[i + 1];
			else if ("-field".equals(args[i]))
				field = args[i + 1];
			else if ("-queries".equals(args[i]))
				queries = args[i + 1];
			else if ("-query".equals(args[i]))
				queryString = args[i + 1];
			else if ("-repeat".equals(args[i]))
				repeat = Integer.parseInt(args[i + 1]);
			else if ("-paging".equals(args[i])) {
				hitsPerPage = Integer.parseInt(args[i + 1]);
				if (hitsPerPage <= 0) {
					System.err.println("There must be at least 1 hit per page.");
					System.exit(1);
				}
			}
		}

		BufferedReader in = null;
		if (queries != null) {
			in = Files.newBufferedReader(Paths.get(queries), StandardCharsets.UTF_8);
		} else {
			in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		}

		if (queries == null && queryString == null) { // prompt the user
			System.out.println("Enter query: ");
		}

		String line = queryString != null ? queryString : in.readLine();
		line = line.trim();

		TweetSearcher searcher = new TweetSearcher(pathIndex, field, hitsPerPage);
		List<SearchResult> results = searcher.termSearch(line);
		if (null != results && 0 < results.size()) {
			for (SearchResult result : results) {
				System.out.println(result);
				System.out.println("---------------------------------------------------");
			}
		}
		System.out.println(getLatLong(results));

	}

}
