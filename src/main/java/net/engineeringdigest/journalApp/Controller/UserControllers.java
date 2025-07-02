package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.Service.WeatherService;
import net.engineeringdigest.journalApp.weather.Weather;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControllers {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;


//    @GetMapping("username/{username}")
//    public User getByUsername(@PathVariable String username){
//        return userService.getByUsername(username);
//    }

//    @GetMapping("id/{id}")
//    public User findById(@PathVariable ObjectId id) {
//        User user = userService.getById(id);
//        if (user != null) {
//            return user;
//        } else {
//            return null;
//        }
//    }


    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDB = userService.getByUsername(username);
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userService.saveEntry(userInDB);
            return new ResponseEntity<>(userInDB, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<User> delete(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(userService.delete(username), HttpStatus.OK);

    }

    @GetMapping
    public String greetings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        //Give me the body of the weather pojo, that is converted from the json response that we got from the api call

        Weather weather = weatherService.getWeather();
        // now we have tp extract the temparature of some fields from the body of the pojo
       int temp = weather.getCurrent().getFeelslike();

        String message = "Hi "+ username + "today's weather is like : " + temp;
        return message;
    }

    @GetMapping("/user-analysis")
    public List<User> getSAUsers(){
        List<User> all = userRepositoryImpl.getUserWithEmailAndSA();
        return all;
    }
}
