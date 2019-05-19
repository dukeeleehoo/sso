/**
 * 统一管理的用户和角色关联管理初始化
 */
var UnifiedUserRole = {
    id: "UnifiedUserRoleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UnifiedUserRole.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'userid', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'roleid', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'appid', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UnifiedUserRole.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UnifiedUserRole.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加统一管理的用户和角色关联
 */
UnifiedUserRole.openAddUnifiedUserRole = function () {
    var index = layer.open({
        type: 2,
        title: '添加统一管理的用户和角色关联',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/unifiedUserRole/unifiedUserRole_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看统一管理的用户和角色关联详情
 */
UnifiedUserRole.openUnifiedUserRoleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '统一管理的用户和角色关联详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/unifiedUserRole/unifiedUserRole_update/' + UnifiedUserRole.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除统一管理的用户和角色关联
 */
UnifiedUserRole.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/unifiedUserRole/delete", function (data) {
            Feng.success("删除成功!");
            UnifiedUserRole.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("unifiedUserRoleId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询统一管理的用户和角色关联列表
 */
UnifiedUserRole.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    UnifiedUserRole.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = UnifiedUserRole.initColumn();
    var table = new BSTable(UnifiedUserRole.id, "/unifiedUserRole/list", defaultColunms);
    table.setPaginationType("client");
    UnifiedUserRole.table = table.init();
});
