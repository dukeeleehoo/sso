package cn.future.sso.oa;


import lombok.Data;

@Data
public class OADaiBan {

    private String id;


    private String subject;

    private Long receiveTime;

    private String senderName;

    private String url;

    private String appid = "c9887b83-8f12-4244-94ff-e2e5bfc7aac3";

    private String receiveDate;

}
