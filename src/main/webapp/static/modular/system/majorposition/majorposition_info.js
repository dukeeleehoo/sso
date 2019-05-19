/**
 * 初始化岗位详情对话框
 */
var MajorpositionInfoDlg = {
    majorpositionInfoData : {}
};

/**
 * 清除数据
 */
MajorpositionInfoDlg.clearData = function() {
    this.majorpositionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MajorpositionInfoDlg.set = function(key, val) {
    this.majorpositionInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MajorpositionInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MajorpositionInfoDlg.close = function() {
    parent.layer.close(window.parent.Majorposition.layerIndex);
}

/**
 * 收集数据
 */
MajorpositionInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('code')
    .set('num')
    .set('status');
}

/**
 * 提交添加
 */
MajorpositionInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/majorposition/add", function(data){
        Feng.success("添加成功!");
        window.parent.Majorposition.table.refresh();
        MajorpositionInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.majorpositionInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MajorpositionInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/majorposition/update", function(data){
        Feng.success("修改成功!");
        window.parent.Majorposition.table.refresh();
        MajorpositionInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.majorpositionInfoData);
    ajax.start();
}

$(function() {

});
