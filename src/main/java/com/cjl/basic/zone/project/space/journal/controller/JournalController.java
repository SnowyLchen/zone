package com.cjl.basic.zone.project.space.journal.controller;

import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.project.manage.user.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/journal")
public class JournalController extends BaseController {

    /**
     * 日志
     */
    @GetMapping()
    public String index(ModelMap mmap, HttpServletResponse response) {
        User user = ShiroAuthenticateUtils.getUserByToken();
        return "space/journal";
    }

}
