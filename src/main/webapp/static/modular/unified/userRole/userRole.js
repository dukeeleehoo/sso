/**
 * 统一管理的用户和角色关联管理初始化
 */
var UserRole = {
    id: "UserRoleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UserRole.initColumn = function () {
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
UserRole.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UserRole.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加统一管理的用户和角色关联
 */
UserRole.openAddUserRole = function () {
    var index = layer.open({
        type: 2,
        title: '添加统一管理的用户和角色关联',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userRole/userRole_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看统一管理的用户和角色关联详情
 */
UserRole.openUserRoleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '统一管理的用户和角色关联详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/userRole/userRole_update/' + UserRole.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除统一管理的用户和角色关联
 */
UserRole.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/userRole/delete", function (data) {
            Feng.success("删除成功!");
            UserRole.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userRoleId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询统一管理的用户和角色关联列表
 */
UserRole.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    UserRole.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = UserRole.initColumn();
    var table = new BSTable(UserRole.id, "/userRole/list", defaultColunms);
    table.setPaginationType("client");
    UserRole.table = table.init();
});
