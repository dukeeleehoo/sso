package cn.future.sso;


import cn.future.modular.unified.service.IApplicationService;
import cn.future.modular.system.dao.UserMapper;
import cn.future.modular.unified.model.Application;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AppUserApi extends BaseController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private IApplicationService iApplicationService;

    /**
     * 根据用户名 应用id 查询用户
     * @param
     * @return
     */
    @RequestMapping("/queryuser")
    @ResponseBody
    public Map queryUser(String account,String appid) throws Exception {
        appid = "44483796-ce5e-4b2b-a237-8f47756a2adc";
        Wrapper<Application> applicationWrapper = new EntityWrapper<>();
        applicationWrapper.eq("appid", appid);
        List<Application> applications = iApplicationService.selectList(applicationWrapper);
        Application application = applications.get(0);
//
//        User user = new User();
//        user.setAccount(RSAUtils.getMingStringPrivate(application, account));
//        User userDB = userMapper.selectOne(user);
//        String sessionId = (String) ShiroKit.getSession().getId();
        Map map = new HashMap<>();
//        map.put("user", userDB);
        return map;
    }

}
