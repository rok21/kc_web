var app = angular.module("kc_web", ["ngResource", "ngRoute", "kc_web.validation"]);

app.config(function($routeProvider, $locationProvider, $provide, $httpProvider) {
	$routeProvider
		.when("/", {
			redirectTo: "/home"
		})
		.when("/login", {
            templateUrl: "/assets/html/login.html",
            controller: "LoginCtrl"
        })
		.when("/register", {
			templateUrl: "/assets/html/register.html",
			controller: "RegisterCtrl"
		})
		.when("/home", {
            templateUrl: "/assets/html/home.html",
            controller: "HomeCtrl"
        })
		.otherwise({
			redirectTo: "/login"
		});
	$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});

    $provide.factory('ErrorInterceptor', function ($q, $location) {
            return {
                responseError: function(rejection) {
                    if(rejection.status===401){
                        console.log("redirecting to login..")
                        $location.path('/login')
                    }
                    return $q.reject(rejection);
                }
            };
        });

    $httpProvider.interceptors.push('ErrorInterceptor');
});