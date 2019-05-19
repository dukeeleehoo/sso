package cn.future.modular.unified.controller;

import cn.future.core.common.constant.state.ManagerStatus;
import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.core.common.node.UnifiedMenuNode;
import cn.future.core.shiro.ShiroKit;
import cn.future.core.util.Md5Encrypt;
import cn.future.modular.system.model.Dept;
import cn.future.modular.system.model.Majorposition;
import cn.future.modular.system.model.Rank;
import cn.future.modular.system.model.User;
import cn.future.modular.system.service.*;
import cn.future.modular.unified.service.*;
import cn.future.modular.unified.model.Application;
import cn.future.modular.unified.model.UnifiedUserRole;
import cn.future.modular.unified.model.Uninterface;
import cn.future.sso.RSAUtils;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 接口Controller
 * @author zp
 * @version 2018-12-13
 */
@RestController
@RequestMapping("unifiedInterface")
public class UnifiedInterface{

    @Autowired
    private IApplicationService applicationService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUninterfaceLogService uninterfaceLogService;
    @Autowired
    private IUninterfaceService uninterfaceService;
    @Autowired
    private IMajorpositionService majorpositionService;
    @Autowired
    private IRankService rankService;
    @Autowired
    private IUnifiedRelationService unifiedRelationService;
    @Autowired
    private IUnifiedUserRoleService unifiedUserRoleService;
    @Autowired
    private IUnifiedMenuService unifiedMenuService;

    /**
     * 用户信息查看服务
     */
    @RequestMapping(value = "/findUnifiedUserByAccount", method = RequestMethod.POST)
    public Map<String, Object> findUnifiedUserByAccount(HttpServletRequest request, HttpServletResponse response) throws Exception  {
        //获取当前时间为开始时间
        long startTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
        Map<String, Object> map = new HashMap<String, Object>();
        String appid = request.getParameter("appid");
        String code = request.getParameter("code");
        Application application = applicationService.getByAppid(appid);
        Uninterface uninterface = uninterfaceService.selectByUrl(request.getServletPath());
        try {
            String result = RSAUtils.decryptByPrivateKey(code,application);
            JSONObject json = JSON.parseObject(result);
            String account = json.getString("account");
            if (ToolUtil.isEmpty(account)) {
                return mapErroe(BizExceptionEnum.CANSHU_ERR.getMessage(), application, uninterface);
            }
            String token = json.getString("token");
            if (ToolUtil.isEmpty(token)) {
                return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
            }else{
                if(!token.equals(application.getToken())){
                    return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
                }
            }
            User user = userService.getByAccount(account);
            if (null == user) {
                map.put("code", BizExceptionEnum.SSO_NO_THIS_USER.getCode());
                map.put("msg", BizExceptionEnum.SSO_NO_THIS_USER.getMessage());
                map.put("data", "");
            }else {
                map.put("code", BizExceptionEnum.YES.getCode());
                map.put("msg", BizExceptionEnum.YES.getMessage());
                map.put("data", RSAUtils.encryptByPrivateKey(JSON.toJSONString(user), application));
            }
            long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
            uninterfaceLogService.addUninterfaceLog(application.getId(), uninterface.getId(), String.valueOf(stopTime - startTime), JSONObject.toJSONString(map));
            return map;
        }catch (javax.crypto.BadPaddingException e) {
            return ExceptionErroe("解密失败", application, uninterface);
        }catch (Exception e) {
            return ExceptionErroe(e.toString(), application, uninterface);
        }
    }

    /**
     * 获取部门列表服务
     */
    @RequestMapping(value = "/findDept", method = RequestMethod.POST)
    public Map<String, Object> findDept(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        long startTime =fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
        Map<String,Object> map = new HashMap<String,Object>();
        String appid = request.getParameter("appid");
        String code = request.getParameter("code");
        Application application = applicationService.getByAppid(appid);
        Uninterface uninterface = uninterfaceService.selectByUrl(request.getServletPath());
        try{
            String result = RSAUtils.decryptByPrivateKey(code,application);
            JSONObject json = JSON.parseObject(result);
            String token = json.getString("token");
            if (ToolUtil.isEmpty(token)) {
                return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
            }else{
                if(!token.equals(application.getToken())){
                    return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
                }
            }
            List<Dept> list = this.deptService.findList();
            map.put("code", BizExceptionEnum.YES.getCode());
            map.put("msg", BizExceptionEnum.YES.getMessage());
            map.put("data",RSAUtils.encryptByPrivateKey(JSON.toJSONString(list),application));
            long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
            uninterfaceLogService.addUninterfaceLog(application.getId(),uninterface.getId(), String.valueOf(stopTime - startTime),JSONObject.toJSONString(map));
            return map;
        }catch (javax.crypto.BadPaddingException e) {
            return ExceptionErroe("解密失败", application, uninterface);
        }catch (Exception e) {
            return ExceptionErroe(e.toString(), application, uninterface);
        }
    }

    /**
     * 校验用户名是否唯一服务
     */
    @RequestMapping(value = "/checkAccount", method = RequestMethod.POST)
    public Map<String, Object> checkAccount(HttpServletRequest request, HttpServletResponse response) throws Exception  {
        long startTime =fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
        Map<String,Object> map = new HashMap<String,Object>();
        String appid = request.getParameter("appid");
        String code = request.getParameter("code");
        Application application = applicationService.getByAppid(appid);
        Uninterface uninterface = uninterfaceService.selectByUrl(request.getServletPath());
        try{
            String result = RSAUtils.decryptByPrivateKey(code,application);
            JSONObject json = JSON.parseObject(result);
            String account = json.getString("account");
            if (ToolUtil.isEmpty(account)) {
                return mapErroe(BizExceptionEnum.CANSHU_ERR.getMessage(), application, uninterface);
            }
            String token = json.getString("token");
            if (ToolUtil.isEmpty(token)) {
                return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
            }else{
                if(!token.equals(application.getToken())){
                    return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
                }
            }
            User user = userService.getByAccount(account);
            if (null == user){
                map.put("code",BizExceptionEnum.SSO_NO_THIS_USER.getCode());
                map.put("msg",BizExceptionEnum.SSO_NO_THIS_USER.getMessage());
            }else {
                map.put("code", BizExceptionEnum.SSO_USER_ALREADY_REG.getCode());
                map.put("msg", BizExceptionEnum.SSO_USER_ALREADY_REG.getMessage());
            }
            map.put("data", "");
            long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
            uninterfaceLogService.addUninterfaceLog(application.getId(),uninterface.getId(), String.valueOf(stopTime - startTime),JSONObject.toJSONString(map));
            return map;
        }catch (javax.crypto.BadPaddingException e) {
            return ExceptionErroe("解密失败", application, uninterface);
        }catch (Exception e) {
            return ExceptionErroe(e.toString(), application, uninterface);
        }
    }

    /**
     * 修改密码服务
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public Map<String, Object> updatePassword(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        long startTime =fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
        Map<String,Object> map = new HashMap<String,Object>();
        String appid = request.getParameter("appid");
        String code = request.getParameter("code");
        Application application = applicationService.getByAppid(appid);
        Uninterface uninterface = uninterfaceService.selectByUrl(request.getServletPath());
        try{
            String result = RSAUtils.decryptByPrivateKey(code,application);
            JSONObject json = JSON.parseObject(result);
            String account = json.getString("account");
            String oldPassword = json.getString("oldPassword");
            String newPassword = json.getString("newPassword");
            if((ToolUtil.isEmpty(account)) || (ToolUtil.isEmpty(oldPassword)) || ToolUtil.isEmpty(newPassword)){
                return mapErroe(BizExceptionEnum.CANSHU_ERR.getMessage(),application,uninterface);
            }
            String token = json.getString("token");
            if (ToolUtil.isEmpty(token)) {
                return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
            }else{
                if(!token.equals(application.getToken())){
                    return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
                }
            }
            User user = userService.getByAccount(account);
            if (null == user) {
                map.put("code", BizExceptionEnum.SSO_NO_THIS_USER.getCode());
                map.put("msg", BizExceptionEnum.SSO_NO_THIS_USER.getMessage());
            }else {
                String oldMd5 = Md5Encrypt.md5(oldPassword).toLowerCase();
                if (user.getPassword().equals(oldMd5)) {
                    String newMd5 = Md5Encrypt.md5(newPassword).toLowerCase();
                    user.setPassword(newMd5);
                    user.updateById();
                    map.put("code", BizExceptionEnum.SSO_YES_PWD.getCode());
                    map.put("msg", BizExceptionEnum.SSO_YES_PWD.getMessage());
                } else {
                    return mapErroe(BizExceptionEnum.SSO_OLD_PWD_NOT_RIGHT.getMessage(),application,uninterface);
                }
            }
            map.put("data","");
            long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
            uninterfaceLogService.addUninterfaceLog(application.getId(),uninterface.getId(), String.valueOf(stopTime - startTime),JSONObject.toJSONString(map));
            return map;
        }catch (javax.crypto.BadPaddingException e) {
            return ExceptionErroe("解密失败", application, uninterface);
        }catch (Exception e) {
            return ExceptionErroe(e.toString(), application, uninterface);
        }
    }

    /**
     * 用户注册服务
     */
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public Map<String, Object> userRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long startTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
        Map<String, Object> map = new HashMap<String, Object>();
        String appid = request.getParameter("appid");
        String code = request.getParameter("code");
        Application application = applicationService.getByAppid(appid);
        Uninterface uninterface = uninterfaceService.selectByUrl(request.getServletPath());
        try {
            String result = RSAUtils.decryptByPrivateKey(code,application);
            JSONObject json = JSON.parseObject(result);
            String account = json.getString("account");
            String password = json.getString("password");
            String name = json.getString("name");
            String phone = json.getString("phone");
            String mobile = json.getString("mobile");
            String email = json.getString("email");
            String deptid = json.getString("deptid");
            String rank = json.getString("rank");
            String majorposition = json.getString("majorposition");

            if ((ToolUtil.isEmpty(account)) || (ToolUtil.isEmpty(password)) || ToolUtil.isEmpty(name) || ToolUtil.isEmpty(mobile)
                    ||(ToolUtil.isEmpty(deptid)) || (ToolUtil.isEmpty(rank)) || ToolUtil.isEmpty(majorposition)) {
                return mapErroe(BizExceptionEnum.CANSHU_ERR.getMessage(), application, uninterface);
            }
            //验证token
            String token = json.getString("token");
            if (ToolUtil.isEmpty(token)) {
                return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
            }else{
                if(!token.equals(application.getToken())){
                    return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
                }
            }
            //验证deptid是否有效
            Dept dept = deptService.selectById(deptid);
            if(null == dept){
                return mapErroe(BizExceptionEnum.DEPT_ERROR.getMessage(), application, uninterface);
            }
            //验证rank是否有效
            Rank r = rankService.getByCode(rank);
            if(null == r){
                return mapErroe(BizExceptionEnum.RANK_ERROR.getMessage(), application, uninterface);
            }
            //验证majorposition是否有效
            Majorposition m = majorpositionService.getByCode(majorposition);
            if(null == m){
                return mapErroe(BizExceptionEnum.MAJORPOSITION.getMessage(), application, uninterface);
            }
            //判断手机号是否存在
            User mUser = userService.getByMobile(mobile);
            if(null != mUser){
                return mapErroe(BizExceptionEnum.MOBILE_ERROR.getMessage(), application, uninterface);
            }
            // 判断账号是否重复
            User user = userService.getByAccount(account);
            if (null != user) {
                map.put("code", BizExceptionEnum.SSO_USER_ALREADY_REG.getCode());
                map.put("msg", BizExceptionEnum.SSO_USER_ALREADY_REG.getMessage());
            } else {
                user = new User();
                String salt = ShiroKit.getRandomSalt(5);
                user.setAccount(account);
                user.setName(name);
                //默认对应用应用赋值为当前注册来源应用
                user.setApplicationids(application.getId().toString());
                //角色给个6
                user.setRoleid("6");
                user.setMobile(mobile);
                user.setRank(Integer.valueOf(rank));
                user.setMajorposition(Integer.valueOf(majorposition));
                user.setPhone(phone);
                user.setEmail(email);
                user.setDeptid(Integer.valueOf(deptid));
                if (ToolUtil.isNotEmpty(json.getString("sex"))) {
                    user.setSex(Integer.parseInt(json.getString("sex")));
                }
                user.setSalt(salt);
                user.setPassword(Md5Encrypt.md5(password).toLowerCase());
                user.setStatus(ManagerStatus.OK.getCode());
                user.setCreatetime(new Date());
                userService.insert(user);
                map.put("code", BizExceptionEnum.YES.getCode());
                map.put("msg", "用户[" + account + "] 注册成功");
            }
            map.put("data","");
            long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
            uninterfaceLogService.addUninterfaceLog(application.getId(), uninterface.getId(), String.valueOf(stopTime - startTime), JSONObject.toJSONString(map));
            return map;
        }catch (javax.crypto.BadPaddingException e) {
            return ExceptionErroe("解密失败", application, uninterface);
        }catch (Exception e) {
            return ExceptionErroe(e.toString(), application, uninterface);
        }
    }

    /**
     * 用户编辑服务
     */
    @RequestMapping(value = "/userEdit", method = RequestMethod.POST)
    public Map<String, Object> userEdit(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        long startTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
        Map<String, Object> map = new HashMap<String, Object>();
        String appid = request.getParameter("appid");
        String code = request.getParameter("code");
        Application application = applicationService.getByAppid(appid);
        Uninterface uninterface = uninterfaceService.selectByUrl(request.getServletPath());
        try{
            String result = RSAUtils.decryptByPrivateKey(code,application);
            JSONObject json = JSON.parseObject(result);
            String account = json.getString("account");
            String name = json.getString("name");
            String phone = json.getString("phone");
            String mobile = json.getString("mobile");
            String email = json.getString("email");
            String deptid = json.getString("deptid");
            String rank = json.getString("rank");
            String majorposition = json.getString("majorposition");

            if ((ToolUtil.isEmpty(account))) {
                return mapErroe(BizExceptionEnum.CANSHU_ERR.getMessage(), application, uninterface);
            }
            String token = json.getString("token");
            if (ToolUtil.isEmpty(token)) {
                return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
            }else{
                if(!token.equals(application.getToken())){
                    return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
                }
            }
            // 判断账号是否重复
            User user = userService.getByAccount(account);
            if (null == user) {
                map.put("code", BizExceptionEnum.SSO_NO_THIS_USER.getCode());
                map.put("meg", BizExceptionEnum.SSO_NO_THIS_USER.getMessage());
            }else {
                //判断手机号是否存在
                if (ToolUtil.isNotEmpty(mobile)) {
                    User mUser = userService.getByMobile(mobile);
                    if (null != mUser) {
                        if (!user.getMobile().equals(mobile)) {
                            return mapErroe(BizExceptionEnum.MOBILE_ERROR.getMessage(), application, uninterface);
                        }
                    }
                }
                if (ToolUtil.isNotEmpty(name)) {
                    user.setName(name);
                }
                if (ToolUtil.isNotEmpty(mobile)) {
                    user.setMobile(mobile);
                }
                if (ToolUtil.isNotEmpty(phone)) {
                    user.setPhone(phone);
                }
                if (ToolUtil.isNotEmpty(email)) {
                    user.setEmail(email);
                }
                if (ToolUtil.isNotEmpty(deptid)) {
                    Dept dept = deptService.selectById(deptid);
                    if (null == dept) {
                        return mapErroe(BizExceptionEnum.DEPT_ERROR.getMessage(), application, uninterface);
                    } else {
                        user.setDeptid(Integer.parseInt(deptid));
                    }
                }
                if (ToolUtil.isNotEmpty(rank)) {
                    //验证rank是否有效
                    Rank r = rankService.getByCode(rank);
                    if(null == r){
                        return mapErroe(BizExceptionEnum.RANK_ERROR.getMessage(), application, uninterface);
                    }{
                        user.setRank(Integer.valueOf(rank));
                    }
                }
                if (ToolUtil.isNotEmpty(majorposition)) {
                    //验证majorposition是否有效
                    Majorposition m = majorpositionService.getByCode(majorposition);
                    if(null == m){
                        return mapErroe(BizExceptionEnum.MAJORPOSITION.getMessage(), application, uninterface);
                    }else{
                        user.setMajorposition(Integer.valueOf(majorposition));
                    }
                }
                if (ToolUtil.isNotEmpty(json.getString("sex"))) {
                    user.setSex(Integer.parseInt(json.getString("sex")));
                }
                userService.updateById(user);
                map.put("code", BizExceptionEnum.YES.getCode());
                map.put("msg", "用户[" + account + "] 修改成功");
            }
            map.put("data", "");
            long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
            uninterfaceLogService.addUninterfaceLog(application.getId(), uninterface.getId(), String.valueOf(stopTime - startTime), JSONObject.toJSONString(map));
            return map;
        }catch (javax.crypto.BadPaddingException e) {
            return ExceptionErroe("解密失败", application, uninterface);
        }catch (Exception e) {
            return ExceptionErroe(e.toString(), application, uninterface);
        }
    }

    /**
     * 获取用户列表服务
     */
    @RequestMapping(value = "/findUser", method = RequestMethod.POST)
    public Map<String, Object> findUser(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        long startTime =fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
        Map<String,Object> map = new HashMap<String,Object>();
        String appid = request.getParameter("appid");
        String code = request.getParameter("code");
        Application application = applicationService.getByAppid(appid);
        Uninterface uninterface = uninterfaceService.selectByUrl(request.getServletPath());
        try{
            String result = RSAUtils.decryptByPrivateKey(code,application);
            JSONObject json = JSON.parseObject(result);
            String token = json.getString("token");
            //一页多少条数据
            Integer size = json.getInteger("size");
            //第几页
            Integer current = json.getInteger("current");
            if((ToolUtil.isEmpty(size)) || (ToolUtil.isEmpty(current))){
                return mapErroe(BizExceptionEnum.CANSHU_ERR.getMessage(),application,uninterface);
            }
            if (ToolUtil.isEmpty(token)) {
                return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
            }else{
                if(!token.equals(application.getToken())){
                    return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
                }
            }
            Page page  = new Page();
            page.setSize(size);
            page.setCurrent(current);
            Page<User> list = this.userService.selectPage(page);
            map.put("code", BizExceptionEnum.YES.getCode());
            map.put("msg", BizExceptionEnum.YES.getMessage());
            map.put("data",RSAUtils.encryptByPrivateKey(JSON.toJSONString(list),application));
            long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
            uninterfaceLogService.addUninterfaceLog(application.getId(),uninterface.getId(), String.valueOf(stopTime - startTime),JSONObject.toJSONString(map));
            return map;
        }catch (javax.crypto.BadPaddingException e) {
            return ExceptionErroe("解密失败", application, uninterface);
        }catch (Exception e) {
            return ExceptionErroe(e.toString(), application, uninterface);
        }
    }

    /**
     * 获取全部用户列表服务
     */
    @RequestMapping(value = "/findAllUser", method = RequestMethod.POST)
    public Map<String, Object> findAllUser(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        long startTime =fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
        Map<String,Object> map = new HashMap<String,Object>();
        String appid = request.getParameter("appid");
        String code = request.getParameter("code");
        Application application = applicationService.getByAppid(appid);
        Uninterface uninterface = uninterfaceService.selectByUrl(request.getServletPath());
        try{
            String result = RSAUtils.decryptByPrivateKey(code,application);
            JSONObject json = JSON.parseObject(result);
            String token = json.getString("token");
            if (ToolUtil.isEmpty(token)) {
                return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
            }else{
                if(!token.equals(application.getToken())){
                    return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
                }
            }
            List<User> list = this.userService.findList();
            map.put("code", BizExceptionEnum.YES.getCode());
            map.put("msg", BizExceptionEnum.YES.getMessage());
            map.put("data",list);
            long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
            uninterfaceLogService.addUninterfaceLog(application.getId(),uninterface.getId(), String.valueOf(stopTime - startTime),JSONObject.toJSONString(map));
            return map;
        }catch (javax.crypto.BadPaddingException e) {
            return ExceptionErroe("解密失败", application, uninterface);
        }catch (Exception e) {
            return ExceptionErroe(e.toString(), application, uninterface);
        }
    }


    /**
     * 根据code获取信息
     */
    @RequestMapping(value = "/findDictByParentCode", method = RequestMethod.POST)
    public Map<String, Object> findDictByDCode(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        long startTime =fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
        Map<String,Object> map = new HashMap<String,Object>();
        String appid = request.getParameter("appid");
        String code = request.getParameter("code");
        Application application = applicationService.getByAppid(appid);
        Uninterface uninterface = uninterfaceService.selectByUrl(request.getServletPath());
        try{
            String result = RSAUtils.decryptByPrivateKey(code,application);
            JSONObject json = JSON.parseObject(result);
            String token = json.getString("token");
            if (ToolUtil.isEmpty(token)) {
                return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
            }else{
                if(!token.equals(application.getToken())){
                    return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
                }
            }
            String dcode = json.getString("dcode");
            if (ToolUtil.isEmpty(dcode)) {
                return mapErroe(BizExceptionEnum.CANSHU_ERR.getMessage(), application, uninterface);
            }else if("majorposition".equals(dcode)){
                List<Majorposition> list = majorpositionService.findList();
                map.put("data",list);
            }else if("rank".equals(dcode)){
                List<Rank> list = rankService.findList();
                map.put("data",list);
            }else{
                return mapErroe(BizExceptionEnum.CANSHU_ERR.getMessage(), application, uninterface);
            }
            map.put("code", BizExceptionEnum.YES.getCode());
            map.put("msg", BizExceptionEnum.YES.getMessage());
            long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
            uninterfaceLogService.addUninterfaceLog(application.getId(),uninterface.getId(), String.valueOf(stopTime - startTime),JSONObject.toJSONString(map));
            return map;
        }catch (javax.crypto.BadPaddingException e) {
            return ExceptionErroe("解密失败", application, uninterface);
        }catch (Exception e) {
            return ExceptionErroe(e.toString(), application, uninterface);
        }
    }


    /**
     * 获取菜单
     */
    @RequestMapping(value = "/findAuthMenu", method = RequestMethod.POST)
    public Map<String, Object> findAuthMenu(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        long startTime =fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
        Map<String,Object> map = new HashMap<String,Object>();
        String appid = request.getParameter("appid");
        String code = request.getParameter("code");
        Application application = applicationService.getByAppid(appid);
        Uninterface uninterface = uninterfaceService.selectByUrl(request.getServletPath());
        try{
            String result = RSAUtils.decryptByPrivateKey(code,application);
            JSONObject json = JSON.parseObject(result);
            String token = json.getString("token");
            if (ToolUtil.isEmpty(token)) {
                return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
            }else{
                if(!token.equals(application.getToken())){
                    return mapErroe(BizExceptionEnum.APP_NOT_EXIT.getMessage(), application, uninterface);
                }
            }
            String account = json.getString("account");
            if((ToolUtil.isEmpty(account))){
                return mapErroe(BizExceptionEnum.CANSHU_ERR.getMessage(),application,uninterface);
            }
            User user = userService.getByAccount(account);
            if (null == user) {
                return mapErroe(BizExceptionEnum.SSO_NO_THIS_USER.getMessage(),application,uninterface);
            }
            //根据appid和accountid得到用户角色
            List<UnifiedUserRole> list = unifiedUserRoleService.getRole(user.getId(),appid);
            List<UnifiedMenuNode> li = unifiedMenuService.getMenusByRoleIds(list);
            map.put("code", BizExceptionEnum.YES.getCode());
            map.put("msg", BizExceptionEnum.YES.getMessage());
            map.put("data", li);
            long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
            uninterfaceLogService.addUninterfaceLog(application.getId(),uninterface.getId(), String.valueOf(stopTime - startTime),JSONObject.toJSONString(map));
            return map;
        }catch (javax.crypto.BadPaddingException e) {
            return ExceptionErroe("解密失败", application, uninterface);
        }catch (Exception e) {
            return ExceptionErroe(e.toString(), application, uninterface);
        }
    }

    /**
     * 根据String型时间，获取long型时间，单位毫秒
     * @param inVal 时间字符串
     * @return long型时间
     */
    public static long fromDateStringToLong(String inVal) {
        Date date = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        try {
            date = inputFormat.parse(inVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 公用错误提示
     * @return
     */
    public Map<String, Object> mapErroe(String msg,Application application,Uninterface uninterface) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", BizExceptionEnum.NO.getCode());
        map.put("msg", msg);
        map.put("data", "");
        uninterfaceLogService.addUninterfaceLog(
                (null == application) ? null:application.getId(),
                (null == uninterface) ? null:uninterface.getId(),null,JSONObject.toJSONString(map));
        return map;
    }

    /**
     * 异常错误提示
     * @return
     */
    public Map<String, Object> ExceptionErroe(String msg,Application application,Uninterface uninterface) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",BizExceptionEnum.ERROR.getCode());
        map.put("msg", msg);
        map.put("data", "");
        uninterfaceLogService.addUninterfaceLog(
                (null == application) ? null:application.getId(),
                (null == uninterface) ? null:uninterface.getId(),null,JSONObject.toJSONString(map));
        return map;
    }

}
