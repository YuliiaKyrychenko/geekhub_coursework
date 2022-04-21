package api.console.controllers;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@ComponentScan(basePackages = {"com.geekhub.config"})
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(
            path = {"", "/"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.ALL_VALUE
    )

    @PostMapping
    public String login(@PathVariable String username, @PathVariable String password) {
        return "login";
    }
}
