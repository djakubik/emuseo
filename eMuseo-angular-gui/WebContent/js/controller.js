
var app = angular.module('exhibitApp');
app.controller('exhibitRestServiceCtrl',['ExhibitRestService','$scope',function(ExhibitRestService, $scope){
       // calling service
       ExhibitRestService.query(function(ExhibitRestService){
              $scope.exhibits=ExhibitRestService;

       });
}]);
