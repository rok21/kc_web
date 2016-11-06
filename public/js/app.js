var app = angular.module("kc_web", ["ngResource", "ngRoute", "kc_web.validation"]);

app.config(function($routeProvider, $locationProvider) {
	$routeProvider
		.when("/", {
			redirectTo: "/register"
		})
		.when("/register", {
			templateUrl: "/assets/html/register.html",
			controller: "RegisterCtrl"
		})
		.otherwise({
			redirectTo: "/register"
		});
	$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});
});