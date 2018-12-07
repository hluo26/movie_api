var app = angular.module('myapp', []);

app.controller("MovieController", function ($scope,$http) {
    $scope.list;
    $scope.get1 = function()
    {console.log("here")
    	$http.get("http://localhost:8080/movie/service/get/list").then(
        		function(response){
        			console.log(response);
        			$scope.list = response.data;
        		});
    }
});

