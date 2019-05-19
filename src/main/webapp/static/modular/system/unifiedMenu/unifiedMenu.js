/**
 * 统一用户菜单表管理初始化
 */
var UnifiedMenu = {
    id: "UnifiedMenuTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UnifiedMenu.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '编号', field: 'code', visible: true, align: 'center', valign: 'middle'},
        {title: '父编号', field: 'pcode', visible: true, align: 'center', valign: 'middle'},
        {title: '子系统唯一标识', field: 'unqiuecode', visible: true, align: 'center', valign: 'middle'},
        {title: 'url', field: 'url', visible: true, align: 'center', valign: 'middle'},
        // {title: '当前菜单的所有父菜单编号', field: 'pcodes', visible: true, align: 'center', valign: 'middle'},
            {title: '排序号', field: 'num', visible: true, align: 'center', valign: 'middle'},
            {title: '层级', field: 'levels', visible: true, align: 'center', valign: 'middle'},
            {title: '是否是菜单', field: 'isMenuName', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'tips', visible: true, align: 'center', valign: 'middle'},
            // {title: '所属应用', field: 'appid', visible: true, align: 'center', valign: 'middle'},
            // {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UnifiedMenu.check = function () {
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UnifiedMenu.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加统一用户菜单表
 */
UnifiedMenu.openAddUnifiedMenu = function () {
    var index = layer.open({
        type: 2,
        title: '添加统一用户菜单表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/unifiedMenu/unifiedMenu_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看统一用户菜单表详情
 */
UnifiedMenu.openUnifiedMenuDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '统一用户菜单表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/unifiedMenu/unifiedMenu_update/' + UnifiedMenu.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除统一用户菜单表
 */
UnifiedMenu.delete = function () {
    if (this.check()) {
        var op = function () {

            var ajax = new $ax(Feng.ctxPath + "/unifiedMenu/remove", function (data) {
                Feng.success("删除成功!");
                UnifiedMenu.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("unifiedMenuId",UnifiedMenu.seItem.id);
            ajax.start();
        }



        Feng.confirm("是否删除菜单, " + UnifiedMenu.seItem.name + "?",op);
    }

};

/**
 * 查询统一用户菜单表列表
 */
UnifiedMenu.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    UnifiedMenu.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = UnifiedMenu.initColumn();
    var table = new BSTreeTable(UnifiedMenu.id, "/unifiedMenu/list", defaultColunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("code");
    table.setParentCodeField("pcode");
    table.setExpandAll(true);
    table.init();
    UnifiedMenu.table = table;


});
