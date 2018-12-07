var mycontroller = angular.module('mycontroller',[]);

mycontroller.controller("SearchController", function ($scope,$http) {
    $scope.searchtext;
    $scope.searchgraduate;
    $scope.searchyear;
    $scope.searchable = false;
    $scope.check = function(){
      $scope.searchable = true;
      $scope.searchtext = $scope.text1;
      $scope.searchgraduate = $scope.grad1;
    };
});

mycontroller.controller("AddController", function ($scope) {
  
});
