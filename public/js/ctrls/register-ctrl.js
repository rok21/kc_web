app.controller('RegisterCtrl', function(validation, $scope, $http, $resource, $routeParams){

    $scope.onComplete = function(){
        var formError = validation.validRegForm(this.$data)
        if(formError.length === 0){
            $http.post("/registration/save", this.$data)
            this.$error = ""
        }else{
            this.$error = formError
        }
    }
});