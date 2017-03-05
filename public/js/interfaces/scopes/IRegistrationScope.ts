module kc_web{

    export interface IRegistrationData extends ng.IScope {
        email: string;
        username: string;
        password: string;
        passwordRepeated: string;
    }

    export interface IRegistrationScope extends ng.IScope {
        data: IRegistrationData;
        error: string;
        vm: RegisterCtrl;
    }
}