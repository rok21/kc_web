module kc_web{

    export interface ILoginCredentials{
        username: string;
        password: string;
    }

    export interface ILoginScope extends ng.IScope {
        credentials: ILoginCredentials;
        error: string;
        vm: LoginCtrl
    }
}