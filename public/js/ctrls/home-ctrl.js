app.controller('HomeCtrl', function($scope, $http, $resource, $routeParams, $location){

    $http.get("/users/user").success(function (data, status, headers, config){
        $scope.userInfo = data
        if(!$scope.userInfo.city){
            $location.path('/cityselect')
        }
    })

    $scope.timers = []
    findTimer = function(key){ return _.find($scope.timers, function(e){ return e.key === key }) }

    ws = new WebSocket($scope.homewsURL)
    ws.onmessage = function(event){
                    if(event.data.startsWith("time")){
                        var key = event.data.split(':')[0]
                        var target = findTimer(key)
                        if(!target){
                            target = {key: key, data: ''}
                            $scope.timers.push(target)
                        }
                        target.data = event.data
                    }else{
                        $scope.serverMsg = event.data
                    }
                    $scope.$apply();
            }


});

app.directive('timer', function () {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: "/assets/html/timer.html",
            controller: function($scope, $http, $resource, $routeParams, $location){

            }
        }
    });