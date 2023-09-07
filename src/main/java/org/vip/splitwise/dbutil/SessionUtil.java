package org.vip.splitwise.dbutil;

import org.vip.splitwise.models.User;

public class SessionUtil {
    private User user;

    public void signInUser(User user) {
        this.user = user;
    }

    public boolean isUserSignedIn() {
        return user != null;
    }

    public void logoutUser() {
        user = null;
    }
}
