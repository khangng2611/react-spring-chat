package com.hcmut.chatterbox.util;

import java.util.Random;

public class Utils {
    public static String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
