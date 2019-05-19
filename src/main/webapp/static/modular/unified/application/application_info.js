/**
 * 初始化接入应用详情对话框
 */
var ApplicationInfoDlg = {
    applicationInfoData : {}
};

/**
 * 清除数据
 */
ApplicationInfoDlg.clearData = function() {
    this.applicationInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ApplicationInfoDlg.set = function(key, val) {
    this.applicationInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ApplicationInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ApplicationInfoDlg.close = function() {
    parent.layer.close(window.parent.Application.layerIndex);
}

/**
 * 收集数据
 */
ApplicationInfoDlg.collectData = function() {
    this
        .set('id')
        .set('name')
        .set('flag')
        .set('url')
        .set('loginurl')
        .set("authurl")
        .set('cancelurl')
        .set('appid')
        .set('token')
        .set('ip')
        .set('logo')
        .set('type')
        .set('noauthenlogo')
        .set('sort');
}

/**
 * 提交添加
 */
ApplicationInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/application/add", function(data){
        Feng.success("添加成功!");
        window.parent.Application.table.refresh();
        ApplicationInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.applicationInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ApplicationInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/application/update", function(data){
        Feng.success("修改成功!");
        window.parent.Application.table.refresh();
        ApplicationInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.applicationInfoData);
    ajax.start();
}

$(function() {
    //初始化状态选项
    $("#flag").val($("#flagValue").val());
    $("#type").val($("#typeValue").val());
    // 初始化头像上传
    var avatarUp = new $WebUpload("logo");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();

    var noauthenlogo = new $WebUpload("noauthenlogo");
    noauthenlogo.setUploadBarId("progressBar");
    noauthenlogo.init();
});
