var app = angular.module("et", ["ngResource", "ngRoute"]);

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