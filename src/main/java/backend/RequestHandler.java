package backend;

import backend.data.DbService;

import backend.data.LoginDetails;
import backend.data.User;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import javax.annotation.Resource;


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

    /**
     * Processes a friend request.
     * @param yourEmail - Username of the person requesting another to befriend him.
     * @param friendEmail - Username of the person he/she wants to befriend.
     * @return - OK if successful.
     */
    @RequestMapping("/addfriend")
    public String friendRequest(@RequestParam String yourEmail,
                                @RequestParam String friendEmail) {
        User thatUser = dbService.getUser(friendEmail);

        if (dbService.getUser(friendEmail) == null) {
            return "Not a valid username";
        } else {
            thatUser.newFriendRequest(yourEmail);
            dbService.addUser(thatUser);
            return "OK";
        }

    }

    /**
     * Returns all friend requests for a certain User.
     * @param yourEmail - Username of the User requesting all their friend requests.
     * @return - all the friend requests of this user.
     */
    @RequestMapping("/getfriendreq")
    public ArrayList<String> getAllFriendRequests(@RequestParam String yourEmail) {
        User thisUser = dbService.getUser(yourEmail);
        return thisUser.getFriendRequests();

    }

    /**
     * Accept a friend request and add that person to each others friend list.
     * @param yourEmail - Username of person who wants to accept the request.
     * @param friendEmail - Username of the person User wants to accept.
     * @return - OK when done.
     */
    @RequestMapping("/acceptfriendreq")
    public String acceptFriend(@RequestParam String yourEmail,
                               @RequestParam String friendEmail) {
        User thisUser = dbService.getUser(yourEmail);
        User friendUser = dbService.getUser(friendEmail);
        thisUser.addFriend(friendEmail);
        friendUser.addFriend(yourEmail);
        thisUser.deleteFriendRequest(friendEmail);
        dbService.addUser(thisUser);
        dbService.addUser(friendUser);
        return "OK";
    }



}




