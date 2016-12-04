app.controller('HeaderCtrl', function($rootScope, $scope, $http, $resource, $routeParams, $location){

    $scope.$user = $rootScope.user
    $http.get("/login/current")
         .success(function(response){
            if(response){
                $rootScope.user = response
            }
        })

    $scope.$watch(
        function(scope){ return scope.user },
        function(newVal, oldVal){
            $scope.$user = $rootScope.user
            console.log("user="+newVal)
        }
    )

    $scope.onLogout = function(){
        console.log("goodbye..")
        $http.post("/login/logout")
            .success(function(response){
                $rootScope.user = null
                $location.path('/login')
            })
    }
});