app.controller('RegisterCtrl', function($scope, $http, $resource, $routeParams){

    console.log("welcome to register-ctrl")

    $scope.data = {}

    $scope.onComplete = function(){
        console.log("welcome to onComplete")
    }

});