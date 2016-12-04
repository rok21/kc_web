app.controller('LoginCtrl', function($rootScope, $scope, $http, $resource, $routeParams, $location){

    checkCurrentUser = function(){
        $http.get("/login/current")
         .success(function(response){
            if(response){
                onLoggedIn(response)
            }
        })
    }

    onLoggedIn = function(username){
        $rootScope.user = username
        $location.path('/home')
    }

    $scope.onComplete = function(){
        $http.post("/login", this.$data)
        .success(function(response){
            if(response){
                $scope.$error = response
            }else{
                checkCurrentUser()
            }
        })
    }

    checkCurrentUser()
});