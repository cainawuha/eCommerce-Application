package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static junit.framework.TestCase.assertEquals;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.mock;


public class UserControllerTest {
    @Autowired
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
private UserController userController;
private UserRepository userRepository = mock(UserRepository.class);
private CartRepository cartRepository = mock(CartRepository.class);
private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

@Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
     userController = new UserController();
    TestUtils.injectObjects(userController,"userRepository",userRepository);
    TestUtils.injectObjects(userController,"cartRepository",cartRepository);
    TestUtils.injectObjects(userController,"bCryptPasswordEncoder",encoder);

    User user = new User();
    Cart cart = new Cart();
    user.setId(0);
    user.setUsername("test");
    user.setPassword("testPassword");
    user.setCart(cart);
}
@Test
public void create_user_happy_path(){
    CreateUserRequest r = new CreateUserRequest();
    r.setUsername("weiwei");
    r.setPassword("1234567");
    r.setConfirmPassword("1234567");

    ResponseEntity<User> response = userController.createUser(r);
    assertNotNull(response);
    assertEquals(200,response.getStatusCodeValue());

    User user =response.getBody();
    assertNotNull(user);
    assertEquals(0,user.getId());
    assertEquals("weiwei",user.getUsername());
    assertEquals("1234567",user.getPassword());
}

    @Test
    public void find_user_by_name_happy_path() {
        final ResponseEntity<User> response = userController.findByUserName("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals("test", u.getUsername());
    }

    @Test
    public void find_user_by_id_happy_path() {
        final ResponseEntity<User> response = userController.findById(0L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());;
    }

}
