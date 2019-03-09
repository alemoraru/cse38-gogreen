package frontend;

import backend.data.Activity;
import backend.data.EatVegetarianMeal;
import backend.data.LoginDetails;
import backend.data.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;

public class Requests {

    /**
     * Sends signup request to the server.
     * @param user - user signing up
     * @return response from the server.
     */
    public static String signupRequest(User user) {
        String url = "http://localhost:8080/signup";
        RestTemplate rest = new RestTemplate();
        return rest.postForEntity(url,user,String.class).getBody();
    }

    /**
     * Sends login request to the server.
     * @param loginDetails - login details of user wanting to log in
     * @return response from server
     */
    public static User loginRequest(LoginDetails loginDetails) {
        String url = "http://localhost:8080/login";
        RestTemplate rest = new RestTemplate();
        return rest.postForEntity(url, loginDetails, User.class).getBody();
    }

    /**
     * Get User identified by username or email from server.
     * @param identifier - username or email
     * @return - the User of the identifier (if any)
     */
    public static User getUserRequest(String identifier) {
        String url = "http://localhost:8080/getUser";
        RestTemplate rest = new RestTemplate();
        return rest.postForEntity(url, identifier, User.class).getBody();
    }

    /**
     * Sends a friend request from one user to another.
     * @param sender - user sending the friend request
     * @param receiver - user receiving the friend request
     * @return
     */
    public static User sendFriendRequest(String sender, String receiver) {

        String url = "http://localhost:8080/friendrequest";

        RestTemplate restTemplate = new RestTemplate();

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sender", sender)
                .queryParam("receiver", receiver);

        return restTemplate.getForEntity(uriBuilder.toUriString(), User.class).getBody();
    }

    /**
     * Request from a user to accept a friend request.
     * @param sender - user who sent the friend request
     * @param accepting - user who accepts the request
     * @return
     */
    public static User acceptFriendRequest(String sender, String accepting) {

        String url = "http://localhost:8080/acceptfriend";

        RestTemplate restTemplate = new RestTemplate();

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sender", sender)
                .queryParam("accepting", accepting);

        return restTemplate.getForEntity(uriBuilder.toUriString(), User.class).getBody();
    }

    /**
     * Request from a user to reject a friend request.
     * @param sender - user who sent the request
     * @param rejecting - user who is rejecting the request
     * @return
     */
    public static User rejectFriendRequest(String sender, String rejecting) {

        String url = "http://localhost:8080/rejectfriend";

        RestTemplate restTemplate = new RestTemplate();

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sender", sender)
                .queryParam("rejecting", rejecting);

        return restTemplate.getForEntity(uriBuilder.toUriString(), User.class).getBody();
    }

    /**
     * Sends request to server/db to check if user is valid.
     * @param identifier - username or email
     * @return - returns true if user is validated, false if not.
     */
    public static boolean validateUserRequest(String identifier) {
        String url = "http://localhost:8080/validateUser";
        RestTemplate rest = new RestTemplate();
        return rest.postForEntity(url, identifier, String.class).getBody().equals("OK");
    }

    public static User addActivityRequest(Activity activity, String identifier) throws JsonProcessingException {

        String url = "http://localhost:8080/addActivity";

        RestTemplate restTemplate = new RestTemplate();


        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("identifier", identifier);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        String jsonDataString = mapper.writeValueAsString(activity);



        return restTemplate.postForEntity(uriBuilder.toUriString(), jsonDataString, User.class).getBody();
    }

    public static void main(String[] args) throws JsonProcessingException {
        EatVegetarianMeal activity = new EatVegetarianMeal();
        Date date = new Date();
        User received = addActivityRequest(activity, "vehjelmtvedt1");
        System.out.println(received.toString());
    }




}
