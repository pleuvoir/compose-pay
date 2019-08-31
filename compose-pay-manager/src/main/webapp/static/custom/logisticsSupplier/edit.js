
var logistics = window.logistics || {};

logistics.paramIndex = 200;
logistics.toolIndex = 200;

/**
 * option.paramIndex
 * option.toolIndex
 */
logistics.edit = function(option){
    if(option){
        if(option.paramIndex && !isNaN(option.paramIndex)){
            logistics.paramIndex = option.paramIndex;
        }
        if(option.toolIndex && !isNaN(option.toolIndex)){
            logistics.toolIndex = option.toolIndex
        }
    }
    logistics.initParamTable();
    logistics.initToolAddBtn();
    logistics.bindSubmit();
}


//初始化参数table
logistics.initParamTable = function(){
    var _table = $('#param-table');
    //添加按钮
    _table.find('.add').on('click', function(){
        logistics.addParamTr(_table);
    });
    _table.find('.remove').on('click', function(){
       $(this).closest('tr').remove();
    });
}

//参数table中添加按钮生成tr
logistics.addParamTr = function($table){
    var _tr = template('show-param-tr-tpl', {});
    _tr = $(_tr);
    _tr.find('.remove').click(function(){
        var _rmTr = $(this).closest('tr');
        logistics.removeParamValidator(_rmTr);
        _rmTr.remove();
    });
    //生成name
    logistics.paramIndex++;
    _tr.find('.param-code').attr('name', 'paramMap['+ logistics.paramIndex +'].code');
    _tr.find('.param-value').attr('name', 'paramMap['+ logistics.paramIndex +'].value');

    $table.find('tbody').append(_tr);
    //增加校验
    logistics.addParamValidator(_tr);
}

//为生成的参数tr增加校验
logistics.addParamValidator = function($tr){
    var icon = "<i class='fa fa-times-circle'></i> ";
    $tr.each(function(i,n){
        $(n).find('input.param-code').rules("add",{required:true, messages:{required: icon+"请输入参数编号"}});
        $(n).find('input.param-value').rules("add",{required:true, messages:{required: icon+"请输入参数值"}});
    });
}

//为生成的参数tr删除校验
logistics.removeParamValidator = function($tr){
    $tr.each(function(i,n){
        $(n).find('.param-code').rules('remove');
        $(n).find('.param-value').rules('remove');
    });
}

//绑定物流工具添加按钮
logistics.initToolAddBtn = function(){
    var _table = $('#tool-table');
    _table.find('#tool-add').on('click', function(){
        logistics.addToolTr();
    });
    _table.find('.remove').on('click', function(){
        $(this).closest('tr').remove();
    });
}

//物流工具table中添加按钮生成tr
logistics.addToolTr = function(){
    var _tr = template('show-tool-tr-tpl', {});
    _tr = $(_tr);
    _tr.find('.remove').click(function(){
        var _rmTr = $(this).closest('tr');
        //删除校验
        logistics.removeToolValidator(_rmTr);
        _rmTr.remove();
    });
    //生成name
    logistics.toolIndex++;
    _tr.find('.tool-code').attr('name', 'tagMap['+ logistics.toolIndex +'].logisticsCompanyCode');
    _tr.find('.express-code').attr('name', 'tagMap['+ logistics.toolIndex +'].expressCode');

    $('#tool-table').find('tbody').append(_tr);
    //增加校验
    logistics.addToolValidator(_tr);
}

//为生成的物流工具tr增加校验
logistics.addToolValidator = function($tr){
    var icon = "<i class='fa fa-times-circle'></i> ";
    $tr.find('.tool-code').rules("add",{required:true, messages:{required: icon+"请输入参数编号"}});
    $tr.find('.express-code').rules("add",{required:true, messages:{required: icon+"请输入参数值"}});
}

//为生成的参数tr删除校验
logistics.removeToolValidator = function($tr){
    $tr.find('.tool-code').rules('remove');
    $tr.find('.express-code').rules('remove');
}

//提交按钮
logistics.bindSubmit = function(){

    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#edit-form").validate({
        rules: {
            "name":{
                required:true,
                maxlength: 30,
            },
            "url":{
                required:true,
            },
        },
        messages: {
            "name": {
                required:icon+'请输入物流合作商名称',
                maxlength: icon+'物流合作商最大30个字符',
            },
            "url": {
                required:icon+'请输入请求地址',
            },
        }
    });
    //参数校验规格
    logistics.initParamValidator();
    //物流工具校验规则
    logistics.initToolValidator();

    $('#form-submit').on('click', function(){
        if(logistics.checkParam() && logistics.checkTool()){
            $('#edit-form').submit();
        }
    });
}

//初始化参数校验
logistics.initParamValidator = function(){
    var _table = $('#param-table');

    var icon = "<i class='fa fa-times-circle'></i> ";
    _table.find('tbody tr').each(function(i,n){
        $(n).find('.param-code').rules("add",{required:true, messages:{required: icon+"请输入参数编号"}});
        $(n).find('.param-value').rules("add",{required:true, messages:{required: icon+"请输入参数值"}});
    });
}

//初始化物流工具校验
logistics.initToolValidator = function(){
    var _table = $('#tool-table');

    var icon = "<i class='fa fa-times-circle'></i> ";
    _table.find('tbody tr').each(function(i,n){
        $(n).find('.tool-code').rules("add",{required:true, messages:{required: icon+"请选择物流工具"}});
        $(n).find('.express-code').rules("add",{required:true, messages:{required: icon+"请输入物流公司编号"}});
    });
}

//校验参数
logistics.checkParam = function(){
    var _paramContent = $('#param-table');

    var paramCodeRepeated = false;
    var paramCodeArray = [];
    _paramContent.find('.param-code').each(function(i,n){
        if(!paramCodeRepeated){
            if(paramCodeArray.indexOf(n.value) >= 0){
                paramCodeRepeated = true;
                layer.msg('参数编号重复：' + n.value);
            }else{
                paramCodeArray.push(n.value);
            }
        }
    });
    return !paramCodeRepeated;
}

//校验物流工具
logistics.checkTool = function(){
    var _toolTable = $('#tool-table');
    var toolCodeRepeated = false;
    var toolExpressCodeRepeated = false;

    var toolCodeArray = [];
    _toolTable.find('.tool-code').each(function(i,n){
        if(!toolCodeRepeated){
            var toolCode = $(n).val();
            if(toolCodeArray.indexOf(toolCode) >= 0){
                toolCodeRepeated = true;
                layer.msg('物流工具重复：' + $(n).find('option:selected').text());
            }else{
                toolCodeArray.push(toolCode);
            }
        }
    });
    var toolExpressCodeArray = [];
    _toolTable.find('.express-code').each(function(i,n){
        if(!toolExpressCodeRepeated){
            var expressCode = n.value;
            if(!expressCode){
                layer.msg('快递公司编号不能为空');
                return;
            }
            if(toolExpressCodeArray.indexOf(expressCode) >= 0){
                toolExpressCodeRepeated = true;
                layer.msg('快递公司编号重复：' + expressCode);
            }else{
                toolExpressCodeArray.push(expressCode);
            }
        }
    });
    return !toolCodeRepeated && !toolExpressCodeRepeated;
}
