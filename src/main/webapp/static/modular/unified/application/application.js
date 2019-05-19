/**
 * 接入应用管理初始化
 */
var Application = {
    id: "ApplicationTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Application.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '类别', field: 'typeName', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'flagname', visible: true, align: 'center', valign: 'middle'},
        {title: '访问路径', field: 'url', visible: true, align: 'center', valign: 'middle'},
        {title: '登录路径', field: 'loginurl', visible: false, align: 'center', valign: 'middle'},
        {title: '认证路径', field: 'authurl', visible: true, align: 'center', valign: 'middle'},
        {title: '排序', field: 'sort', visible: true, align: 'center', valign: 'middle'},
        {title: 'appid', field: 'appid', visible: true, align: 'center', valign: 'middle'},
        {title: 'token', field: 'token', visible: true, align: 'center', valign: 'middle'},
        {title: '服务器ip', field: 'ip', visible: true, align: 'center', valign: 'middle'}
        // {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Application.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Application.seItem = selected[0];
        return true;
    }
};



/**
 * 点击添加接入应用
 */
Application.openAddApplication = function () {
    var index = layer.open({
        type: 2,
        title: '添加接入应用',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/application/application_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看接入应用详情
 */
Application.openApplicationDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '接入应用详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/application/application_update/' + Application.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 下载公钥
 */
Application.uploadkey = function () {
    if (this.check()) {
        window.location = Feng.ctxPath + "/application/uploadkey?id=" + Application.seItem.id;
    }
 };


/**
 * 删除接入应用
 */
Application.delete = function () {
    if (this.check()) {

        var operation = function() {
            var ajax = new $ax(Feng.ctxPath + "/application/delete", function (data) {
                Feng.success("删除成功!");
                Application.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("applicationId", Application.seItem.id);
            ajax.start();
        }
        Feng.confirm("是否删除" + Application.seItem.name + "应用?",operation);
    }
};

/**
 * 启用应用
 */
Application.freezeApplication = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/application/freeze", function (data) {
            Feng.success("启用成功!");
            Application.table.refresh();
        }, function (data) {
            Feng.error("启用失败!" + data.responseJSON.message + "!");
        });
        ajax.set("applicationId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 停用应用
 */
Application.unfreezeApplication = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/application/unfreeze", function (data) {
            Feng.success("停用成功!");
            Application.table.refresh();
        }, function (data) {
            Feng.error("停用失败!");
        });
        ajax.set("applicationId", this.seItem.id);
        ajax.start();
    }
}

/**
 * 查询接入应用列表
 */
Application.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Application.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Application.initColumn();
    var table = new BSTable(Application.id, "/application/list", defaultColunms);
    table.setPaginationType("client");
    Application.table = table.init();
});
