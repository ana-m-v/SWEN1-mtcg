package at.fhtw.mtcg.app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testUserNewUser() throws Exception {
        User user = new User();
        user.setUsername("cody");
        user.setPassword("123");
        assertEquals("cody", user.getUsername());
        assertEquals("123", user.getPassword());
    }
}