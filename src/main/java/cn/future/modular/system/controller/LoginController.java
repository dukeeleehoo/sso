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
package cn.future.modular.system.controller;


import cn.future.config.properties.GunsProperties;
import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.core.common.exception.InvalidKaptchaException;
import cn.future.core.common.exception.UserNameAndPasswordException;
import cn.future.core.common.node.MenuNode;
import cn.future.core.log.LogManager;
import cn.future.core.log.factory.LogTaskFactory;
import cn.future.core.shiro.ShiroKit;
import cn.future.core.shiro.ShiroUser;
import cn.future.core.tokenlogin.UserAndToken;
import cn.future.core.util.ApiMenuFilter;
import cn.future.core.util.KaptchaUtil;
import cn.future.core.util.UserAgentUtils;
import cn.future.modular.system.model.Notice;
import cn.future.modular.system.model.User;
import cn.future.modular.system.service.IMenuService;
import cn.future.modular.system.service.INoticeService;
import cn.future.modular.system.service.IUserService;
import cn.future.modular.unified.service.IApplicationService;
import cn.future.sso.DaiBanEntity;
import cn.future.sso.GISDaiBanEntity;
import cn.future.sso.RSAUtils;
import cn.future.sso.oa.*;
import cn.hutool.http.HttpUtil;

import cn.future.modular.unified.model.Application;


import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.code.kaptcha.Constants;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Case;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static cn.stylefeng.roses.core.util.HttpContext.getIp;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
@Slf4j
public class LoginController extends BaseController {


    @Autowired
    AuthorityService authorityService;

    @Autowired
    AffairRest affairRest;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IApplicationService iApplicationService;

    @Autowired
    private GunsProperties gunsProperties;
    @Autowired
    private INoticeService noticeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private static String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则
    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index1(Model model) {

        if (!ShiroKit.isAuthenticated() || ShiroKit.getUser() == null) {
            return "/login.html";
        }
        //获取菜单列表
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/login.html";
        }
        List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);

        //获取用户头像
        Integer id = ShiroKit.getUser().getId();
        User user = userService.selectById(id);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);

        return "/index.html";
    }


    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //获取菜单列表
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/login.html";
        }
        List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);

        //获取用户头像
        Integer id = ShiroKit.getUser().getId();
        User user = userService.selectById(id);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);


        return REDIRECT+"/main";
    }

    /**
     *
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String redirctUrl, HttpServletRequest request,String appid,String code) throws Exception {
        model.addAttribute("appid", appid);
        model.addAttribute("redirctUrl", redirctUrl);
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            model.addAttribute("account", ShiroKit.getUser().getAccount());
            return REDIRECT+"/main";
        } else {
            return "/login.html";
        }
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali(HttpServletRequest request,Model model) {



        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");

        if(ToolUtil.isEmpty(username)||ToolUtil.isEmpty(password)){
            throw new UserNameAndPasswordException();
        }

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        if(UserAgentUtils.isMobile(super.getHttpServletRequest())){
        String tokenstr = UUID.randomUUID().toString().replace("-", "");
        User user = userService.getByAccount(shiroUser.getAccount());
        //修改用户信息
        user.setToken(tokenstr);
        userService.updateById(user);
    }

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);
        model.addAttribute("account", username);
        return REDIRECT+"/main";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        deleteAllCookie();
        return REDIRECT + "/login";
    }



    /**
     * app点击登录执行的动作
     */
    @RequestMapping(value = "/applogin", method = RequestMethod.POST)
    @ResponseBody
    public Map apploginVali(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = new HashMap();
        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");


        if(ToolUtil.isEmpty(username)||ToolUtil.isEmpty(password)){
            map.put("code", 400);
            map.put("msg", "请输入用户名和密码!");
            return map;
        }

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                map.put("code", 400);
                map.put("msg", "验证码错误!");
                return map;
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }
        try {
            currentUser.login(token);
        }catch (Exception e){
            map.put("code", 400);
            map.put("msg", "用户名或密码错误!");
            return map;
        }


        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        //单点登陆操作
        String appid = super.getPara("appid");
        User user = userService.getByAccount(shiroUser.getAccount());
        String ids = user.getApplicationids();
        if(!ids.contains(appid)){
            map.put("code", 400);
            map.put("msg", "您没有权限访问!");
            return map;
        }
        Wrapper<Application> applicationWrapper = new EntityWrapper<>();
        applicationWrapper.eq("appid", appid);
        List<Application> applications = iApplicationService.selectList(applicationWrapper);
        if(applications==null||applications.size()==0){
            map.put("code", BizExceptionEnum.APP_NOT_EXIT.getCode());
            map.put("msg", BizExceptionEnum.APP_NOT_EXIT.getMessage());
            return map;
        }
        Application application = applications.get(0);

        if(application.getFlag().toString().equals("2")){
            map.put("code", BizExceptionEnum.APP_STATUS_FAIL.getCode());
            map.put("msg", BizExceptionEnum.APP_STATUS_FAIL.getMessage());
            return map;
        }
        String redirectUrl = super.getPara("redirctUrl");
        map.put("account", username);
        map.put("redirctUrl", redirectUrl);
        map.put("token", application.getToken());
        map.put("code", BizExceptionEnum.LINK_SUCCESS.getCode());
        map.put("msg", BizExceptionEnum.LINK_SUCCESS.getMessage());
        String str = JSON.toJSON(map).toString();
        map.put("data", RSAUtils.encryptByPrivateKey(str, application));
        LOGGER.debug("应用名称："+application.getName());
        LOGGER.debug("回调回传参数："+ RSAUtils.encryptByPrivateKey(str, application));
        map.put("code", BizExceptionEnum.LINK_SUCCESS.getCode());
        map.put("authurl", application.getAuthurl());
        LOGGER.debug("全部参数："+map.toString());
        return map;
    }



    @RequestMapping("/main")
    public String main(Model model) throws Exception {

        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            List<String> roles = ShiroKit.getUser().getRoleNames() ;
            model.addAttribute("account", ShiroKit.getUser().getAccount());
            Integer id = ShiroKit.getUser().getId();
            User user = userService.selectById(id);
            String str = user.getApplicationids();
            Wrapper<Application> wrapper = new EntityWrapper<>();
            Boolean ismobile = UserAgentUtils.isMobile(super.getHttpServletRequest());
            if(ismobile){
                wrapper.eq("type", 2);
            }else{
                wrapper.eq("type", 1);
            }
            wrapper.orderBy("sort");
            List<Map<String, Object>> applications = iApplicationService.selectMaps(wrapper);
            if(str!=null && !"".equals(str)){
                String[] idArr = str.split(",");
                for(Map<String,Object>  map :applications){
                    String appId = map.get("appid")+"";
                    if(ArrayUtils.contains(idArr,appId)){
                        Map paraMap = new HashMap();
                        paraMap.put("account", ShiroKit.getUser().getAccount());
                        paraMap.put("token", (String)map.get("token"));
                        map.put("data", RSAUtils.encryptByPrivateKey(JSON.toJSON(paraMap).toString(), (String) map.get("privatekey")));
                        map.put("has", "true");
                    }else{
                        map.put("has", "false");
                    }
                }
            }else{
                for(Map<String,Object>  map :applications){
                    map.put("has", "false");
                }
            }

            model.addAttribute("gismoreurl", gunsProperties.getGisMoreUrl());
            model.addAttribute("oanewsmore", gunsProperties.getGisMoreUrl());
            model.addAttribute("oaappid", "c9887b83-8f12-4244-94ff-e2e5bfc7aac3");
            model.addAttribute("gisappid", "8b224ff2-dca2-455b-bd1f-9fe364014191");
            Notice notice = noticeService.getMaxTimeNotice();
            model.addAttribute("apps", applications);
            model.addAttribute("roleNames", roles);
            model.addAttribute("roleNames", roles);
            model.addAttribute("msg", notice.getTitle());
            model.addAttribute("token", user.getToken());
            return "/main.html";
        }else{
            return REDIRECT+"/login.html";
        }

    }

    @RequestMapping("/downapp")
    public String downApp(){
      return "/downapp.html";
    }


    /**
     * 绩效代办获取
     */

    public List<DaiBanEntity> getJIXIAODAIBAN(){
        List<DaiBanEntity> list = new ArrayList<>();
        //构建内同板块内容
        Map<String, Object> param = new HashMap<>();
        param.put("userId", "jiangbin");
        param.put("pageNo", "1");
        param.put("pageSize", "6");
        String result = HttpUtil.post(gunsProperties.getJixiaoDaibanUrl(), param,6000);

        log.debug("绩效代办请求结果 ："+result);
        JSONObject object = JSON.parseObject(result);
        String code =object.getString("code");
        if(code.equals("10000")){
            String data = object.getString("data");
            JSONObject dataObject = JSON.parseObject(data);
             list = new ArrayList<>();
            list = JSON.parseArray(dataObject.getString("result"), DaiBanEntity.class);
            for(DaiBanEntity d:list){
                String yuan = d.getSubmitDate();
                Pattern pat = Pattern.compile(REGEX_CHINESE);
                Matcher mat = pat.matcher(yuan);
                System.out.println(mat.replaceAll("-").substring(0,10));
                d.setSubmitDate(mat.replaceAll("-").substring(0,10));
            }
            if(list.size()<6){
                for (int i=0;i<6-list.size();i++){
                    DaiBanEntity daiBanEntity = new DaiBanEntity();
                    list.add(daiBanEntity);
                }
            }
        }
        return list;
    }




    /**
     *
     * @return
     */
    @RequestMapping("/getgisdata")
    @ResponseBody
    public Object getGISDaiBan()  {
        List<GISDaiBanEntity> list = new ArrayList<>();
        Map<String,Object> map = new HashMap();
        String result = "";
        try {
             result = HttpUtil.get(gunsProperties.getGisDaiBanUrl(),6000);
        }catch (Exception e){
            log.debug("gis请求接口 :"+e.getMessage());
            map.put("code", 400);
            return map;
        }
        String data1 = result.substring(result.indexOf("["), result.indexOf("]")+1);
        log.debug("gis代办请求结果 ："+data1);
        if(data1.length()>0){
            list = JSON.parseArray(data1, GISDaiBanEntity.class);
        }
        List<GISDaiBanEntity> newList = new ArrayList<>();
        if(list.size()>6){
            for(GISDaiBanEntity g:list){
                if(newList.size()<6){
                    g.setAppid("8b224ff2-dca2-455b-bd1f-9fe364014191");
                    g.setUrl(gunsProperties.getGisOneUrl()+g.getWFBM());
                    newList.add(g);
                }
            }
        }
        map.put("code", 200);
        map.put("data", newList);
        return map;
    }




    /**
     *
     * @return
     */
    @RequestMapping("/getoadata")
    @ResponseBody
    public Object getOADaiBan()  {
        List<OADaiBan> list = new ArrayList<>();
        Map map = new HashMap();
        authorityService.setGunsProperties(gunsProperties);
        try {
            if (AuthorityService.auFlag) {
                String userCode = affairRest.getUserCode(ShiroKit.getUser().getAccount());
                if (StringUtils.isNotBlank(userCode)) {
                    list = affairRest.exportEdocPendingList(userCode);
                    for (OADaiBan oaDaiBan : list) {
                        oaDaiBan.setUrl(gunsProperties.getOaOneUrl() + oaDaiBan.getId());
                        String receiveTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.valueOf(oaDaiBan.getReceiveTime())));
                        oaDaiBan.setReceiveDate(receiveTime);
                        if(StringUtils.isNotBlank(oaDaiBan.getSubject())) {
                            if (oaDaiBan.getSubject().length() > 13) {
                                oaDaiBan.setSubject(oaDaiBan.getSubject().substring(0, 13) + "...");
                            }
                        }
                    }

                } else {
                    map.put("code", 400);
                    map.put("msg", "获取代办失败,人员编码为空!");
                    return map;
                }

            } else {
                map.put("code", 400);
                map.put("msg", "获取代办失败,用户OA系统认证失败!");
                return map;
            }
        }catch (NullPointerException e){
            map.put("code", 400);
            map.put("msg", "获取代办失败,人员编码为空!");
            return map;

        }catch (Exception e){
            authorityService.setGunsProperties(gunsProperties);
            String userCode = affairRest.getUserCode(ShiroKit.getUser().getAccount());
            if(StringUtils.isNotBlank(userCode)) {
                list = affairRest.exportEdocPendingList(userCode);
                for (OADaiBan oaDaiBan : list) {
                    oaDaiBan.setUrl(gunsProperties.getOaOneUrl() + oaDaiBan.getId());
                    String receiveTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.valueOf(oaDaiBan.getReceiveTime())));
                    oaDaiBan.setReceiveDate(receiveTime);
                    if (StringUtils.isNotBlank(oaDaiBan.getSubject())) {
                        if (oaDaiBan.getSubject().length() > 13) {
                            oaDaiBan.setSubject(oaDaiBan.getSubject().substring(0, 13) + "...");
                        }
                    }
                }
            }else{
                map.put("code", 400);
                map.put("msg", "获取代办失败,人员编码为空！");
                return map;
            }

        }

        map.put("code", 200);
        map.put("data", list);
        return map;
    }



    /**
     *
     * @return
     */
    @RequestMapping("/getoanews")
    @ResponseBody
    public Object getoanews()  {
        List<OANews> list = new ArrayList<>();
        Map map = new HashMap();
        authorityService.setGunsProperties(gunsProperties);
        try {
            if (AuthorityService.auFlag) {
                String userCode = affairRest.getUserCode(ShiroKit.getUser().getAccount());
                if (StringUtils.isNotBlank(userCode)) {
                    list = affairRest.getNews(userCode);
                    for (OANews oaNews : list) {
                        oaNews.setUrl(gunsProperties.getOaNewsOneUrl()+oaNews.getId());
                        String createDate =  new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.valueOf(oaNews.getCreateDate())));
                        oaNews.setCreateDateStr(createDate);
                        if(oaNews.getTitle().length()>13) {
                            oaNews.setTitle(oaNews.getTitle().substring(0, 13) + "...");
                        }
                    }

                } else {
                    map.put("code", 400);
                    map.put("msg", "获取新闻失败,人员编码为空!");
                    return map;
                }

            } else {
                map.put("code", 400);
                map.put("msg", "获取新闻失败,用户OA系统认证失败!");
                return map;
            }
        }catch (NullPointerException e){
            map.put("code", 400);
            map.put("msg", "获取新闻失败,人员编码为空！");
            return map;

        }catch (Exception e){
            authorityService.setGunsProperties(gunsProperties);
            String userCode = affairRest.getUserCode(ShiroKit.getUser().getAccount());
            if(StringUtils.isNotBlank(userCode)) {
                list = affairRest.getNews(userCode);
                for (OANews oaNews : list) {
                    oaNews.setUrl(gunsProperties.getOaNewsOneUrl()+oaNews.getId());
                    String createDate =  new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.valueOf(oaNews.getCreateDate())));
                    oaNews.setCreateDateStr(createDate);
                    if(oaNews.getTitle().length()>13) {
                        oaNews.setTitle(oaNews.getTitle().substring(0, 13) + "...");
                    }
                }
            }else{
                map.put("code", 400);
                map.put("msg", "获取新闻失败,人员编码为空!");
                return map;
            }
        }

        map.put("code", 200);
        map.put("data", list);
        return map;
    }

    /**
     *  oa  移动端代办获取
     * @return
     */
    @RequestMapping("/getMobileOaNews")
    @ResponseBody
    public Object getMobileOaNews()  {
        Map map = new HashMap();
        String account = ShiroKit.getUser().getAccount();
        String result="";
        try {
            result = HttpUtil.get(gunsProperties.getOaMobileOneUrl()+account,6000);
        }catch (Exception e){
            e.printStackTrace();
            log.debug("OA移动端信息获取失败",e);
            map.put("code", 400);
            map.put("msg", e.getMessage());
            return map;

        }

        List<OAMobileNewsDaiBan> list1 = JSON.parseArray(result, OAMobileNewsDaiBan.class);

        for(OAMobileNewsDaiBan oa:list1){
            oa.setAppid("76464d58-8db0-45d6-96d0-137f02ecfee0");
            switch (oa.getType()){
                case "col" :
                    oa.setTypeName("事务");
                    break;
                case "edoc" :
                    oa.setTypeName("公文");
                    break;
                case "meet" :
                    oa.setTypeName("会议");
                    break;
                default:
                    oa.setTypeName("其他");

            }
            oa.setUrl("http://microcental.oicp.net:40010"+oa.getUrl().replace("http://127.0.0.1:80",""));

        }

        map.put("code", 200);
        map.put("data", list1);
        return map;
    }




    /**
     * apptoken
     */
    @GetMapping(value = "/apptokenlogin")
    public String appteokenlogin(HttpServletRequest request,Model model,String token) {
        Subject currentUser = ShiroKit.getSubject();
        UserAndToken userAndToken = new UserAndToken(token);

        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            model.addAttribute("account", ShiroKit.getUser().getAccount());
            model.addAttribute("token", (userService.getByAccount(ShiroKit.getUser().getAccount()).getToken()));
            return REDIRECT + "/main";
        }

        currentUser.login(userAndToken);

        ShiroUser shiroUser = ShiroKit.getUser();

        User user = userService.getByAccount(shiroUser.getAccount());
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);
        model.addAttribute("account", shiroUser.getAccount());
        model.addAttribute("token",user.getToken());
        return REDIRECT+"/main";
    }



}
