/**
 * 初始化统一管理的用户和角色关联详情对话框
 */
var UserRoleInfoDlg = {
    userRoleInfoData : {}
};

/**
 * 清除数据
 */
UserRoleInfoDlg.clearData = function() {
    this.userRoleInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserRoleInfoDlg.set = function(key, val) {
    this.userRoleInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserRoleInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UserRoleInfoDlg.close = function() {
    parent.layer.close(window.parent.UserRole.layerIndex);
}

/**
 * 收集数据
 */
UserRoleInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userid')
    .set('roleid')
    .set('appid');
}

/**
 * 提交添加
 */
UserRoleInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userRole/add", function(data){
        Feng.success("添加成功!");
        window.parent.UserRole.table.refresh();
        UserRoleInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userRoleInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UserRoleInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userRole/update", function(data){
        Feng.success("修改成功!");
        window.parent.UserRole.table.refresh();
        UserRoleInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userRoleInfoData);
    ajax.start();
}

$(function() {

});
