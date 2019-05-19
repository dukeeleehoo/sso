/**
 * 初始化统一管理的菜单和角色关联详情对话框
 */
var UnifiedRelationInfoDlg = {
    unifiedRelationInfoData : {}
};

/**
 * 清除数据
 */
UnifiedRelationInfoDlg.clearData = function() {
    this.unifiedRelationInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnifiedRelationInfoDlg.set = function(key, val) {
    this.unifiedRelationInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnifiedRelationInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UnifiedRelationInfoDlg.close = function() {
    parent.layer.close(window.parent.UnifiedRelation.layerIndex);
}

/**
 * 收集数据
 */
UnifiedRelationInfoDlg.collectData = function() {
    this
    .set('id')
    .set('menuid')
    .set('roleid');
}

/**
 * 提交添加
 */
UnifiedRelationInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unifiedRelation/add", function(data){
        Feng.success("添加成功!");
        window.parent.UnifiedRelation.table.refresh();
        UnifiedRelationInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unifiedRelationInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UnifiedRelationInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unifiedRelation/update", function(data){
        Feng.success("修改成功!");
        window.parent.UnifiedRelation.table.refresh();
        UnifiedRelationInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unifiedRelationInfoData);
    ajax.start();
}

$(function() {

});
