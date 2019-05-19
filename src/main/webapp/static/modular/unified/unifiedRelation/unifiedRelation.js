/**
 * 统一管理的菜单和角色关联管理初始化
 */
var UnifiedRelation = {
    id: "UnifiedRelationTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UnifiedRelation.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '菜单id', field: 'menuid', visible: true, align: 'center', valign: 'middle'},
            {title: '角色id', field: 'roleid', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UnifiedRelation.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UnifiedRelation.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加统一管理的菜单和角色关联
 */
UnifiedRelation.openAddUnifiedRelation = function () {
    var index = layer.open({
        type: 2,
        title: '添加统一管理的菜单和角色关联',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/unifiedRelation/unifiedRelation_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看统一管理的菜单和角色关联详情
 */
UnifiedRelation.openUnifiedRelationDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '统一管理的菜单和角色关联详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/unifiedRelation/unifiedRelation_update/' + UnifiedRelation.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除统一管理的菜单和角色关联
 */
UnifiedRelation.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/unifiedRelation/delete", function (data) {
            Feng.success("删除成功!");
            UnifiedRelation.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("unifiedRelationId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询统一管理的菜单和角色关联列表
 */
UnifiedRelation.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    UnifiedRelation.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = UnifiedRelation.initColumn();
    var table = new BSTable(UnifiedRelation.id, "/unifiedRelation/list", defaultColunms);
    table.setPaginationType("client");
    UnifiedRelation.table = table.init();
});
