/// <reference path='../_all.ts' />

module kc_web {
    export class LoginCtrl {
        public static $inject = [
			'$scope',
            '$rootScope',
            '$http', 
            '$location',
            '$routeParams'
		];

        constructor(
            private $scope: ILoginScope,
            private $rootScope: IRootScope, 
            private $http: ng.IHttpService,
            private $location: ng.ILocationService){
                this.checkCurrentUser();
        }
        
        checkCurrentUser(){
            this.$http.get("/login/current").then((response)=> {
                if(response){
                    this.onLoggedIn(response);
            }})
        }
        onLoggedIn(username){
            this.$rootScope.user = username;
            console.log(this.$location)
            this.$location.path('/home');
        }
    }
}

// app.controller('LoginCtrl', function($rootScope, $scope, $http, $resource, $routeParams, $location){

    // checkCurrentUser = function(){
    //     $http.get("/login/current")
    //      .success(function(response){
    //         if(response){
    //             onLoggedIn(response)
    //         }
    //     })
    // }

    // onLoggedIn = function(username){
    //     $rootScope.user = username
    //     $location.path('/home')
    // }

    // $scope.onComplete = function(){
    //     $http.post("/login", this.$data)
    //     .success(function(response){
    //         if(response){
    //             $scope.$error = response
    //         }else{
    //             checkCurrentUser()
    //         }
    //     })
    // }

    // checkCurrentUser()
// });