/// <reference path='_all.ts' />

/**
 *  @type {angular.Module}
 */
module kc_web {
    
    var app = angular.module('kc_web', ["ngRoute" /*, "kc_web.validation"*/ ]);

    app.config((
        $routeProvider: angular.route.IRouteProvider, 
        $locationProvider: angular.ILocationProvider, 
        $provide, 
        $httpProvider: angular.IHttpProvider) => {
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
                controller: LoginCtrl
            })
            // .when("/register", {
            //     templateUrl: "/assets/html/register.html",
            //     controller: "RegisterCtrl"
            // })
            .when("/home", {
                templateUrl: "/assets/html/home.html",
                controller: HomeCtrl
            })
            // .when("/cityselect", {
            //     templateUrl: "/assets/html/cityselect.html",
            //     controller: "CitySelectCtrl"
            // })
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

    app.directive('headerDir', headerDir)

    console.log("hello from typescript")
}