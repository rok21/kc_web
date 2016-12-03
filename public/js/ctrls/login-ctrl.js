app.controller('LoginCtrl', function($scope, $http, $resource, $routeParams, $location){

    $scope.onComplete = function(){
        $http.post("/login", this.$data)
        .success(function(response){
            if(response){
                $scope.$error = response
            }else{
                $location.path('/home')
            }
        })
    }
});