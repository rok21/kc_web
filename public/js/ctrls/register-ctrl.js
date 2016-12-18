app.controller('RegisterCtrl', function(validation, $rootScope,  $scope, $http, $resource, $routeParams, $location){

    $scope.onComplete = function(){
        var formError = validation.validRegForm(this.$data)
        if(formError.length === 0){
            $http.post("/registration/save", this.$data)
                 .success(onSuccess)
                 .error(function(xhr){ $scope.$error = xhr.responseText})
        }else{
            this.$error = formError
        }
    }

    onSuccess = function(){
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
});