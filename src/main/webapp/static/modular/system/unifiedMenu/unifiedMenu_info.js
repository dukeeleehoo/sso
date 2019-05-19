/**
 * 初始化统一用户菜单表详情对话框
 */
var UnifiedMenuInfoDlg = {
    unifiedMenuInfoData : {
        name: {
            validators: {
                notEmpty: {
                    message: '菜单名称不能为空'
                }
            }
        },
        code: {
            validators: {
                notEmpty: {
                    message: '菜单编号不能为空'
                }
            }
        },
        pcodeName: {
            validators: {
                notEmpty: {
                    message: '父菜单不能为空'
                }
            }
        },
        url: {
            validators: {
                regexp: {
                    regexp: /^((ht|f)tps?):\/\/([\w\-]+(\.[\w\-]+)*\/)*[\w\-]+(\.[\w\-]+)*\/?(\?([\w\-\.,@?^=%&:\/~\+#]*)+)?/,
                    message: '请输入正确得url'
                }
            }
        },
        num: {
            validators: {
                notEmpty: {
                    message: '序号不能为空'
                },
                numeric: {
                    message: '请输入数字'
                },
                stringLength:{
                    min: 1,
                    max: 6,
                    message: '长度不能超过六位！'
                }
            }
        },
        unqiuecode: {
            validators: {
                notEmpty: {
                    message: '子应用标识不能为空'
                }
            }
        }

    }
};

/**
 * 清除数据
 */
UnifiedMenuInfoDlg.clearData = function() {
    this.unifiedMenuInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnifiedMenuInfoDlg.set = function(key, val) {
    this.unifiedMenuInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnifiedMenuInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UnifiedMenuInfoDlg.close = function() {
    parent.layer.close(window.parent.UnifiedMenu.layerIndex);
}

/**
 * 收集数据
 */
UnifiedMenuInfoDlg.collectData = function() {
    this
    .set('id')
    .set('code')
    .set('pcode')
    .set('pcodes')
    .set('name')
    .set('unqiuecode')
    .set('num')
    .set('levels')
    .set('ismenu')
    .set('tips')
    .set('url')
    .set('appid');
}



/**
 * 显示父级菜单选择的树
 */
UnifiedMenuInfoDlg.showMenuSelectTree = function () {
    Feng.showInputTree("pcodeName", "pcodeTreeDiv", 15, 34);
};


/**
 * 验证数据是否为空
 */
UnifiedMenuInfoDlg.validate = function () {
    $('#unmenuInfoForm').data("bootstrapValidator").resetForm();
    $('#unmenuInfoForm').bootstrapValidator('validate');
    return $("#unmenuInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加
 */
UnifiedMenuInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unifiedMenu/add", function(data){
        Feng.success("添加成功!");
        window.parent.UnifiedMenu.table.refresh();
        UnifiedMenuInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unifiedMenuInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UnifiedMenuInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unifiedMenu/edit", function(data){
        Feng.success("修改成功!");
        window.parent.UnifiedMenu.table.refresh();
        UnifiedMenuInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unifiedMenuInfoData);
    ajax.start();
}



/**
 * 点击父级编号input框时
 */
UnifiedMenuInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#pcodeName").attr("value", UnifiedMenuInfoDlg.ztreeInstance.getSelectedVal());
    $("#pcode").attr("value", treeNode.id);
};


$(function() {

    Feng.initValidator("unmenuInfoForm", UnifiedMenuInfoDlg.unifiedMenuInfoData);

    var ztree = new $ZTree("pcodeTree", "/unifiedMenu/selectMenuTreeList");
    ztree.bindOnClick(UnifiedMenuInfoDlg.onClickDept);
    ztree.init();
    UnifiedMenuInfoDlg.ztreeInstance = ztree;

    //初始化是否是菜单
    if($("#ismenuValue").val() == undefined){
        $("#ismenu").val(0);
    }else{
        $("#ismenu").val($("#ismenuValue").val());
    }

});
