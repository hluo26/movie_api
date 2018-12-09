var app = angular.module('myapp', []);

app.controller("MovieController", function ($scope,$http) {
    $scope.list;
    
    $scope.getmovielist = function(text1,choice1)
    {console.log(choice1);
    console.log(text1);
    var url = "http://localhost:8080/movie/service/get/list?Orderby="+choice1+"&Value="+text1;
    console.log(url);
    	$http.get(url).then(
        		function(response){
        			console.log(response);
        			$scope.list = response.data;
        		});
    };
});

