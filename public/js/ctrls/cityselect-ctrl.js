app.controller('CitySelectCtrl', function($scope, $http, $resource, $routeParams, $location){
    $http.get("/app/cities").success(function (data){
        $scope.allCities = data
    })

    $scope.getCityFullName = function(city){
        return city.name + ', ' + city.country
    }

    $scope.onComplete = function(){
        $http.post("/users/update/city", this.citySelValue).success(function(response){
            $location.path('/home')
        })
    }
});
