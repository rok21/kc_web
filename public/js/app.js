var app = angular.module("kc_web", ["ngResource", "ngRoute", "kc_web.validation"]);

app.config(function($routeProvider, $locationProvider) {
	$routeProvider
		.when("/", {
			redirectTo: "/home"
		})
		.when("/login", {
            templateUrl: "/assets/html/login.html"
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
			redirectTo: "/register"
		});
	$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});
});