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
    			).then(console.log(response),
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
            data: $.param({
                name: movie.name,
                year: movie.year,
                genre: movie.genre,
                watched: movie.seen
            }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            // handle success things
        	console.log(response);
        	alert("Creating successfully!");
        }).catch(function (err	) {
            // handle error things
        	console.log(err);  
        	alert("Something Wrong!");
        });
    };
});

