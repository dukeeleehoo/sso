package cn.future.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 统一菜单表
 * </p>
 *
 * @author lee
 * @since 2019-04-18
 */
@TableName("unified_menu")
public class UnifiedMenu extends Model<UnifiedMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 菜单编号
     */
    private String code;
    /**
     * 菜单父编号
     */
    private String pcode;
    /**
     * 当前菜单的所有父菜单编号
     */
    private String pcodes;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 子系统唯一标识
     */
    private String unqiuecode;
    /**
     * 菜单排序号
     */
    private Integer num;
    /**
     * 菜单层级
     */
    private Integer levels;
    /**
     * 是否是菜单（1：是 0：不是）
     */
    private Integer ismenu;
    /**
     * 备注
     */
    private String tips;
    /**
     * 所属应用
     */
    private String appid;
    /**
     * 是否启用
     *
     */

    private  Integer status;
    /**
     * 菜单url
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPcodes() {
        return pcodes;
    }

    public void setPcodes(String pcodes) {
        this.pcodes = pcodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnqiuecode() {
        return unqiuecode;
    }

    public void setUnqiuecode(String unqiuecode) {
        this.unqiuecode = unqiuecode;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public Integer getIsmenu() {
        return ismenu;
    }

    public void setIsmenu(Integer ismenu) {
        this.ismenu = ismenu;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UnifiedMenu{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", pcode='" + pcode + '\'' +
                ", pcodes='" + pcodes + '\'' +
                ", name='" + name + '\'' +
                ", unqiuecode='" + unqiuecode + '\'' +
                ", num=" + num +
                ", levels=" + levels +
                ", ismenu=" + ismenu +
                ", tips='" + tips + '\'' +
                ", appid='" + appid + '\'' +
                ", status=" + status +
                ", url='" + url + '\'' +
                '}';
    }
}
