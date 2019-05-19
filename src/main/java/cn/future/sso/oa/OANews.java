package cn.future.sso.oa;


import lombok.Data;

@Data
public class OANews {

    private String id;

    private String title;

    private String appid = "c9887b83-8f12-4244-94ff-e2e5bfc7aac3";

    private Long createDate;

    private String createDateStr;

    private String url;

}
