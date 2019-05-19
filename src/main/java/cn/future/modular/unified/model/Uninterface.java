package cn.future.modular.unified.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 接口管理
 * </p>
 *
 * @author zp
 * @since 2018-12-10
 */
@TableName("unified_uninterface")
public class Uninterface extends Model<Uninterface> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 接口名称
     */
    private String name;
    /**
     * 接口请求url
     */
    private String url;
    /**
     * 接口类型（1：普通，2：加密）
     */
    private Integer type;
    /**
     * 简介
     */
    private String briefing;
    /**
     * 请求参数
     */
    private String requestparameter;
    /**
     * 返回结果
     */
    private String returnparameter;
    /**
     * 接口状态（1：正常，2：停用，3：删除）
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 创建人
     */
    private String creater;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBriefing() {
        return briefing;
    }

    public void setBriefing(String briefing) {
        this.briefing = briefing;
    }

    public String getRequestparameter() {
        return requestparameter;
    }

    public void setRequestparameter(String requestparameter) {
        this.requestparameter = requestparameter;
    }

    public String getReturnparameter() {
        return returnparameter;
    }

    public void setReturnparameter(String returnparameter) {
        this.returnparameter = returnparameter;
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Override
    public String toString() {
        return "Uninterface{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", briefing='" + briefing + '\'' +
                ", requestparameter='" + requestparameter + '\'' +
                ", returnparameter='" + returnparameter + '\'' +
                ", status=" + status +
                ", createtime=" + createtime +
                ", creater='" + creater + '\'' +
                '}';
    }
}
