package luceneIndexSearchFinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SearcherModify {
	
	private final String PATH_INDEX;
	private final String FIELD;
	private final int NUM_HITS;
	
	SearcherModify(String pathIndexFile, String field, int numHits){
		this.PATH_INDEX = pathIndexFile;
		this.FIELD = field;
		this.NUM_HITS = numHits;
	}
	
	private void termSearch(String line) throws ParseException{
		IndexReader reader;
		try {
			//indexDir = FSDirectory.open(new File(this.PATH_INDEX).toPath());
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(PATH_INDEX)));
			IndexSearcher indexSearcher = new IndexSearcher(reader);
			
			QueryParser queryParser = new QueryParser(FIELD, new KeywordAnalyzer());
			Query query = queryParser.parse(line);

			//Term term = new Term("user", "MattoPhoto");
			//Query query = new TermQuery(term);
			TopDocs topDocs = indexSearcher.search(query, this.NUM_HITS);
			returnResults(topDocs.scoreDocs, indexSearcher);
		}catch (IOException e) {
			System.err.println("Error in reading the index: "+e.getMessage());
		}
		
	}
	
	private void returnResults(ScoreDoc[] scoreDocs, IndexSearcher indexSearcher) throws IOException{
		/*
		if(null == scoreDocs || 0 == scoreDocs.length){
			System.out.println("Total tweet returned for given user: 0");
			return;
		}
		*/
		System.out.println("Total tweet returned for given user: "+scoreDocs.length);
		for(ScoreDoc sDoc : scoreDocs){
			Document retTweet = indexSearcher.doc(sDoc.doc); // sDoc.doc id docID
			System.out.println("Found Tweet: "+retTweet.get(TweetFields.CONTENT));
			System.out.println("for the user: "+retTweet.get(TweetFields.USER));
		}
	}

	public static void main(String[] args) throws ParseException, IOException {
		String usage =
			      "Usage:\tjava luceneIndexSearch.TweetSearcher [-pathIndex dir] [-field f] [-repeat n] [-queries file] [-query string] [-paging hitsPerPage]\n\nSee http://lucene.apache.org/core/4_1_0/demo/ for details.";
			    if (args.length > 0 && ("-h".equals(args[0]) || "-help".equals(args[0]))) {
			      System.out.println(usage);
			      System.exit(0);
			    }
			    
		String pathIndex = "/Users/reetas/RS_Work/Fall16/IR/Project/Data/Index";
		String field = "user";
		String queries = null;
		int repeat = 0;
	    String queryString = null;
	    int hitsPerPage = 10;
	    
	    for(int i = 0;i < args.length;i+=2) {
	        if ("-pathIndex".equals(args[i]))
	        	pathIndex = args[i+1];
	        else if ("-field".equals(args[i]))
	          field = args[i+1];
	        else if ("-queries".equals(args[i]))
	          queries = args[i+1];
	        else if ("-query".equals(args[i]))
	          queryString = args[i+1];
	        else if ("-repeat".equals(args[i]))
	          repeat = Integer.parseInt(args[i+1]);
	        else if ("-paging".equals(args[i])) {
	          hitsPerPage = Integer.parseInt(args[i+1]);
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
		
	    if (queries == null && queryString == null) {                        // prompt the user
	        System.out.println("Enter query: ");
	      }

	    String line = queryString != null ? queryString : in.readLine();
	    line = line.trim();
	    System.out.println("query: "+line+" field: "+ field);
	    
	    SearcherModify searcher = new SearcherModify(pathIndex, field, hitsPerPage);
		searcher.termSearch(line);
	}

}
