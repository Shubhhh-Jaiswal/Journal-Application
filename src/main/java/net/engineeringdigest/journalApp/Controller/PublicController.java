package net.engineeringdigest.journalApp.Controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.Utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtility jwtutil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/health-check")
    public String health(){
        return "OK";
    }

//    @PostMapping("/user-create")
//    public ResponseEntity<User> createUser(@RequestBody User user){
////        User userInDb = userService.getByUsername(user.getUserName());
//            return new ResponseEntity<>(userService.saveEntry(user), HttpStatus.ACCEPTED);
//
////            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//    }

    @PostMapping("/signUp")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User userInDb = userService.getByUsername(user.getUserName());
        if(userInDb == null){
            return new ResponseEntity<>(userService.saveEntry(user), HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtutil.getToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
            log.error("Exception occured", e);
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }

    }
}
