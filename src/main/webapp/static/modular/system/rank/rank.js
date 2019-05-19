/**
 * 职务管理初始化
 */
var Rank = {
    id: "RankTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Rank.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '职级名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '编码', field: 'code', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'num', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
Rank.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Rank.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加职务
 */
Rank.openAddRank = function () {
    var index = layer.open({
        type: 2,
        title: '添加职务',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/rank/rank_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看职务详情
 */
Rank.openRankDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '职务详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/rank/rank_update/' + Rank.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除职务
 */
Rank.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/rank/delete", function (data) {
            Feng.success("删除成功!");
            Rank.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("rankId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询职务列表
 */
Rank.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Rank.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Rank.initColumn();
    var table = new BSTable(Rank.id, "/rank/list", defaultColunms);
    table.setPaginationType("client");
    Rank.table = table.init();
});
