/**
 * 接口日志管理初始化
 */
var UninterfaceLog = {
    id: "UninterfaceLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UninterfaceLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '接口名称', field: 'uninterfacename', visible: true, align: 'center', valign: 'middle'},
            {title: '调用应用', field: 'applicationname', visible: true, align: 'center', valign: 'middle'},
            {title: '调用时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
            {title: '调用ip', field: 'ip', visible: true, align: 'center', valign: 'middle'},
            {title: '响应时间', field: 'time', visible: true, align: 'center', valign: 'middle'},
            {title: '具体消息', field: 'message', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UninterfaceLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UninterfaceLog.seItem = selected[0];
        return true;
    }
};

/**
 * 查看日志详情
 */
UninterfaceLog.detail = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/uninterfaceLog/detail/" + this.seItem.id, function (data) {
            Feng.infoDetail("日志详情", data.message);
        }, function (data) {
            Feng.error("获取详情失败!");
        });
        ajax.start();
    }
};

/**
 * 清空日志
 */
UninterfaceLog.delLog = function () {
    Feng.confirm("是否清空所有日志?",function(){
        var ajax = Feng.baseAjax("/uninterfaceLog/delUninterfaceLog","清空日志");
        ajax.start();
        UninterfaceLog.table.refresh();
    });
}


/**
 * 查询接口日志列表
 */
UninterfaceLog.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    UninterfaceLog.table.refresh({query: queryData});
};


$(function () {
    var defaultColunms = UninterfaceLog.initColumn();
    var table = new BSTable(UninterfaceLog.id, "/uninterfaceLog/list", defaultColunms);
    table.setPaginationType("server");
    UninterfaceLog.table = table.init();
});
