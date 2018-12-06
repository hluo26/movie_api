<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.net.*" %>
<%@page import="java.io.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="css/mine.css" />
<title>My movie API</title>
</head>
<body>
	<div class="jumbotron grey-gradient color-block">
      <div class="container">
        <div class="row">
          <div class="col-sm-2">
            <img src="image/icon1.jpg" width="150" height="150"/>
          </div>
          <div class="col-sm-10" id="title1">
          <br><br>
            <h2>Movie API example</h2>
            <p>Only for learning servlet</p>
          </div>
        </div>
      </div>
    </div>
    
    <div class="container">
      <div class="container">
        <div class="row">
          <div class="col-sm-2">
          	<div class="row"><a href="API_test.jsp">Search</a></div>
          	<div class="row"><a href="index.html">Back</a></div>	
          </div>
          <div class="col-sm-10">
            <h5>Testing movie API</h5>
           	<%
           		String url = request.getRequestURL().toString();
           		String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
            	URL dest = new URL(baseURL+"service/get/list");
            	URLConnection yc = dest.openConnection();
            	BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                        yc.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null)
    				System.out.println(inputLine);
				in.close();
           	%>
          </div>
        </div>
      </div>
    </div>
</body>
</html>