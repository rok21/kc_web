app.controller('RegisterCtrl', ['validation', '$scope', '$http', '$resource', '$routeParams',
    function(validation, $scope, $http, $resource, $routeParams){

    console.log("welcome to register-ctrl")

    $scope.onComplete = function(){
        var formError = validation.validRegForm(this.$data)
        if(formError.length === 0){
            $http.post("/registration/save", this.$data)
            this.$error = ""
        }else{
            this.$error = formError
        }
    }
}]);