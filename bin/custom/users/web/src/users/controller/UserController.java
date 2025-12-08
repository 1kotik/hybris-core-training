package users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import users.data.UserData;
import users.facade.UserFacade;

@Controller
@RequestMapping
public class UserController {
    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping
    public String get(@RequestParam(name = "userId", required = false) final String userId, final Model model) {
        final UserData user = userFacade.get(userId);
        model.addAttribute("user", user);
        return "user";
    }
}
