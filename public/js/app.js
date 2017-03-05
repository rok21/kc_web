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
/// <reference path='../_all.ts' />
var kc_web;
(function (kc_web) {
    var LoginCtrl = (function () {
        function LoginCtrl($scope, $http, sessionService) {
            this.$scope = $scope;
            this.$http = $http;
            this.sessionService = sessionService;
            this.sessionService.checkCookieAndRedirect();
            $scope.vm = this;
        }
        LoginCtrl.prototype.onComplete = function () {
            var _this = this;
            this.$http.post("/login", this.$scope.credentials)
                .then(function (response) {
                return response.data ? _this.$scope.error = response.data : _this.sessionService.checkCookieAndRedirect();
            });
        };
        return LoginCtrl;
    }());
    LoginCtrl.$inject = [
        '$scope',
        '$http',
        'sessionService'
    ];
    kc_web.LoginCtrl = LoginCtrl;
})(kc_web || (kc_web = {}));
var kc_web;
(function (kc_web) {
    var RegisterCtrl = (function () {
        function RegisterCtrl(validationService, sessionService, $scope, $http) {
            this.validationService = validationService;
            this.sessionService = sessionService;
            this.$scope = $scope;
            this.$http = $http;
            $scope.vm = this;
        }
        RegisterCtrl.prototype.onComplete = function () {
            var _this = this;
            var csvError = this.validationService.validRegForm(this.$scope.data);
            if (csvError.length === 0) {
                this.$http.post("/registration/save", this.$scope.data).then(function (response) { return _this.sessionService.checkCookieAndRedirect(); });
            }
            else {
                this.$scope.error = csvError; //client side validation error
            }
        };
        RegisterCtrl.prototype.serverSideValidate = function () {
            var _this = this;
            this.$http.post('/registration/unique', this.$scope.data).then(function (response) {
                _this.$scope.error = response.data;
            });
        };
        return RegisterCtrl;
    }());
    RegisterCtrl.$inject = [
        'validationService',
        'sessionService',
        '$scope',
        '$http'
    ];
    kc_web.RegisterCtrl = RegisterCtrl;
})(kc_web || (kc_web = {}));
var kc_web;
(function (kc_web) {
    function headerDir($rootScope, $http, $location, sessionService) {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: "/assets/html/header.html",
            link: function ($scope, element, attributes) {
                sessionService.checkCookie();
                //update $scope whenever $rootScope changes
                //$rootScope.user is also used in LoginCtrl
                $rootScope.$watch(function (rscope) { return rscope.user; }, function (newVal, oldVal) { return $scope.user = newVal; });
                $scope.onLogout = function () {
                    $http.post("/login/logout", null)
                        .then(function (resp) {
                        $rootScope.user = null;
                        $location.path('/login');
                    });
                };
            }
        };
    }
    kc_web.headerDir = headerDir;
    headerDir.$inject = ['$rootScope', '$http', '$location', 'sessionService'];
})(kc_web || (kc_web = {}));
var kc_web;
(function (kc_web) {
    var ValidationService = (function () {
        function ValidationService() {
        }
        ValidationService.prototype.validRegForm = function (form) {
            if (!this.validMail(form.email)) {
                return "Invalid email!";
            }
            if (!form.password || form.password.length < 5) {
                return "At least 5 characters for password please.";
            }
            if (!form.username || form.username.length < 5) {
                return "At least 5 characters for username please.";
            }
            if (form.password !== form.passwordRepeated) {
                return "Passwords don't match!";
            }
            return "";
        };
        ValidationService.prototype.validMail = function (email) {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(email);
        };
        return ValidationService;
    }());
    kc_web.ValidationService = ValidationService;
})(kc_web || (kc_web = {}));
var kc_web;
(function (kc_web) {
    var SessionService = (function () {
        function SessionService($rootScope, $http, $location) {
            this.$rootScope = $rootScope;
            this.$http = $http;
            this.$location = $location;
        }
        SessionService.prototype.checkCookieAnd = function (and) {
            var _this = this;
            this.$http.get("/login/current").then(function (response) {
                if (response.data) {
                    _this.$rootScope.user = response.data;
                    and();
                }
            });
        };
        SessionService.prototype.checkCookieAndRedirect = function () {
            var _this = this;
            this.checkCookieAnd(function () { return _this.$location.path("/home"); });
        };
        SessionService.prototype.checkCookie = function () {
            this.checkCookieAnd(function () { });
        };
        return SessionService;
    }());
    SessionService.$inject = [
        '$rootScope',
        '$http',
        '$location'
    ];
    kc_web.SessionService = SessionService;
})(kc_web || (kc_web = {}));
/// <reference path="node_modules/@types/jquery/index.d.ts" />
/// <reference path="node_modules/@types/angular/index.d.ts" />
/// <reference path="node_modules/@types/angular-route/index.d.ts" />
/// <reference path="ctrls/HomeCtrl.ts" />
/// <reference path="ctrls/LoginCtrl.ts" />
/// <reference path="ctrls/RegisterCtrl.ts" />
/// <reference path="interfaces/scopes/ILoginScope.ts" />
/// <reference path="interfaces/scopes/IRootScope.ts" />
/// <reference path="interfaces/scopes/IRegistrationScope.ts" />
/// <reference path="directives/HeaderDir.ts" />
/// <reference path="services/ValidationService.ts" />
/// <reference path="services/SessionService.ts" />
/// <reference path='App.ts' />
/// <reference path='_all.ts' />
/**
 *  @type {angular.Module}
 */
var kc_web;
(function (kc_web) {
    var app = angular.module('kc_web', ["ngRoute"]);
    app
        .service('validationService', kc_web.ValidationService)
        .service('sessionService', kc_web.SessionService)
        .directive('headerDir', kc_web.headerDir);
    app.config(function ($routeProvider, $locationProvider, $provide, $httpProvider) {
        $routeProvider
            .when("/", {
            redirectTo: "/home"
        })
            .when("/login", {
            templateUrl: "/assets/html/login.html",
            controller: kc_web.LoginCtrl
        })
            .when("/register", {
            templateUrl: "/assets/html/register.html",
            controller: kc_web.RegisterCtrl
        })
            .when("/home", {
            templateUrl: "/assets/html/home.html",
            controller: kc_web.HomeCtrl
        })
            .otherwise({
            redirectTo: "/home"
        });
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
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
    console.log("hello from typescript");
})(kc_web || (kc_web = {}));
