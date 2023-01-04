package at.fhtw.mtcg.app.service.user;

import at.fhtw.mtcg.app.controller.Controller;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.app.model.User;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import com.fasterxml.jackson.core.JsonProcessingException;

public class UserController extends Controller {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET /users(:id
    public Response getUserById(String id)
    {
        try {
            User userData = this.userRepository.getUserById(Integer.valueOf(id));
            String userDataJSON = this.getObjectMapper().writeValueAsString(userData);

            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    userDataJSON
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }

    public Response getUserByUsername(String username, String token)
    {
        try {
            User userData = this.userRepository.getUserByUsername(username);
            String userDataJSON = this.getObjectMapper().writeValueAsString(userData);
            String tokenToUsername = this.userRepository.tokenToUsername(token);

            if(username.equals(tokenToUsername)) {
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        userDataJSON
                );
            }
            else {
                return new Response(
                        HttpStatus.CONFLICT,
                        ContentType.JSON,
                        "{ message: \"Conflicting credentials\" }"
                );
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }

    public Response updateUser(String username, Request request, String token) {
        try {
            // request.getBody() => "{ \"id\": 4, \"city\": \"Graz\", ... }
            User userData = this.getObjectMapper().readValue(request.getBody(), User.class);
//            String authorization = request.getHeaderMap().getHeader("Authorization");
            if(this.userRepository.isLoggedInToken(token)) {
                String usernameFromToken = this.userRepository.tokenToUsername(token);

                if(!username.equals(usernameFromToken)) {
                    return new Response(
                            HttpStatus.CONFLICT,
                            ContentType.JSON,
                            "{ message: \"Conflicting user data\" }"
                    );
                }
                this.userRepository.updateUser(userData, username);
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ message: \"User data updated\" }"
                );
            }
            else {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ message: \"User with such credentials is not found\" }"
                );
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }

    public Response registerUser(Request request) {
        try {
            User userData = this.getObjectMapper().readValue(request.getBody(), User.class);
            if(this.userRepository.userExists(userData.getUsername())) {
                return new Response(
                        HttpStatus.CONFLICT,
                        ContentType.JSON,
                        "{ message: \"User exists\" }"
                );
            }
            else {
                this.userRepository.registerUser(userData);
                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        "{ message: \"Success\" }"
                );
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }
}
