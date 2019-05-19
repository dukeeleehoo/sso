package cn.future.modular.system.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
@TableName("sys_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 名字
     */
    private String name;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 角色id
     */
    private String roleid;
    /**
     * 部门id
     */
    private Integer deptid;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 保留字段
     */
    private Integer version;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 主岗对应字典表
     */
    private Integer majorposition;
    /**
     * 副岗1
     */
    private String deputypostone;
    /**
     * 副岗2
     */
    private String deputyposttwo;
    /**
     * 职级对应字典表
     */
    private Integer rank;
    /**
     * 应用
     */
    private String applicationids;
    /**
     * 设备类型
     */
    private String equipmenttype;
    /**
     * token(手机登录使用)
     */
    private String token;
    /**
     * mac地址(手机使用)
     */
    private String mac;
    /**
     * 登录时间
     */
    private Date logintime;
    /**
     * 清空token的时间(手机使用)
     */
    private Date deltokentime;
    /**
     * 客户端的唯一标识符
     */
    private String guid;

    private String subsystemadmin;

    @JSONField(serialize = false)
    public String getSubsystemadmin() {
        return subsystemadmin;
    }


    public void setSubsystemadmin(String subsystemadmin) {
        this.subsystemadmin = subsystemadmin;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JSONField(serialize = false)
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JSONField(serialize = false)
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JSONField(serialize = false)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JSONField(serialize = false)
    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @JSONField(serialize = false)
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getMajorposition() {
        return majorposition;
    }

    public void setMajorposition(Integer majorposition) {
        this.majorposition = majorposition;
    }

    public String getDeputypostone() {
        return deputypostone;
    }

    public void setDeputypostone(String deputypostone) {
        this.deputypostone = deputypostone;
    }

    @JSONField(serialize = false)
    public String getDeputyposttwo() {
        return deputyposttwo;
    }

    public void setDeputyposttwo(String deputyposttwo) {
        this.deputyposttwo = deputyposttwo;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getApplicationids() {
        return applicationids;
    }

    public void setApplicationids(String applicationids) {
        this.applicationids = applicationids;
    }

    @JSONField(serialize = false)
    public String getEquipmenttype() {
        return equipmenttype;
    }

    public User setEquipmenttype(String equipmenttype) {
        this.equipmenttype = equipmenttype;
        return this;
    }

    @JSONField(serialize = false)
    public String getGuid() {
        return guid;
    }

    public User setGuid(String guid) {
        this.guid = guid;
        return this;
    }

    @JSONField(serialize = false)
    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    @JSONField(serialize = false)
    public String getMac() {
        return mac;
    }

    public User setMac(String mac) {
        this.mac = mac;
        return this;
    }

    @JSONField(serialize = false)
    public Date getLogintime() {
        return logintime;
    }

    public User setLogintime(Date logintime) {
        this.logintime = logintime;
        return this;
    }

    @JSONField(serialize = false)
    public Date getDeltokentime() {
        return deltokentime;
    }

    public User setDeltokentime(Date deltokentime) {
        this.deltokentime = deltokentime;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", roleid='" + roleid + '\'' +
                ", deptid=" + deptid +
                ", status=" + status +
                ", createtime=" + createtime +
                ", version=" + version +
                ", mobile='" + mobile + '\'' +
                ", majorposition=" + majorposition +
                ", deputypostone='" + deputypostone + '\'' +
                ", deputyposttwo='" + deputyposttwo + '\'' +
                ", rank='" + rank + '\'' +
                ", applicationids='" + applicationids + '\'' +
                ", equipmenttype='" + equipmenttype + '\'' +
                ", token='" + token + '\'' +
                ", mac='" + mac + '\'' +
                ", logintime=" + logintime +
                ", deltokentime=" + deltokentime +
                ", guid=" + guid +
                '}';
    }
}
