package cn.future.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lee
 * @since 2019-04-17
 */
@TableName("unified_role")
public class UnifiedRole extends Model<UnifiedRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 部门
     */
    private Integer deptid;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 序号
     */
    private Integer num;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色所属应用
     */
    private String appid;
    /**
     * 提示
     */
    private String tips;
    /**
     * 保留字段(暂时没用）
     */
    private Integer version;
    /**
     * 父角色
     */
    private Integer pid;



    private String uniquecode;

    public String getUniquecode() {
        return uniquecode;
    }

    public void setUniquecode(String uniquecode) {
        this.uniquecode = uniquecode;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UnifiedRole{" +
                "deptid=" + deptid +
                ", id=" + id +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", appid='" + appid + '\'' +
                ", tips='" + tips + '\'' +
                ", version=" + version +
                ", pid=" + pid +
                ", uniquecode='" + uniquecode + '\'' +
                '}';
    }
}
