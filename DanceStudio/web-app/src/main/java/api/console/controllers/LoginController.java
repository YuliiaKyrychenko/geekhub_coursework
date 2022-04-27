package api.console.controllers;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@ComponentScan(basePackages = {"com.geekhub.config"})
@RestController
public class LoginController {

    @RequestMapping(
            path = {"", "/"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.ALL_VALUE
    )

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ModelAttribute
    @GetMapping("/login-successful")
    public String loginSuccessful() {
        return "login";
    }
}
