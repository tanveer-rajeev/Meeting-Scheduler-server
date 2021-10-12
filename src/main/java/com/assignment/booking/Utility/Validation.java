package com.assignment.booking.Utility;

public class Validation {
    public static boolean checkPasswordValidation(String password) {
        if (password.length() < 6) return false;

        boolean checkContainWithSymbol= false;

        for (int i = 0; i < password.length(); i++) {
            if (!(password.charAt(i) >= 48 && password.charAt(i) <= 57) && !(password.charAt(i) >= 65 && password.charAt(
                    i) <= 90) && !(password.charAt(i) >= 97 && password.charAt(i) <= 122)) {

                checkContainWithSymbol=true;
                break;
            }
        }

        return checkContainWithSymbol;
    }
}
