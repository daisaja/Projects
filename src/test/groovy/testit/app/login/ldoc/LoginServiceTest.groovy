package testit.app.login.ldoc

import de.deutschepost.ldoc.annotation.ConfluencePath
import de.deutschepost.ldoc.annotation.HeaderName
import de.deutschepost.ldoc.annotation.JiraId
import de.deutschepost.ldoc.annotation.Narrative
import spock.lang.Specification
import testit.app.login.LoginService


@JiraId("LDT-21")
@ConfluencePath("LDT/Confluence Importer Tests")
@HeaderName("Test eines LogIn Dienstes")

@Narrative(role ="Als Betreiber eines Login Dienstes",
           action=" möchte ich sicherstellen dass man die LogIn Funktionalität,",
           expect=" um mich nur mit validen Anmeldedaten anmelden zu können ")


class LoginServiceTest extends Specification {

    static final VALID_USERNAME = 'hans'
    static final VALID_PASSWORD = 'wurst'
    static final INVALID_PASSWORD = 'unknown'
    static final INVALID_USERNAME = 'unknown'
    static final IS_AUTHENTICATED = true
    static final IS_REJECTED = false

    LoginService service = new LoginService()
    def isAuthenticated

    void 'Einloggen eines Nutzers mit validem Nutzernamen und Passwort'() {
        when: 'sich der Benutzer mit validen Anmeldedaten anmelden will'
        userTriesToLogInWith VALID_USERNAME, VALID_PASSWORD
        then: 'wird er ordnungsgemäß im System angemeldet'
        userIs IS_AUTHENTICATED
    }

    void 'Verweigere LogIn bei ungültigem Passwort' () {
        when: 'sich der Benutzer mit einem validen Nutzer , aber mit falschem Passwort anmelden will'
        userTriesToLogInWith VALID_USERNAME, INVALID_PASSWORD
        then: 'wird er im System nicht angemeldet'
        userIs IS_REJECTED
    }

    void 'Verweigere LogIn bei ungültigem Benutzernamen' () {
        when: 'sich der Benutzer mit einem invaliden Nutzer anmelden will'
        userTriesToLogInWith INVALID_USERNAME, VALID_PASSWORD
        then: 'wird er im System nicht angemeldet'
        userIs IS_REJECTED
    }

    // when
    private void userTriesToLogInWith(username, password) {
        isAuthenticated = service.login(username, password)
    }

    // then
    private void userIs(expectation) {
        assert isAuthenticated == expectation
    }
}

