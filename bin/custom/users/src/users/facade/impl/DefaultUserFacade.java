package users.facade.impl;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import users.data.UserData;
import users.facade.UserFacade;

@Component("defaultUserFacade")
public class DefaultUserFacade implements UserFacade {
    private final UserService userService;

    @Autowired
    public DefaultUserFacade(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserData get(final String id) {
        final UserModel userModel;
        if (id == null) {
            userModel = userService.getCurrentUser();
        } else {
            userModel = userService.getUserForUID(id);
        }
        return setBriefUserData(userModel);
    }

    private UserData setBriefUserData(final UserModel userModel) {
        final UserData userData = new UserData();
        userData.setId(userModel.getUid());
        userData.setName(userModel.getName());
        userData.setDescription(userModel.getDescription());
        return userData;
    }
}
