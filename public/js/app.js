/// <reference path='../_all.ts' />
var kc_web;
(function (kc_web) {
    var LoginCtrl = (function () {
        function LoginCtrl($scope, $rootScope, $http, $location) {
            this.$scope = $scope;
            this.$rootScope = $rootScope;
            this.$http = $http;
            this.$location = $location;
            this.checkCurrentUser();
        }
        LoginCtrl.prototype.checkCurrentUser = function () {
            var _this = this;
            this.$http.get("/login/current").then(function (response) {
                if (response) {
                    _this.onLoggedIn(response);
                }
            });
        };
        LoginCtrl.prototype.onLoggedIn = function (username) {
            this.$rootScope.user = username;
            console.log(this.$location);
            this.$location.path('/home');
        };
        return LoginCtrl;
    }());
    LoginCtrl.$inject = [
        '$scope',
        '$rootScope',
        '$http',
        '$location',
        '$routeParams'
    ];
    kc_web.LoginCtrl = LoginCtrl;
})(kc_web || (kc_web = {}));
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
var kc_web;
(function (kc_web) {
    var HomeCtrl = (function () {
        function HomeCtrl() {
        }
        return HomeCtrl;
    }());
    kc_web.HomeCtrl = HomeCtrl;
})(kc_web || (kc_web = {}));
// app.controller('HomeCtrl', function($scope, $http, $resource, $routeParams, $location){
//     $http.get("/users/user").success(function (data, status, headers, config){
//         $scope.userInfo = data
//         if(!$scope.userInfo.city){
//             $location.path('/cityselect')
//         }
//     })
//     $scope.timers = []
//     // findTimer = function(key){ return _.find($scope.timers, function(e){ return e.key === key }) }
//     // var ws = new WebSocket($scope.homewsURL)
//     // ws.onmessage = function(event){
//     //                 if(event.data.startsWith("time")){
//     //                     var key = event.data.split(':')[0]
//     //                     var target = findTimer(key)
//     //                     if(!target){
//     //                         target = {key: key, data: ''}
//     //                         $scope.timers.push(target)
//     //                     }
//     //                     target.data = event.data
//     //                 }else{
//     //                     $scope.serverMsg = event.data
//     //                 }
//     //                 $scope.$apply();
//     //         }
// });
// app.directive('timer', function () {
//         return {
//             restrict: 'A',
//             replace: true,
//             templateUrl: "/assets/html/timer.html",
//             controller: function($scope, $http, $resource, $routeParams, $location){
//             }
//         }
//     }); 
var kc_web;
(function (kc_web) {
    function headerDir() {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: "/assets/html/header.html",
            link: function ($rootScope, $scope, $http, $location) {
                console.log("header");
            }
        };
    }
    kc_web.headerDir = headerDir;
})(kc_web || (kc_web = {}));
// app.controller('HeaderCtrl', function($rootScope, $scope, $http, $resource, $routeParams, $location){
//     $scope.$user = $rootScope.user
//     $http.get("/login/current")
//          .success(function(response){
//             if(response){
//                 $rootScope.user = response
//             }
//         })
//     $scope.$watch(
//         function(scope){ return scope.user },
//         function(newVal, oldVal){
//             $scope.$user = $rootScope.user
//             console.log("user="+newVal)
//         }
//     )
//     $scope.onLogout = function(){
//         console.log("goodbye..")
//         $http.post("/login/logout")
//             .success(function(response){
//                 $rootScope.user = null
//                 $location.path('/login')
//             })
//     }
// }); 
/// <reference path="node_modules/@types/jquery/index.d.ts" />
/// <reference path="node_modules/@types/angular/index.d.ts" />
/// <reference path="node_modules/@types/angular-route/index.d.ts" />
/// <reference path="ctrls/LoginCtrl.ts" />
/// <reference path="ctrls/HomeCtrl.ts" />
/// <reference path="interfaces/IRootScope.ts" />
/// <reference path="interfaces/ILoginScope.ts" />
/// <reference path="directives/HeaderDir.ts" />
/// <reference path='App.ts' />
/// <reference path='_all.ts' />
/**
 *  @type {angular.Module}
 */
var kc_web;
(function (kc_web) {
    var app = angular.module('kc_web', ["ngRoute" /*, "kc_web.validation"*/]);
    app.config(function ($routeProvider, $locationProvider, $provide, $httpProvider) {
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        });
        $routeProvider
            .when("/", {
            redirectTo: "/home"
        })
            .when("/login", {
            templateUrl: "/assets/html/login.html",
            controller: kc_web.LoginCtrl
        })
            .when("/home", {
            templateUrl: "/assets/html/home.html",
            controller: kc_web.HomeCtrl
        })
            .otherwise({
            redirectTo: "/home"
        });
        //     $provide.factory('ErrorInterceptor', function ($q, $location) {
        //     return {
        //         responseError: function(rejection) {
        //             if(rejection.status===401){
        //                 console.log("redirecting to login..")
        //                 $location.path('/login')
        //             }
        //             return $q.reject(rejection);
        //         }
        //     };
        // });
        // $httpProvider.interceptors.push('ErrorInterceptor');
    });
    app.directive('headerDir', kc_web.headerDir);
    console.log("hello from typescript");
})(kc_web || (kc_web = {}));
