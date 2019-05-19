package cn.future.modular.unified.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 接口日志
 * </p>
 *
 * @author zp
 * @since 2018-12-14
 */
@TableName("unified_uninterface_log")
public class UninterfaceLog extends Model<UninterfaceLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 关联接口id
     */
    private Integer uninterfaceid;
    /**
     * 关联应用id(调用方)
     */
    private Integer applicationid;
    /**
     * 调用时间
     */
    private Date createtime;
    /**
     * 具体消息
     */
    private String message;
    /**
     * 调用ip
     */
    private String ip;
    /**
     * 响应时间
     */
    private String time;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUninterfaceid() {
        return uninterfaceid;
    }

    public void setUninterfaceid(Integer uninterfaceid) {
        this.uninterfaceid = uninterfaceid;
    }

    public Integer getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(Integer applicationid) {
        this.applicationid = applicationid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UninterfaceLog{" +
        ", id=" + id +
        ", uninterfaceid=" + uninterfaceid +
        ", applicationid=" + applicationid +
        ", createtime=" + createtime +
        ", message=" + message +
        ", ip=" + ip +
        ", time=" + time +
        "}";
    }
}
