package cn.future.sso;


import lombok.Data;

/**
 * gis代办
 */

@Data
public class GISDaiBanEntity {

    //违法主体
    private String WFZT;
    //主体分类
    private String ZTFL;
    //所属乡镇编码
    private String SSXZ;
    //所属乡镇
    private String AD_NM;
    //违法用地用途
    private String WFYT;
    //违法类型
    private String WFLX;
    //违法性质
    private String WFXZ;
    //是否结案
    private String ISOVER;

    private String WFBM;
    //每一条连接拼装
    private String url;

    private String appid;


}
