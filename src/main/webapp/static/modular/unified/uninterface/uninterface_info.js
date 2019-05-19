/**
 * 初始化接口管理详情对话框
 */
var UninterfaceInfoDlg = {
    uninterfaceInfoData : {}
};

/**
 * 清除数据
 */
UninterfaceInfoDlg.clearData = function() {
    this.uninterfaceInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UninterfaceInfoDlg.set = function(key, val) {
    this.uninterfaceInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UninterfaceInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UninterfaceInfoDlg.close = function() {
    parent.layer.close(window.parent.Uninterface.layerIndex);
}

/**
 * 收集数据
 */
UninterfaceInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('url')
    .set('type')
    .set('requestparameter')
    .set('returnparameter')
    .set('status')
    .set('briefing')
    .set('createtime')
    .set('creater');
}

/**
 * 提交添加
 */
UninterfaceInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/uninterface/add", function(data){
        Feng.success("添加成功!");
        window.parent.Uninterface.table.refresh();
        UninterfaceInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.uninterfaceInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UninterfaceInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/uninterface/update", function(data){
        Feng.success("修改成功!");
        window.parent.Uninterface.table.refresh();
        UninterfaceInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.uninterfaceInfoData);
    ajax.start();
}

$(function() {
    $("#type").val($("#typeValue").val());
    $("#status").val($("#statusValue").val());
});
