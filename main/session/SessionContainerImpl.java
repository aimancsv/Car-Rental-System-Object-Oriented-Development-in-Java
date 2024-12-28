package main.session;

import main.model.account.Account;

public class SessionContainerImpl implements SessionContainer {
    private Session currentSession = new Session();

    @Override
    public Session getSession() {
        return currentSession;
    }

    @Override
    public void setCurrentUser(Account account) {
        currentSession.setCurrentUser(account);
    }
}
