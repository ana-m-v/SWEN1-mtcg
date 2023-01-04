package at.fhtw.mtcg.app.service.session;

import at.fhtw.mtcg.app.controller.Controller;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.app.model.User;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import com.fasterxml.jackson.core.JsonProcessingException;

public class SessionController extends Controller {

    private UserRepository userRepository;

    public SessionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response getUserByUsername(String username)
    {
        try {
            User userData = this.userRepository.getUserById(Integer.valueOf(username));
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

    public Response loginUser(Request request) {
        try {
            User userData = this.getObjectMapper().readValue(request.getBody(), User.class);
            if(this.userRepository.isLoggedIn(userData.getUsername(), userData.getPassword())) {
                return new Response(
                        HttpStatus.CONFLICT,
                        ContentType.JSON,
                        "{ message: \"User already logged in\" }"
                );
            }
            else if(!this.userRepository.isLoggedIn(userData.getUsername(), userData.getPassword())){
                if(this.userRepository.userExists(userData.getUsername())) {
                    if (this.userRepository.checkCredentials(userData.getUsername(), userData.getPassword())) {
                        this.userRepository.loginUser(userData.getUsername(), userData.getPassword());
                        return new Response(
                                HttpStatus.OK,
                                ContentType.JSON,
                                "{ message: \"User login successful\" }"
                        );
                    } else {
                        return new Response(
                                HttpStatus.CONFLICT,
                                ContentType.JSON,
                                "{ message: \"Invalid password\" }"
                        );
                    }
                }
            }
            else {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
//                        token
                        "{ message: \"User does not exist\" }"
                );
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ContentType.JSON,
                "{ \"message\" : \"Internal Server Error\" }"
        );
    }
}
