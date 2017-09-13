package me.sjtumeow.meow.authorization.manager.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import me.sjtumeow.meow.authorization.manager.TokenManager;
import me.sjtumeow.meow.authorization.model.TokenModel;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTokenManagerTest {
    @Autowired
    TokenManager tokenManager;

    @Test
    public void checkToken() throws Exception {
        Long userId = 1L;
        TokenModel token = tokenManager.createToken(userId);
        assertTrue(tokenManager.checkToken(token));
        tokenManager.deleteToken(userId);
        assertFalse(tokenManager.checkToken(token));
    }

    @Test
    public void getToken() throws Exception {
        String userId = "123";
        String tokenString = "tokenString";
        String auth = userId + '_' + tokenString;
        TokenModel token = tokenManager.getToken(auth);
        assertEquals(Long.valueOf(userId), Long.valueOf(token.getUserId()));
        assertEquals(tokenString, token.getToken());
    }

}