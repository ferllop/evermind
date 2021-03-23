package com.ferllop.evermind;

import com.ferllop.evermind.repositories.UserRepository;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class PasswordValidatorTest {
    final String TAG = "MYAPP:tet";
    String validPassword;

    @Before
    public void init(){
        validPassword = "aA-45678901234567890123456789012";
    }

    @Test
    public void valid_password_is_between_8_and_32_characters_long(){
        assertFalse(UserRepository.isValidPassword(validPassword.substring(0,7)));
        assertFalse(UserRepository.isValidPassword(validPassword + "a"));
    }

    @Test
    public void invalid_password_do_not_have_at_least_one_lowercase_letter(){
        assertFalse(UserRepository.isValidPassword(validPassword.toUpperCase()));
    }

    @Test
    public void invalid_password_do_not_have_at_least_one_uppercase_letter(){
        assertFalse(UserRepository.isValidPassword(validPassword.toLowerCase()));
    }

    @Test
    public void invalid_password_do_not_have_at_least_one_special_character(){
        assertFalse(UserRepository.isValidPassword(validPassword.replaceAll("-", "a")));
    }



}
