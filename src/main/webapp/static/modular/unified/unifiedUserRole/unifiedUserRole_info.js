/**
 * 初始化统一管理的用户和角色关联详情对话框
 */
var UnifiedUserRoleInfoDlg = {
    unifiedUserRoleInfoData : {}
};

/**
 * 清除数据
 */
UnifiedUserRoleInfoDlg.clearData = function() {
    this.unifiedUserRoleInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnifiedUserRoleInfoDlg.set = function(key, val) {
    this.unifiedUserRoleInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnifiedUserRoleInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UnifiedUserRoleInfoDlg.close = function() {
    parent.layer.close(window.parent.UnifiedUserRole.layerIndex);
}

/**
 * 收集数据
 */
UnifiedUserRoleInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userid')
    .set('roleid')
    .set('appid');
}

/**
 * 提交添加
 */
UnifiedUserRoleInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unifiedUserRole/add", function(data){
        Feng.success("添加成功!");
        window.parent.UnifiedUserRole.table.refresh();
        UnifiedUserRoleInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unifiedUserRoleInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UnifiedUserRoleInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unifiedUserRole/update", function(data){
        Feng.success("修改成功!");
        window.parent.UnifiedUserRole.table.refresh();
        UnifiedUserRoleInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unifiedUserRoleInfoData);
    ajax.start();
}

$(function() {

});
