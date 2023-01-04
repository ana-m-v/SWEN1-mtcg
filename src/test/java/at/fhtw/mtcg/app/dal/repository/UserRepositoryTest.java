package at.fhtw.mtcg.app.dal.repository;

import at.fhtw.mtcg.app.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    @Test
    void testRegisterUserGetUser() throws Exception {
        User user = new User();
        user.setUsername("mila");
        user.setPassword("567");

        UserRepository userRepository = new UserRepository();
        userRepository.registerUser(user);
        String username = userRepository.getUserByUsername("mila").getUsername();
        assertEquals("mila", username);
    }

    @Test
    void testRegisterUserDefaults() throws Exception {
        User user = new User();
        user.setUsername("cody");
        user.setPassword("123");

        UserRepository userRepository = new UserRepository();
        userRepository.registerUser(user);
        assertEquals(20, userRepository.getUserByUsername("cody").getCoins());
    }

    @Test
    void testLoginUserCorrectPassword() throws Exception {
        User user = new User();
        user.setUsername("cody");
        user.setPassword("123");

        UserRepository userRepository = new UserRepository();
        userRepository.loginUser(user.getUsername(), user.getPassword());
        assertTrue(userRepository.isLoggedIn(user.getUsername(), user.getPassword()));
    }

    @Test
    void testLoginUserIncorrectPassword() throws Exception {
        User user = new User();
        user.setUsername("jonah");
        user.setPassword("123");

        UserRepository userRepository = new UserRepository();
        assertFalse(userRepository.checkCredentials(user.getUsername(), "789"));
    }

    @Test
    void testUpdateStats() throws Exception {
        User gimli = new User();
        gimli.setUsername("Gimli");
        gimli.setPassword("123");

        User legolas = new User();
        legolas.setUsername("Legolas");
        legolas.setPassword("123");

        UserRepository userRepository = new UserRepository();
        userRepository.registerUser(gimli);
        userRepository.registerUser(legolas);


        userRepository.updateStats("gimli", true);
        userRepository.updateStats("legolas", false);

        // get wins and losses

        userRepository.getStats("gimli");
    }
}