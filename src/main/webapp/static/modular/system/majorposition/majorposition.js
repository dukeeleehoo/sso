/**
 * 岗位管理初始化
 */
var Majorposition = {
    id: "MajorpositionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Majorposition.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '岗位名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '编码', field: 'code', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'num', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
Majorposition.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Majorposition.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加岗位
 */
Majorposition.openAddMajorposition = function () {
    var index = layer.open({
        type: 2,
        title: '添加岗位',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/majorposition/majorposition_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看岗位详情
 */
Majorposition.openMajorpositionDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '岗位详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/majorposition/majorposition_update/' + Majorposition.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除岗位
 */
Majorposition.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/majorposition/delete", function (data) {
            Feng.success("删除成功!");
            Majorposition.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("majorpositionId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询岗位列表
 */
Majorposition.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Majorposition.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Majorposition.initColumn();
    var table = new BSTable(Majorposition.id, "/majorposition/list", defaultColunms);
    table.setPaginationType("client");
    Majorposition.table = table.init();
});
