package org.vip.splitwise.dbutil;

import org.vip.splitwise.models.User;

public class SessionUtil {
    private static User user;

    public static void signInUser(User user) {
        SessionUtil.user = user;
    }

    public static boolean isUserSignedIn() {
        return user != null;
    }

    public static void logoutUser() {
        user = null;
    }
}
