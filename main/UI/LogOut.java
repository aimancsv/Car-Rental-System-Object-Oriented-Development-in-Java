package main.UI;

import main.session.SessionContainer;

public interface LogOut extends Back {
    SessionContainer getSessionContainer();

    default void logOut() {
        getSessionContainer().setCurrentUser(null);
        backToPreviousPage();
    }
}
