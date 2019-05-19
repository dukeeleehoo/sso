 /**
 * 接口管理管理初始化
 */
var Uninterface = {
    id: "UninterfaceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Uninterface.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '请求地址', field: 'url', visible: true, align: 'center', valign: 'middle'},
            {title: '请求参数', field: 'requestparameter', visible: false, align: 'center', valign: 'middle'},
            {title: '类型', field: 'typename', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'statusname', visible: true, align: 'center', valign: 'middle'},
            {title: '返回结果', field: 'returnparameter', visible: false, align: 'center', valign: 'middle'},
            {title: '简介', field: 'briefing', visible: false, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Uninterface.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Uninterface.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加接口管理
 */
Uninterface.openAddUninterface = function () {
    var index = layer.open({
        type: 2,
        title: '添加接口管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/uninterface/uninterface_add'
    });
    this.layerIndex = index;
};

 /**
  * 查看接口返回详情
  */
 Uninterface.detail = function () {
     if (this.check()) {
         var ajax = new $ax(Feng.ctxPath + "/uninterface/detail/" + this.seItem.id, function (data) {
             Feng.infoDetail("接口返回结果", data.returnparameter);
         }, function (data) {
             Feng.error("获取接口返回结果失败!");
         });
         ajax.start();
     }
 };

/**
 * 打开查看接口管理详情
 */
Uninterface.openUninterfaceDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '接口管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/uninterface/uninterface_update/' + Uninterface.seItem.id
        });
        this.layerIndex = index;
    }
};

 /**
  * 启用接口
  */
 Uninterface.freezeUninterface = function () {
     if (this.check()) {
         var ajax = new $ax(Feng.ctxPath + "/uninterface/freeze", function (data) {
             Feng.success("启用成功!");
             Uninterface.table.refresh();
         }, function (data) {
             Feng.error("启用失败!" + data.responseJSON.message + "!");
         });
         ajax.set("uninterfaceId", this.seItem.id);
         ajax.start();
     }
 };

 /**
  * 停用接口
  */
 Uninterface.unfreezeUninterface = function () {
     if (this.check()) {
         var ajax = new $ax(Feng.ctxPath + "/uninterface/unfreeze", function (data) {
             Feng.success("停用成功!");
             Uninterface.table.refresh();
         }, function (data) {
             Feng.error("停用失败!");
         });
         ajax.set("uninterfaceId", this.seItem.id);
         ajax.start();
     }
 }

/**
 * 删除接口管理
 */
Uninterface.delete = function () {
    if (this.check()) {

        var operation = function() {
            var ajax = new $ax(Feng.ctxPath + "/uninterface/delete", function (data) {
                Feng.success("删除成功!");
                Uninterface.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("uninterfaceId", Uninterface.seItem.id);
            ajax.start();
        }

        Feng.confirm("是否删除" + Uninterface.seItem.name + "接口?",operation);
    }
};

/**
 * 查询接口管理列表
 */
Uninterface.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Uninterface.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Uninterface.initColumn();
    var table = new BSTable(Uninterface.id, "/uninterface/list", defaultColunms);
    table.setPaginationType("client");
    Uninterface.table = table.init();
});
