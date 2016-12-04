app.controller('HomeCtrl', function($scope, $http, $resource, $routeParams){

    $http.get("/users/user").success(function (data, status, headers, config){
        $scope.userInfo = data
    })
});