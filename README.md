LIBRARIES
======================
Under lib directory, containing (1) Jackson, (2) Jsoup, (3) Twitter4J (4) Lucene



CODE FILES
======================
Under Crawler directory:

1- Crawler.java: Main class

2- TweetListener.java: listens to incoming status

3- Pojo.java: constructs a tweet object with the following attributes: Title - Created_at - favoriteCount- GeoLocation- Url- Hashtag- id - retweetCount- text - place - user

4- Processor.java: stores tweets in json objects and stores them in buffer. After 3000 tweets are collected, it flushes buffer to file.(10 MB file)

Under luceneIndexSearchFinal directory:

1- HashtagEntityImpl.java
2- SearcherModify.java This class is used by Search.html
3- SearchResult.java
4- TweetFields.java   Conatins
5- TweetGetSet.java
6- TweetIndexer.java: Contains code for indexing tweets, which are stored in files of 10 MB(in total 5GB). This class contains a method named indexTweets which performs the functionality of indexing.
7- TweetIndexer.java
8- URLEntityImpl.java


HOW TO EXECUTE
======================
Shell:  runIndexer.sh

Compile :
javac  -cp ".:./lib/*" ./src/luceneIndexSearchFinal/* -d ./bin

Run :
cd ./bin

java -Xmx6g -cp ".:../lib/*" luceneIndexSearchFinal.TweetIndexer 1 1 "./TwitterDataForDemo/" "./Index"

Additional Info
======================
This project consisted of a Java program with the Twitter Streaming API in order to collect and retrieve information about tweets. I have used Twitter4J in order to integrate my Java application with the Twitter service. This allowede me to catch tweets and store data about them.

Then I have use Apache Lucene to index tweet files, so that the user can enter a search query into search box and see the results.


Visualization
======================
The GUI pictures are provided.
![Alt text](/UI.png?raw=true "Search box")
![Alt text](/UI2.png?raw=true "Results page")





