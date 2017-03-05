/// <reference path='../_all.ts' />

module kc_web {
    export class LoginCtrl {
        public static $inject = [
			'$scope',
            '$http',
            'sessionService'
		];

        constructor(
            private $scope: ILoginScope,
            private $http: ng.IHttpService, 
            private sessionService: SessionService){
                this.sessionService.checkCookieAndRedirect()
                $scope.vm = this;
        }
        
        onComplete(){
            this.$http.post("/login", this.$scope.credentials)
            .then((response: ng.IHttpPromiseCallbackArg<string>) =>
                response.data ? this.$scope.error = response.data : this.sessionService.checkCookieAndRedirect()
            );
        }
    }
}