/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.future.modular.app;

import cn.future.config.properties.GunsProperties;
import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.core.log.LogManager;
import cn.future.core.log.factory.LogTaskFactory;
import cn.future.core.util.AppUtil;
import cn.future.modular.system.model.User;
import cn.future.modular.system.service.IUserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;

/**
 * app登录接口控制器
 * @author zp
 */
@RestController
public class AppLoginController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private GunsProperties gunsProperties;


    /**
     * app登录接口，通过账号密码获取token
     */
    @RequestMapping(path="/appLogon")
    public Map<String, Object> appLogin(HttpServletRequest request, HttpServletResponse response) {

        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String equipmenttype = request.getParameter("equipmenttype");
        String guid  = request.getParameter("guid");

        if(ToolUtil.isEmpty(account)||ToolUtil.isEmpty(password)||ToolUtil.isEmpty(equipmenttype)||ToolUtil.isEmpty(guid)){
            return AppUtil.msgMap(BizExceptionEnum.AUTH_REQUEST_ERROR.getCode(), BizExceptionEnum.AUTH_REQUEST_ERROR.getMessage(),null);
        }

        User user = userService.getByAccount(account);
        //根据用户名找到了用户
        if(user != null) {
            String oldPass = user.getPassword();
            //密码比对
            if(!password.equals(oldPass)) {
                return AppUtil.msgMap(BizExceptionEnum.AUTH_REQUEST_ERROR.getCode(), BizExceptionEnum.AUTH_REQUEST_ERROR.getMessage(),null);
            }else {
                String token = UUID.randomUUID().toString().replace("-", "");
                user.setLogintime(new Date());
                //设备类型
                user.setEquipmenttype(equipmenttype);
                //客户端的唯一标识符
                user.setGuid(guid);
                user.setToken(token);
                //token失效时间，按照配置文件＋多少天，定时计算
                user.setDeltokentime(AppUtil.addDay(gunsProperties.getDelTokenDate()));
                user.setMac(AppUtil.getNewMac());
                //修改用户信息
                userService.updateById(user);
                //保存登录日志
                LogManager.me().executeLog(LogTaskFactory.appLoginLog(user.getId(), getIp()));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("token",token );
                map.put("user", user);
                return AppUtil.msgMap(BizExceptionEnum.AUTH_REQUEST_YES.getCode(), BizExceptionEnum.AUTH_REQUEST_YES.getMessage(),map);
            }
        }else {
            return AppUtil.msgMap(BizExceptionEnum.AUTH_REQUEST_ERROR.getCode(), BizExceptionEnum.AUTH_REQUEST_ERROR.getMessage(),null);
        }

    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/appLogout")
    public Map<String, Object>  appLogout(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String token = request.getParameter("token");

        if(ToolUtil.isEmpty(account)||ToolUtil.isEmpty(token)){
            return AppUtil.msgMap(BizExceptionEnum.CANSHU_ERR.getCode(), BizExceptionEnum.CANSHU_ERR.getMessage(),null);
        }

        User user = userService.getByAccount(account);
        //根据用户名找到了用户
        if(user != null) {
            //检查token
            if (token.equals(user.getToken())) {
                userService.delToken(user.getId());
                LogManager.me().executeLog(LogTaskFactory.appExitLog(user.getId(), getIp()));
                return AppUtil.msgMap(BizExceptionEnum.LOGOUT_SUCCESS.getCode(), BizExceptionEnum.LOGOUT_SUCCESS.getMessage(), null);
            }else{
                return AppUtil.msgMap(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage(), null);
            }
        }else{
            return AppUtil.msgMap(BizExceptionEnum.SSO_NO_THIS_USER.getCode(), BizExceptionEnum.SSO_NO_THIS_USER.getMessage(),null);
        }
    }



}

