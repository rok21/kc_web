module kc_web {
    export function headerDir($rootScope: IRootScope, $http : ng.IHttpService, $location: ng.ILocationService, sessionService: SessionService): ng.IDirective {
        return{
            restrict: 'A',
            replace: true,
            templateUrl: "/assets/html/header.html",
            link: ($scope: ng.IScope, element: JQuery, attributes: any) => {
                
                sessionService.checkCookie()

                //update $scope whenever $rootScope changes
                //$rootScope.user is also used in LoginCtrl
                $rootScope.$watch(
                    rscope => rscope.user,
                    (newVal, oldVal) => $scope.user = newVal
                );
                
                $scope.onLogout = () => {
                    $http.post("/login/logout", null)
                    .then((resp) => {
                        $rootScope.user = null
                        $location.path('/login')
                    })
                }
            }
        }
    }

    headerDir.$inject = ['$rootScope', '$http', '$location', 'sessionService'];
}