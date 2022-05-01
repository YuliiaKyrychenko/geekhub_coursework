package api.console.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class AccessCheckController {

    @GetMapping("/name")
    public String showUserName(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }

    @GetMapping("/read-user")
    public UserDetails readUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/delete-user")
    public UserDetails deleteUser(@AuthenticationPrincipal UserDetails userDetails) { return userDetails; }
}
