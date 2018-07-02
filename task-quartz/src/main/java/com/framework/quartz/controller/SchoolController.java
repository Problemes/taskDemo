package com.framework.quartz.controller;

import com.framework.quartz.entities.Goods;
import com.framework.quartz.entities.School;
import com.framework.quartz.rabbitmq.MessageProducer;
import com.framework.quartz.service.GoodsService;
import com.framework.quartz.service.RoleService;
import com.framework.quartz.service.SchoolService;
import com.framework.quartz.shiro_2.entity.Role;
import com.framework.quartz.shiro_2.entity.User;
import com.framework.quartz.service.UserService;
import com.framework.quartz.shiro_2.entity.UserRoleList;
import io.goeasy.GoEasy;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by HR on 2017/9/19.
 */
@Controller
@RequestMapping("test")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    MessageProducer messageProducer;

    @RequestMapping("/test")
    public String testSchoolController(Model model, Long schId){

        School school = schoolService.testmy(schId);
        model.addAttribute("school",school);


        //MessageProducer messageProducer = new MessageProducer();
        int x = 10;

        while (x > 0){
            messageProducer.sendMessage("Hello, I am amq sender num :" + x--);
            try {
                //暂停一下，好让消息消费者去取消息打印出来
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "success";
    }

    @RequestMapping("/goods")
    public String testGoodsBecauseMultiDS(Model model, Long goodsId, HttpServletRequest request){

        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        String username = principal.getName();

        Goods goods = goodsService.getGoods(goodsId);
        model.addAttribute("goods", goods);
        model.addAttribute("username", username);

        return "success";
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public String testUserCreate(Model model){

        User user = new User();

        user.setUsername("test");
        user.setPassword("testpw");
        user.setSalt("test");

        Long result = userService.createUser(user);
        model.addAttribute("user", result);
        System.out.println("Result:---->>>" + result);

        return "success";
    }

    @RequestMapping("/change")
    public String testUpdateUser(Long userId, String password){

        userService.changePassword(userId, password);

        return "success";
    }

    @Autowired
    private RoleService roleService;

    @RequestMapping("/role")
    public String testCreateRole(Model model, Role role){

        Long roleId = roleService.createRole(role);

        System.out.println("Role: --->>>" + role);

        model.addAttribute("role", role);

        return "success";
    }

    @RequestMapping("/userRole")
    public String testUserRole(Model model, UserRoleList userRoleList){

        System.out.println("UserRole:--->>>" + userRoleList);
        userService.correlationRoles(userRoleList.getUserRoles());

        return "success";
    }

    @RequestMapping("/deleteUR")
    public String testDeleteUserRole(Model model, UserRoleList userRoleList){

        System.out.println("UserRole:--->>>" + userRoleList);
        userService.uncorrelationRoles(userRoleList.getUserRoles());

        return "success";
    }

    @RequestMapping("/findUser")
    public String testFindUser(@RequestParam(required = false) String username, Model model){

        GoEasy goEasy = new GoEasy("BC-d3a3a7f09f3f4aee95fe82934c20bd4c");
        goEasy.publish("channelX", "Hello, World!");
        System.out.println("sending...GoEasy");

        System.out.println("Username:--->>>" + username);
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "success";
    }

    @RequestMapping("/findRoles")
    public String testFindRolesByUsername(@RequestParam(required = false) String username, Model model){

        System.out.println("Username:--->>>" + username);
        Set<String> roles = userService.findRoles(username);
        System.out.println("Roles:---->>>" + roles);
        model.addAttribute("role", roles);
        return "success";
    }

    @RequestMapping("/findPermissions")
    public String testFindPermissionsByUsername(@RequestParam(required = false) String username, Model model){

        System.out.println("Username:--->>>" + username);
        Set<String> permissions = userService.findPermissions(username);
        System.out.println("Permissions:---->>>" + permissions);
        model.addAttribute("permission", permissions);
        return "success";
    }


}
