<%@page import="luceneIndexSearchFinal.*, java.util.List, java.util.ArrayList"%>
<html>
<head>
<title>Tweet Search Engine</title>
</head>
<head>
<style>
#map {
	height: 200px;
	width: 30%;
	position: absolute;
	bottom: 0;
	right: 0;
}
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
</style>
</head>
<body>


	<%!String[] colors = { "Green", "yellow", "Black", "Red", "pink" };%>



	<%
		String q = request.getParameter("search");
		String select[] = request.getParameterValues("id");
		String selected = null;
		if (select != null && select.length != 0) {

			for (int i = 0; i < select.length; i++) {
				if (select[i] != null) {
					selected = select[i];
				}
			}
		}
		TweetSearcher searcher = new TweetSearcher("C:\\Users\\Niloufar\\Desktop\\index", selected, 100);
	%>

	<jsp:include page="search3.html">
		<jsp:param value="<%=q%>" name="query" />
	</jsp:include>




<%! String loc; %>
<table style="width:100%">

		<%
			List<SearchResult> results = TweetSearcher.uiSearch(q,select);
		loc = TweetSearcher.getLatLong(results);
		int j=1;
		int k =0;
		for (SearchResult result : results) {
			//String loc = TweetSearcher.getLatLong(results);
			//String[] lo = result.getGeoLocation();
			//String[][] loc=new String[results.size()][2];
			//loc[k][0]=lo[0];
			//loc[k][1]=lo[1];
			%>
			 
				 <tr  style="background-color:pink;">
				 <th><%=j %>: Tweet Content</th>
				 <th><% out.println(result.getContent()); %></th></tr>
				 <tr><th>UserName</th><th><% out.println(result.getUserName()); %></th></tr>
				 <tr><th>Place</th><th><% out.println(result.getPlace()); %></th></tr>
				
				 <%
			String[] urls = result.getUrls();
			for (int i = 0; i < urls.length; ++i) {
				
%>
<tr><th>Link</th>
<th><a href="<%=urls[i]%>"><%=urls[i]%></a></th></tr>

<%
	}
%>


<%
String[] hashtags=result.getHashTags();%>
<% 
			for (int i = 0; i < hashtags.length; ++i) {
%>
<tr><th>Hashtags</th>
<th><%=hashtags[i]%></th></tr>

<%
	}
%>



<%
String[] titleURLs=result.getTitleUrls();
			for (int i = 0; i < titleURLs.length; ++i) {
%>
<tr><th>titleURLs</th>
<th><%=titleURLs[i]%></th></tr>

<%
	}
%>

<%

String[] geoLocation=result.getGeoLocation();


%>
<tr><th>GeoLocation</th>
<th><%out.print("lat: "); %><%=geoLocation[0]%><%out.print(" , long: "); %><%=geoLocation[1]%></th></tr>



			  <% j++; k++; } %>
			 </TABLE>
	<div id="map"></div>
    <script>

      // The following example creates complex markers to indicate beaches near
      // Sydney, NSW, Australia. Note that the anchor is set to (0,32) to correspond
      // to the base of the flagpole.

      function initMap() {
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 3,
          center: {lat: 39.5, lng: -98.35}
        });

        setMarkers(map);
      }

      // Data for the markers consisting of a name, a LatLng and a zIndex for the
      // order in which these markers should display on top of each other.
      /*
      var beaches = [
        [-33.890542, 151.274856],
        [-33.923036, 151.259052],
        [-34.028249, 151.157507],
        [ -33.80010128657071, 151.28747820854187],
        [-33.950198, 151.259302]
      ];
*/
 var beaches=<%=loc%>;
      function setMarkers(map) {
        // Adds markers to the map.

        // Marker sizes are expressed as a Size of X,Y where the origin of the image
        // (0,0) is located in the top left of the image.

        // Origins, anchor positions and coordinates of the marker increase in the X
        // direction to the right and in the Y direction down.
        var image = {
          url: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png',
          // This marker is 20 pixels wide by 32 pixels high.
          size: new google.maps.Size(20, 32),
          // The origin for this image is (0, 0).
          origin: new google.maps.Point(0, 0),
          // The anchor for this image is the base of the flagpole at (0, 32).
          anchor: new google.maps.Point(0, 32)
        };
        // Shapes define the clickable region of the icon. The type defines an HTML
        // <area> element 'poly' which traces out a polygon as a series of X,Y points.
        // The final coordinate closes the poly by connecting to the first coordinate.
        var shape = {
          coords: [1, 1, 1, 20, 18, 20, 18, 1],
          type: 'poly'
        };
        for (var i = 0; i < beaches.length; i++) {
          var beach = beaches[i];
          var marker = new google.maps.Marker({
            position: {lat: beach[0], lng: beach[1]},
            map: map,
           // icon: image,
            shape: shape,
            
          });
        }
      }
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCj8azuUXrmIY03tmg6bB7DVG6fkm2haEI&callback=initMap">
    </script>
	

	</body>
</html>
