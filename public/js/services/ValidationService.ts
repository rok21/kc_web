 module kc_web {
     export class ValidationService {
         validRegForm(form) : string {
            if(!this.validMail(form.email)){
                return "Invalid email!"
            }
            if(!form.password || form.password.length < 5){
                return "At least 5 characters for password please."
            }
            if(!form.username || form.username.length < 5){
                return "At least 5 characters for username please."
            }
            if(form.password!==form.passwordRepeated){
                return "Passwords don't match!"
            }
            return ""
       }

       validMail(email) : boolean {
         var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
         return re.test(email);
       }
     }
 }