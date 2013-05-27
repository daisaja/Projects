package testit.app.login

import spock.lang.Specification

class LoginServiceTest extends Specification {
    
    LoginService service = new LoginService()
    
    void 'Login user when username and passowrd are valid'() {
        when: def isAuthenticated = service.login('hans', 'wurst')
        then: assert isAuthenticated == true
    }

}

