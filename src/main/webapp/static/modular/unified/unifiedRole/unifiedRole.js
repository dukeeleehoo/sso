/**
 * 应用角色表管理初始化
 */
var UnifiedRole = {
    id: "UnifiedRoleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UnifiedRole.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '角色名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '子系统标识', field: 'uniquecode', visible: true, align: 'center', valign: 'middle'},
            {title: '序号', field: 'num', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'tips', visible: true, align: 'center', valign: 'middle'}
            // {title: '保留字段(暂时没用）', field: 'version', visible: true, align: 'center', valign: 'middle'},
            // {title: '父角色', field: 'pid', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UnifiedRole.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UnifiedRole.seItem = selected[0];
        return true;
    }
};


/**
 * 权限配置
 */
UnifiedRole.assign = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '权限配置',
            area: ['300px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/unifiedRole/role_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 点击添加应用角色表
 */
UnifiedRole.openAddUnifiedRole = function () {
    var index = layer.open({
        type: 2,
        title: '添加应用角色表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/unifiedRole/unifiedRole_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看应用角色表详情
 */
UnifiedRole.openUnifiedRoleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '应用角色表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/unifiedRole/unifiedRole_update/' + UnifiedRole.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除应用角色表
 */
UnifiedRole.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/unifiedRole/delete", function (data) {
            Feng.success("删除成功!");
            UnifiedRole.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("unifiedRoleId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询应用角色表列表
 */
UnifiedRole.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    UnifiedRole.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = UnifiedRole.initColumn();
    var table = new BSTable(UnifiedRole.id, "/unifiedRole/list", defaultColunms);
    table.setPaginationType("client");
    UnifiedRole.table = table.init();
});
