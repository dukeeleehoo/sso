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
package cn.future.core.common.exception;

import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * @author fengshuonan
 * @Description 所有业务异常的枚举
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum implements AbstractBaseExceptionEnum {


//    消息推送
    MSG_EMPTY(20001,"推送消息为空"),

    //    单点登录
    APP_STATUS_FAIL(10002,"应用已被停止使用单点登录系统"),
    TOKEN_CANCELD(10010,"teoken无效"),
    LINK_SUCCESS(10000,"success"),
    LOGOUT_SUCCESS(10000,"登出成功"),
    //sso
    YES(10000,"响应成功"),
    SSO_YES_PWD(10000, "密码修改成功"),
    APP_NOT_EXIT(10001,"应用认证失败"),
    APP_NOT_TING(10002,"应用已停用"),
    INTERFACE_NOFIND(10003,"接口认证失败"),
    INTERFACE_TING(10004,"接口已停用"),
    IP_CANCELD(10005,"ip认证失败"),
    CANSHU_ERR(10006,"请仔细检查参数"),
    DEPT_ERROR(10006,"未找到部门"),
    RANK_ERROR(10006,"未找到职级"),
    MAJORPOSITION(10006,"未找到岗位"),
    RSA_ERROR(10006,"解密失败"),
    MOBILE_ERROR(10006,"手机号已存在"),
    SSO_OLD_PWD_NOT_RIGHT(10006, "原密码不正确"),
    NO(10006,null),
    SSO_USER_ALREADY_REG(10007, "该用户已经注册"),
    SSO_NO_THIS_USER(10008, "没有此用户"),
    ERROR(10009,null),
    ERROR_URL(10011,"url链接不正确"),
    ERROR_PUSH(10012,"推送失败"),

    /**
     * 字典
     */
    DICT_EXISTED(400, "字典已经存在"),
    ERROR_CREATE_DICT(500, "创建字典失败"),
    ERROR_WRAPPER_FIELD(500, "包装字典属性失败"),
    ERROR_CODE_EMPTY(500, "字典类型不能为空"),

    /**
     * 文件上传
     */
    FILE_READING_ERROR(400, "FILE_READING_ERROR!"),
    FILE_NOT_FOUND(400, "FILE_NOT_FOUND!"),
    UPLOAD_ERROR(500, "上传图片出错"),

    /**
     * 权限和数据问题
     */
    DB_RESOURCE_NULL(400, "数据库中没有该资源"),
    NO_PERMITION(405, "权限异常"),
    REQUEST_INVALIDATE(400, "请求数据格式不正确"),
    INVALID_KAPTCHA(400, "验证码不正确"),
    CANT_DELETE_ADMIN(600, "不能删除超级管理员"),
    CANT_FREEZE_ADMIN(600, "不能冻结超级管理员"),
    CANT_CHANGE_ADMIN(600, "不能修改超级管理员角色"),

    /**
     * 账户问题
     */
    USER_ALREADY_REG(401, "该用户已经注册"),
    USER_SHOUJI_REG(401, "该手机号码已经使用"),
    NO_THIS_USER(400, "没有此用户"),
    USER_NOT_EXISTED(400, "没有此用户"),
    ACCOUNT_FREEZED(401, "账号被冻结"),
    OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
    TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),
    YES_PWD(200, "密码修改成功"),
    /**
     * 错误的请求
     */
    MENU_PCODE_COINCIDENCE(400, "菜单编号和副编号不能一致"),
    EXISTED_THE_MENU(400, "菜单编号重复，不能添加"),
    DICT_MUST_BE_NUMBER(400, "字典的值必须为数字"),
    REQUEST_NULL(400, "请求有错误"),
    SESSION_TIMEOUT(400, "会话超时"),
    SERVER_ERROR(500, "服务器异常"),

    /**
     * token异常
     */
    TOKEN_EXPIRED(700, "token过期"),
    TOKEN_ERROR(700, "token验证失败"),

    /**
     * 签名异常
     */
    SIGN_ERROR(700, "签名验证失败"),

    /**
     * 其他
     */
    AUTH_REQUEST_YES(200, "登录成功"),
    CODE_ERROR(400, "该编号已经使用"),
    AUTH_REQUEST_ERROR(400, "账号密码错误");


    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }



    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
