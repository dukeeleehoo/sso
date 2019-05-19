package cn.future.modular.unified.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 统一菜单和角色关联表
 * </p>
 *
 * @author zp
 * @since 2019-04-18
 */
@TableName("unified_relation")
public class UnifiedRelation extends Model<UnifiedRelation> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 菜单id
     */
    private Long menuid;
    /**
     * 角色id
     */
    private Integer roleid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UnifiedRelation{" +
        ", id=" + id +
        ", menuid=" + menuid +
        ", roleid=" + roleid +
        "}";
    }
}
