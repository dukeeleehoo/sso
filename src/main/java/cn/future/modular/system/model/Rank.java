package cn.future.modular.system.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 职级
 * </p>
 *
 * @author zp
 * @since 2019-04-21
 */
@TableName("sys_rank")
public class Rank extends Model<Rank> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 职级名
     */
    private String name;
    /**
     * 唯一编码
     */
    private String code;
    /**
     * 排序
     */
    private Integer num;
    /**
     * 状态（1：正常，2：删除）
     */
    private Integer status;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @JSONField(serialize = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Rank{" +
        ", id=" + id +
        ", name=" + name +
        ", code=" + code +
        ", num=" + num +
        ", status=" + status +
        "}";
    }
}
