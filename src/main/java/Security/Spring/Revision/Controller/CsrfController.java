package Security.Spring.Revision.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {
        @GetMapping("/csrf-token")
        public CsrfToken csrfToken(CsrfToken token) {
            return token;
        }
    }

