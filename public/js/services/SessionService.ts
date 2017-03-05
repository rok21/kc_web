module kc_web {
     export class SessionService {

         public static $inject = [
            '$rootScope',
            '$http', 
            '$location'
		];

        constructor(
            private $rootScope: IRootScope, 
            private $http: ng.IHttpService,
            private $location: ng.ILocationService){            
        }

         private checkCookieAnd(and: () => void){
             this.$http.get("/login/current").then((response: ng.IHttpPromiseCallbackArg<string>) => {
                if(response.data){ 
                    this.$rootScope.user = response.data;
                    and();
                }
            })
         }
         
         checkCookieAndRedirect() {
             this.checkCookieAnd(() => this.$location.path("/home"))
         }

         checkCookie(){
             this.checkCookieAnd(() => {})
         }
     }
}