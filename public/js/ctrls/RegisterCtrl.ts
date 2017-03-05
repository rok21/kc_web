module kc_web {
    export class RegisterCtrl {
        public static $inject = [
			'validationService',
            'sessionService',
            '$scope',
            '$http'
		];

        constructor(
            private validationService: ValidationService,
            private sessionService: SessionService,
            private $scope: IRegistrationScope,
            private $http: ng.IHttpService){
                $scope.vm = this;
            }

        onComplete(){
            var csvError = this.validationService.validRegForm(this.$scope.data)
            if(csvError.length === 0){
                this.$http.post("/registration/save", this.$scope.data).then((response) => this.sessionService.checkCookieAndRedirect() )
            }else{
                this.$scope.error = csvError //client side validation error
            }
        }

        serverSideValidate(){
            this.$http.post('/registration/unique', this.$scope.data).then((response: ng.IHttpPromiseCallbackArg<string>) => {
                this.$scope.error = response.data
            });
        }
    }
}