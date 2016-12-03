app.controller('HomeCtrl', function($scope, $http, $resource, $routeParams){

    console.log("wazzap.")

    var logResult = function (str, data, status, headers, config)
            {
              var zz = str + "\n\n" +
                "data: " + data + "\n\n" +
                "status: " + status + "\n\n" +
                "headers: " + headers + "\n\n"
              console.log(zz)
            };
        $http.get("/users/user").success(function (data, status, headers, config){
            $scope.userInfo = data
            logResult("GET SUCCESS", data, status, headers, config);
        })
        .error(function (response){
           logResult(response);
       })
});