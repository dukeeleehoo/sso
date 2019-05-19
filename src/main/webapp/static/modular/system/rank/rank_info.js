/**
 * 初始化职务详情对话框
 */
var RankInfoDlg = {
    rankInfoData : {}
};

/**
 * 清除数据
 */
RankInfoDlg.clearData = function() {
    this.rankInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RankInfoDlg.set = function(key, val) {
    this.rankInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RankInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
RankInfoDlg.close = function() {
    parent.layer.close(window.parent.Rank.layerIndex);
}

/**
 * 收集数据
 */
RankInfoDlg.collectData = function() {
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
RankInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rank/add", function(data){
        Feng.success("添加成功!");
        window.parent.Rank.table.refresh();
        RankInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.rankInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
RankInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rank/update", function(data){
        Feng.success("修改成功!");
        window.parent.Rank.table.refresh();
        RankInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.rankInfoData);
    ajax.start();
}

$(function() {

});
