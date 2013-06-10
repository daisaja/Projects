package testit.app.login.ldoc

import de.deutschepost.ldoc.annotation.ConfluencePath
import de.deutschepost.ldoc.annotation.HeaderName
import de.deutschepost.ldoc.annotation.JiraId
import de.deutschepost.ldoc.annotation.Narrative
import spock.lang.Specification
import testit.app.login.LoginController
import testit.app.login.LoginService
import testit.app.login.UserNotAuthenticatedException


@JiraId("LDT-21")
@ConfluencePath("LDT/Confluence Importer Tests")
@HeaderName("Test eines Web Controllers für LogIn Prozeduren")

@Narrative( role ="Als Entwickler eines Login Dienstes",
            action=" möchte ich sicherstellen dass man die LogIn Funktionalität,",
            expect=" um mich nur mit validen Anmeldedaten anmelden zu können ")


class LoginControllerTest extends Specification {

    static final String VALID_USERNAME = 'valid_username'
    static final String VALID_PASSWORD = 'valid_password'
    static final String INVALID_PASSWORD = 'invalid_password'
    static final String INVALID_USERNAME = 'invalid_username'
    static final boolean USER_AUTHENTICATED = true
    static final boolean USER_NOT_AUTHENTICATED = false

    static final NO_USERNAME = null
    static final NO_PASSWORD = null

    static final String EMPTY_USERNAME = ''
    static final String EMPTY_PASSWORD = ''

    LoginController controller = new LoginController()
    LoginService service = Mock(LoginService)

    void setup() {
        controller.loginService = service
    }

    void 'Login when user tries to login with valid credentials' () {
        given: ''
        loginServiceAllowsAccessFor VALID_USERNAME, VALID_PASSWORD
        expect:''
        userDoesLoginWith VALID_USERNAME, VALID_PASSWORD
    }

    void 'Expect exception when user tries to login with invalid credentials'() {
        given: 'sind ungültige Anmeldedaten (Nutzer, Passwort)'
        loginServiceDeniesAccessFor INVALID_USERNAME, INVALID_PASSWORD
        when: 'man sich mit den ungültigen Anmeldedaten anmelden möchte '
        userDoesLoginWith INVALID_USERNAME, INVALID_PASSWORD
        then: 'bekommt man eine' + 'User not authenitcated!' + 'Fehlermeldung'
        def exception =thrown(UserNotAuthenticatedException)
    }

    void 'Expect IllegalArgumentException when no credentials are given'() {
        when: 'man sich ohne Anmeldedaten anmelden will'
        userDoesLoginWith NO_USERNAME, NO_PASSWORD
        then: 'bekommt man eine unbehandelte IllegalArgumentException'
        def exception = thrown(IllegalArgumentException)
    }

    void 'Expect IllegalArgumentException when empty credentials are given'() {
        when: 'man keine Anmeldedaten eingibt'
        userDoesLoginWith EMPTY_USERNAME, EMPTY_PASSWORD
        then: 'bekommt man eine unbehandelte IllegalArgumentException'
        def exception = thrown(IllegalArgumentException)
    }

    // given
    private void loginServiceDeniesAccessFor(username, password) {
        service.login(username, password) >> USER_NOT_AUTHENTICATED
    }

    private void loginServiceAllowsAccessFor(username, password) {
        service.login(username, password) >> USER_AUTHENTICATED
    }

    // when
    private void userDoesLoginWith(username, password) {
        controller.doLogin(username, password)
    }

}

/**
 * expect: wird verwendet für void Methodenaufruf
 * thrown(): zum abfangen von erwarteten Exception
 * loginServiceDeniesAccessFor und loginServiceAllowsAccessFor UseCase-spezifische given-Methoden
 */
