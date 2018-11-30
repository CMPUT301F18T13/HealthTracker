<<<<<<< HEAD
package com.example.healthtracker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private User u;
    private String phone;
    private String email;
    private String userID;
    private String password;


    @Before
    public void setup() {
        phone = "7801234567";
        email = "abc@gmail.com";
        userID = "abc";
        password = "abcd";

        u = new User(phone, email, userID, password);
    }

    @Test
    public void getUserID() {
        assertEquals(u.getUserID(), userID);
    }

    @Test
    public void getEmail() {
        assertEquals(u.getEmail(), email);
    }

    @Test
    public void getPhone() {
        assertEquals(u.getPhone(), phone);
    }

    @Test
    public void setUserID() {
        u.setUserID("def");
        assertEquals(u.getUserID(), "def");
    }

    @Test
    public void setEmail() {
        u.setEmail("def@gmail.com");
        assertEquals(u.getEmail(), "def@gmail.com");
    }

    @Test
    public void setPhone() {
        u.setPhone("7804567890");
        assertEquals(u.getPhone(), "7804567890");
    }

    @Test
    public void updateUserInfo() {
        u.updateUserInfo("7804567890", "def@gmail.com");

        assertEquals(u.getPhone(), "7804567890");
        assertEquals(u.getEmail(), "def@gmail.com");
    }

    @Test
    public void search() {
        //TODO
    }

    @Test
    public void createMap() {
        //TODO
    }
}
=======
package com.example.healthtracker;

import com.example.healthtracker.EntityObjects.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private User u;
    private String phone;
    private String email;
    private String userID;
    private String password;


    @Before
    public void setup() {
        phone = "7801234567";
        email = "abc@gmail.com";
        userID = "abc";
        password = "abcd";

        u = new User(phone, email, userID);
    }

    @Test
    public void getUserID() {
        assertEquals(u.getUserID(), userID);
    }

    @Test
    public void getEmail() {
        assertEquals(u.getEmail(), email);
    }

    @Test
    public void getPhone() {
        assertEquals(u.getPhone(), phone);
    }

    @Test
    public void setUserID() {
        u.setUserID("def");
        assertEquals(u.getUserID(), "def");
    }

    @Test
    public void setEmail() {
        u.setEmail("def@gmail.com");
        assertEquals(u.getEmail(), "def@gmail.com");
    }

    @Test
    public void setPhone() {
        u.setPhone("7804567890");
        assertEquals(u.getPhone(), "7804567890");
    }

    @Test
    public void updateUserInfo() {
        u.updateUserInfo("7804567890", "def@gmail.com");

        assertEquals(u.getPhone(), "7804567890");
        assertEquals(u.getEmail(), "def@gmail.com");
    }
}
>>>>>>> tyler
