package backend;

import backend.data.DbService;

import backend.data.LoginDetails;
import backend.data.User;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RequestHandler {
    @Resource(name = "DbService")
    private DbService dbService;

    /*    @RequestMapping("/greeting")
        public String respond() {
            return "TestGreeting";
        }*/

    /**
     * .
     * Login REST Method
     */
    @RequestMapping("/login")
    public User loginController(@RequestBody LoginDetails loginDetails) {

        if (dbService.grantAccess(loginDetails.getIdentifier(), loginDetails.getPassword())) {
            return dbService.getUser(loginDetails.getIdentifier());
        }

        return null;
    }

    /**
     * .
     * Sign-up REST Method
     */
    @RequestMapping("/signup")
    public String signupController(@RequestBody User user) {
        System.out.println(user);
        if (dbService.getUser(user.getUsername()) != null) {
            return "username exists";
        }
        if (dbService.getUser(user.getEmail()) != null) {
            return "email exists";
        }

        dbService.addUser(user);
        return "success";
        //return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @RequestMapping("/getUser")
    public User getUser(@RequestBody String identifier) {
        return dbService.getUser(identifier);
    }

    @RequestMapping("/friendrequest")
    public String friendRequest(@RequestParam String sender, @RequestParam String receiver) {
        dbService.addFriendRequest(sender, receiver);
        return "OK";
    }

    @RequestMapping("/acceptfriend")
    public String acceptFriendRequest(@RequestParam String sender, @RequestParam String receiver) {
        dbService.acceptFriendRequest(sender, receiver);
        return "OK";
    }

    @RequestMapping("/rejectfriend")
    public String rejectFriendRequest(@RequestParam String sender, @RequestParam String receiver) {
        dbService.rejectFriendRequest(sender, receiver);
        return "OK";
    }

    @RequestMapping("/getFriendRequests")
    public List<String> getAllFriendRequests(@RequestParam String email) {
        return dbService.getUser(email).getFriendRequests();
    }

}




