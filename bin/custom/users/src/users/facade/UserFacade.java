package users.facade;

import users.data.UserData;

public interface UserFacade {
    UserData get(String id);
}
