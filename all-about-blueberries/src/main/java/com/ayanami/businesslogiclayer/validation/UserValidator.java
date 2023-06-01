package com.ayanami.businesslogiclayer.validation;

import java.util.regex.*;

/**
 *    Регулярні вирази, які я використовую:
 *
 *     ^[a-zA-Z0-9_-]{3,16}$ - ім'я користувача має містити від 3 до 16 символів і може складатися з латинських букв
 *     (великих або малих), цифр, символів підкреслення (_) та дефісу (-).
 *     ^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$ - електронна пошта повинна мати правильний формат зі знаком '@' та допустимими
 *     символами до та після нього.
 *     ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$ - пароль має містити принаймні одну цифру,
 *     одну малу літеру, одну велику літеру, один спеціальний символ (@#$%^&+=) та не містити пробілів.
 *     Довжина пароля повинна бути не менше 8 символів.
 */

/**
 * User entity validator
 */
public class UserValidator {
    private static final String USERNAME_PATTERN = "^[a-zA-Z]{3,16}$";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public static boolean validateUsername(String username) {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
