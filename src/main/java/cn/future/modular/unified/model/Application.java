package cn.future.modular.unified.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 接入应用管理
 * </p>
 *
 * @author zp
 * @since 2018-12-06
 */
@TableName("unified_application")
public class Application extends Model<Application> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 应用名称
     */
    @TableField("name")
    private String name;
    /**
     * 是否可用
     */
    @TableField("flag")
    private Integer flag;
    /**
     * 应用访问路径
     */
    @TableField("url")
    private String url;
    /**
     * 应用登录页面的路径
     */
    @TableField("loginurl")
    private String loginurl;
    /**
     * 应用注销url
     */
    @TableField("cancelurl")
    private String cancelurl;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 状态(0：启用 1：删除）
     */
    @TableField("delflag")
    private Integer delflag;

    @TableField("creater")
    private String creater;

    @TableField("appid")
    private String appid;

    @TableField("token")
    private String token;

    @TableField("publickey")
    private String publickey;

    @TableField("privatekey")
    private String privatekey;

    @TableField("authurl")
    private String authurl;

    @TableField("logo")
    private String logo;
    @TableField("noauthenlogo")
    private String noauthenlogo;

    @TableField("ip")
    private String ip;

    @TableField("sort")
    private Integer sort;

    /**
     * 应用来源（1：pc，2：mobile）
     */
    @TableField("type")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNoauthenlogo() {
        return noauthenlogo;
    }

    public void setNoauthenlogo(String noauthenlogo) {
        this.noauthenlogo = noauthenlogo;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLoginurl() {
        return loginurl;
    }

    public void setLoginurl(String loginurl) {
        this.loginurl = loginurl;
    }

    public String getCancelurl() {
        return cancelurl;
    }

    public void setCancelurl(String cancelurl) {
        this.cancelurl = cancelurl;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    public String getAuthurl() {
        return authurl;
    }

    public void setAuthurl(String authurl) {
        this.authurl = authurl;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flag=" + flag +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", loginurl='" + loginurl + '\'' +
                ", cancelurl='" + cancelurl + '\'' +
                ", createtime=" + createtime +
                ", delflag=" + delflag +
                ", creater='" + creater + '\'' +
                ", appid='" + appid + '\'' +
                ", token='" + token + '\'' +
                ", publickey='" + publickey + '\'' +
                ", privatekey='" + privatekey + '\'' +
                ", authurl='" + authurl + '\'' +
                ", logo='" + logo + '\'' +
                ", ip='" + ip + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
