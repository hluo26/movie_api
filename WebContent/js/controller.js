var app = angular.module('myapp', []);

app.controller("MovieController", function ($scope,$http) {
    $scope.list;
    $scope.getmovielist = function(text1,choice1)
    {
    	console.log(typeof choice1);
    	console.log(typeof text1);
    	var url = "http://localhost:8080/movie/service/get/list";
    	$http(
    			{
    		        method: 'GET',
    		        url: url,
    		        params: {orderby: choice1, value: text1}
    		    }
    			).then(
        		function(response){
        			console.log(response);
        			$scope.list = response.data;
        		});
    };
    $scope.removeMovieFromList = function(name)
    {
    	console.log(typeof name);
    	var url = "http://localhost:8080/movie/service/delete";
    	$http(
    			{
    		        method: 'DELETE',
    		        url: url,
    		        params: {name: name}
    		    }
    			).then(
        		function(response){
        			console.log(response);
        			$scope.list = response.data;
        		});
    };
    $scope.CreateMovieToList = function(movie)
    {
    	console.log(movie.name);
    	var url = "http://localhost:8080/movie/service/post";
    	$http({
    		method: 'POST',
	        url: url,
	        params: {name: movie.name, year: movie.year, genre: movie.genre, watched: movie.seen}
    	}).then(
        		function(response){
        			alert("Success");
        		}).catch(function() {
        			alert("Problem");
        		});
    };
});

