var app = angular.module('myapp', ['ngCookies']);

app.run(function($location,$rootScope) {
    $rootScope.server = {url: location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '')};
});

app.controller("MovieController", function ($scope,$http,$window,$cookies,$location,$rootScope) {
    $scope.list;
    $scope.getmovielist = function(text1,choice1)
    {
    	console.log(typeof choice1);
    	console.log(typeof text1);
    	console.log($rootScope.server.url);
    	var url = $rootScope.server.url+"/movie/service/get/list";
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
    	var url = $rootScope.server.url+"/movie/service/delete";
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
        			alert(name + " deleted");
   //     			location.reload();
        		});
    };
    $scope.CreateMovieToList = function(movie)
    {
    	console.log(movie.name);
    	var url = $rootScope.server.url+"/movie/service/post";
        $http({
            method: 'POST',
            url: url,
            params: {
                name: movie.name,
                year: movie.year,
                genre: movie.genre,
                watched: movie.seen
            }
            //headers: {'Content-Type': 'application/x-www-form-urlencoded'}
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
    
    //where the magic happens
    $scope.selected = {};
    /*$scope.editMovie = function(movie)
    {
    	$scope.selected = angular.copy(movie);
    };
    $scope.getTemplate = function (movie) {  
        if (movie.name === $scope.selected.name){  
            return 'edit.html';  
        }  
        else return 'display.html';  
    };
    $scope.reset = function () {  
    	   $scope.selected = {};  
    };*/
    
    $scope.saveMovie = function(movie) {

        console.log(movie);
        $cookies.put("name", movie.name);
        $cookies.put("genre", movie.genre);
        $cookies.put("year", movie.year);
        $cookies.put("watched", movie.watched);
        //if($scope.select!=null)
        $window.location.href = $rootScope.server.url+'/movie/Update_movie.html';
        //alert(name + " updated!");

    };
   

});

app.controller('updateController',function ($scope,$http,$window,$cookies,$location,$rootScope){
	$scope.originalName = $cookies.get("name");
	$scope.newName = $scope.originalName;
	$scope.year = parseInt($cookies.get("year"),10);
	$scope.genre = $cookies.get("genre");
	if($cookies.get("watched")){
		$scope.watched = "True";
	}
	else
	{
		$scope.watched = "False";
	}
	
    $scope.updateMovie = function(){
    	var url=$rootScope.server.url+"/movie/service/put/movie";
    	$http({
    		method:'PUT',
    		url:url,
    		data: $.param({
    			name: $scope.originalName,
                changedName: $scope.newName,
                changedYear: $scope.year,
                changedGenre: $scope.genre,
                changedWatched: $scope.watched
            }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            // handle success things
        	console.log(response);
        	alert("Updated successfully!");
        }).catch(function (err	) {
            // handle error things
        	console.log(err);  
        	alert("Something Wrong!");
        });
    };
    
	
	
});

