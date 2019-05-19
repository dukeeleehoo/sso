/**
 * 初始化应用角色表详情对话框
 */
var UnifiedRoleInfoDlg = {
    unifiedRoleInfoData : {
        num: {
            validators: {
                numeric: {
                    message: '请输入数字'
                },
                stringLength: {
                 min: 1,
                 max: 6,
                message: '长度不能超过六位！'
                }

            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '名称不能为空'
                }
            }
        },
        uniquecode: {
            validators: {
                notEmpty: {
                    message: '标识不能为空'
                }
            }
        }

    }
};

/**
 * 清除数据
 */
UnifiedRoleInfoDlg.clearData = function() {
    this.unifiedRoleInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnifiedRoleInfoDlg.set = function(key, val) {
    this.unifiedRoleInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnifiedRoleInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UnifiedRoleInfoDlg.close = function() {
    parent.layer.close(window.parent.UnifiedRole.layerIndex);
}

/**
 * 验证数据是否为空
 */
UnifiedRoleInfoDlg.validate = function () {
    $('#unroleForm').data("bootstrapValidator").resetForm();
    $('#unroleForm').bootstrapValidator('validate');
    return $("#unroleForm").data('bootstrapValidator').isValid();
}

/**
 * 收集数据
 */
UnifiedRoleInfoDlg.collectData = function() {
    this
    .set('deptid')
    .set('id')
    .set('num')
    .set('name')
    .set('appid')
    .set('tips')
    .set('version')
    .set('uniquecode')
    .set('pid');
}

/**
 * 提交添加
 */
UnifiedRoleInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unifiedRole/add", function(data){
        Feng.success("添加成功!");
        window.parent.UnifiedRole.table.refresh();
        UnifiedRoleInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unifiedRoleInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UnifiedRoleInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unifiedRole/update", function(data){
        Feng.success("修改成功!");
        window.parent.UnifiedRole.table.refresh();
        UnifiedRoleInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unifiedRoleInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("unroleForm", UnifiedRoleInfoDlg.unifiedRoleInfoData);
});
