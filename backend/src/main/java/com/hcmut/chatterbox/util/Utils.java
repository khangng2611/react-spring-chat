package com.hcmut.chatterbox.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

public class Utils {
    public static String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
