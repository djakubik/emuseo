
var app = angular.module('serviceDemoApp');
app.controller('serviceCtrl',['DemoService','$scope',function(DemoService, $scope){
       // calling service
       DemoService.query(function(DemoService){
              $scope.response=DemoService;

       });
}]);
