app.controller('LoginCtrl', function($scope, $http, $resource, $routeParams, $location){

    $http.get("/login/already")
    .success(function(response){
        if(response === "true"){
            onLoggedIn()
        }
    })

    $scope.onComplete = function(){
        $http.post("/login", this.$data)
        .success(function(response){
            if(response){
                $scope.$error = response
            }else{
                onLoggedIn()
            }
        })
    }

    onLoggedIn = function(){
        $location.path('/home')
    }
});