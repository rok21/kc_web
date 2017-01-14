app.controller('HomeCtrl', function($scope, $http, $resource, $routeParams, $location){

    $http.get("/users/user").success(function (data, status, headers, config){
        $scope.userInfo = data
        if(!$scope.userInfo.city){
            $location.path('/cityselect')
        }
    })

    ws = new WebSocket($scope.homewsURL)
    ws.onmessage = function(event){
                    console.log(event.data)
                    $scope.serverMsg = event.data
                    $scope.$apply();
            }
});