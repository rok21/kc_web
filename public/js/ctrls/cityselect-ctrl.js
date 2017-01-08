app.controller('CitySelectCtrl', function($scope, $http, $resource, $routeParams){
    $http.get("/app/cities").success(function (data, status, headers, config){
        $scope.allCities = data
        console.log(data)
    })

    $scope.getCityFullName = function(city){
        return city.name + ', ' + city.country
    }
});
