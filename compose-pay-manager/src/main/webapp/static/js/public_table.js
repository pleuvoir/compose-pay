$.jgrid.defaults.styleUI = 'Bootstrap';

/**
 * 封装操作jqGrid
 * @param controlOpt	初始化元素
 * @returns
 */
function JqGridControl(controlOpt){
    var _this = this;


    var selector = controlOpt.selector;			//tableId

    //是否启用缓存
    var _useParamsCache = false;

    var _controlOpt = {
        alertTagSelector: '.msg-alert-tag',
        form: '',								//查询条件表单Id
        url: '',								//查询url
        button: '',								//查询按钮Id
        pager: '',								//分页工具栏Id
        formResetButton: ''						//清空查询条件按钮
    };

    $.extend(_controlOpt, controlOpt);

    //设置缓存参数
    //开启缓存须引入localStorageUtil.js
    var _paramsCacheOpts = {
        // cacheId: '',							//全局唯一缓存id(废除)
        autoQuery: true							//自动查询
    };

    var cacheId = window.document.location.pathname;

    /**
     * 缓存参数
     * autoQuery 自动查询
     * @param opts
     */
    this.useParamsCache = function(opts){
        $.extend(_paramsCacheOpts, opts);
        _useParamsCache = true;
    };

    /**
     * 初始化jqGrid
     * 参数：
     * options: jqGrid的参数，通常只用设置colNames和colModel属性即可
     */
    this.init = function(options){
        var params = {
            url: '',
            datatype: 'local',
            height: 400,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 20,
            rowList: [10, 20, 30],
            pagerpos: 'right',
            recordpos: 'left',
            viewrecords: true,
            hidegrid: false,
            sortable: false,
            rownumbers: true,
            rownumWidth: 50,
            loadui: 'enable',
            loadtext: '<div class="sk-spinner sk-spinner-three-bounce">'
            + '<div class="sk-bounce1"></div>'
            + '<div class="sk-bounce2"></div>'
            + '<div class="sk-bounce3"></div>'
            + '</div>',
            loadError: function(xhr,status,error){
                swal("出错了！", error, "error");
            }
        };

        //如果开启缓存，初始化时给查询条件input,select赋值，并查询
        if(_useParamsCache){
            var _params = this.formParamsCache();
            if(_paramsCacheOpts.autoQuery){
                if(_params && !$.isEmptyObject(_params)){
                    $.extend(params, {
                        url: _controlOpt.url,
                        datatype: 'json',
                        postData: _params,
                        page: 1
                    });
                }
            }

        }

        $.extend(params, options);

        //翻页工具栏
        $.extend(params, {
            pager: _controlOpt.pager
        });

        $(selector).jqGrid(params);

        // Add responsive to jqGrid
        $(window).bind('resize', function () {
            var width = $(selector).parent().width();
            $(selector).setGridWidth(width);
        });

        $(selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "auto" });
    };

    /**
     * 绑定查询按钮功能
     */
    this.bindQuery = function(callback){
        //查询按钮
        $(_controlOpt.button).bind("click", function(){
            //参数本地缓存
            if(_useParamsCache){
                setLocalCache(_controlOpt.form, cacheId);
            }
            var formParams = formToObject(_controlOpt.form);
            var params = {
                datatype: 'json',
                postData: formParams,
                page: 1
            };
            $.extend(params, _controlOpt);
            $(selector).jqGrid('setGridParam', params).trigger('reloadGrid');
            _this.removeAlertTag();
            
            if(callback && $.isFunction(callback)){
            	callback.call(_this, formParams);
            }
        });
    };

    /**
     * 绑定清空按钮功能
     */
    this.bindReset = function(){
        //清空按钮
        $(_controlOpt.formResetButton).bind("click",function(){
            //清除本地缓存
            if(_useParamsCache){
                localData.remove(cacheId);
            }
            var _form = $(_controlOpt.form);
            _form[0].reset();
        });
    }

    /**
     * 绑定清空按钮功能，重置时间查询条件，不清空时间
     * 调用此方法后不需要调用【formDatepicker】方法
     * options [{},{}]形式，每个对象的属性参考【formDatepicker】方法中的params
     */
    this.bindResetWithCallback = function(options){
        //清空按钮
        $(_controlOpt.formResetButton).bind("click",function(){
            //清除本地缓存
            if(_useParamsCache){
                localData.remove(cacheId);
            }
            var _form = $(_controlOpt.form);
            _form[0].reset();

            _this.callFormDatepicker(options);
        });

        _this.callFormDatepicker(options);
    }

    /**
     * 循环调用【formDatepicker】方法
     */
    this.callFormDatepicker = function(options){
        if(options instanceof Array){
            for (var i = 0;i < options.length;i++){
                if(options[i] instanceof Object) {
                    _this.formDatepicker(options[i]);
                }
            }
        }
    }

    /**
     * 刷新jqrid
     */
    this.reload = function(){
        $(selector).trigger('reloadGrid');
    };
    /**
     * 删除自动生成的alert标签
     */
    this.removeAlertTag = function(tagSelector){
        if(tagSelector){
            $(tagSelector).remove();
        }else{
            $(_controlOpt.alertTagSelector).remove();
        }
    };

    /**
     * 删除操作，弹出询问框
     * 参数：
     * options 删除相关参数，参入如下
     * options.url 提交的URL
     * options.data 提交的参数，支持json对象和key=value
     * options.successReload 成功时是否刷新jqGrid，true/false
     * options.statusName 服务端返回的状态参数名，默认status
     * options.messageName 服务端返回的消息参数名，默认message
     * options.successValue 成功时服务端返回的参数值，默认success
     * options.errorValue 失败时服务端返回的参数值，默认error
     *
     * swalOptions 可选的参数，swal插件的参数，详见：https://sweetalert.js.org/docs/
     */
    this.delete = function(options, swalOptions){
        var params = {
            title: "您确定要删除这条纪录吗？",
            text: "删除后将无法恢复，请谨慎操作！",
            icon: "warning",
            dangerMode: true,
            buttons: ['取消', '删除']
        };
        if(swalOptions){
            $.extend(params, swalOptions);
        }

        var ops = {
            successReload: true,
            statusName: 'status',
            messageName: 'message',
            successValue: 'success',
            errorValue: 'error'
        };
        $.extend(ops, options);

        swal(params).then(function(willDelete){
            if (willDelete) {
                $.post(ops.url, ops.data, function(msg){
                    if(msg){
                        if(msg[ops.statusName] == ops.successValue){
                            swal(msg[ops.messageName], {
                                icon: "success"
                            });
                            if(ops.successReload){
                                $(selector).trigger('reloadGrid');
                            }
                        }else if(msg[ops.statusName] == ops.errorValue){
                            swal(msg[ops.messageName], {
                                icon: "error"
                            });
                        }
                    }
                }, 'json');

                _this.removeAlertTag();
            }
        });
    };
    /**
     * 执行自定义操作，弹出询问框
     * 参数：
     * options 相关参数，参入如下
     * options.url 提交的URL
     * options.data 提交的参数，支持json对象和key=value
     * options.successReload 成功时是否刷新jqGrid，true/false
     * options.statusName 服务端返回的状态参数名，默认status
     * options.messageName 服务端返回的消息参数名，默认message
     * options.successValue 成功时服务端返回的参数值，默认success
     * options.errorValue 失败时服务端返回的参数值，默认error
     * options.successCallback 成功时回調，使用該參數時options.successReload將生效
     *
     * swalOptions 可选的参数，swal插件的参数，详见：https://sweetalert.js.org/docs/
     */
    this.execute = function(options, swalOptions){
        var params = {
            title: "您确定要操作这条纪录吗？",
            text: "操作后将无法恢复，请谨慎考虑！",
            icon: "warning",
            dangerMode: true,
            buttons: ['取消', '确定']
        };
        if(swalOptions){
            $.extend(params, swalOptions);
        }

        var ops = {
            successReload: true,
            statusName: 'status',
            messageName: 'message',
            successValue: 'success',
            errorValue: 'error',
            successCallback: null
        };
        $.extend(ops, options);

        swal(params).then(function(willDelete){
            if (willDelete) {
                $.post(ops.url, ops.data, function(msg){
                    if(msg){
                        if(msg[ops.statusName] == ops.successValue){
                            swal(msg[ops.messageName], {
                                icon: "success"
                            });
                            if(ops.successCallback && $.isFunction(ops.successCallback)){
                            	ops.successCallback.call(_this, msg);
                            	
                            }else if(ops.successReload){
                                $(selector).trigger('reloadGrid');
                            }
                        }else if(msg[ops.statusName] == ops.errorValue){
                            swal(msg[ops.messageName], {
                                icon: "error"
                            });
                        }
                    }
                }, 'json');

                _this.removeAlertTag();
            }
        });
    };

    //初始化input,select查询数据
    this.formParamsCache = function(){
        var _params;
        var jsonStr = localData.get(cacheId);
        if(jsonStr){
            _params = JSON.parse(jsonStr);
            var _form = $(_controlOpt.form);
            _form.find('input, select').each(function(i,n){
                if(_params[n.name]){
                    $(n).val(_params[n.name]);
                }
            });
        }
        return _params;
    }

    /**
     * laydate时间选择器封装
     * 针对单个选择器的配置，每个laydate Input框配置一次
     * 如果缓存中存在值，则使用缓存；缓存中没有值则使用默认时间
     * @param options 参考方法中的params
     */
    this.formDatepicker = function(options){
        var val = new Date();

        var params = {
            type: 'date',					//datetime 带有时间选择器
            format: 'yyyy-MM-dd',
            trigger: 'click',
            elementName: '',				//时间input框name
            deviation: 0,					//时间偏移量  例： 值为-30表示输入框默认的时间为今天时间-30天
            defaultval:true,                //默认值
            btns: ['now', 'confirm']        //clear、now、confirm
        }
        $.extend(params, options);

        var selectorOption = $(_controlOpt.form).find("input[name="+options.elementName+"]").get(0);
        //默认时间
        val.setDate(val.getDate()+params.deviation);

        //从页面缓存中取时间查询条件
        if(_useParamsCache){
            var jsonStr = localData.get(cacheId);
            if(jsonStr){
                var _cacheParams = JSON.parse(jsonStr);
                if(_cacheParams[params.elementName]){
                    val = _cacheParams[params.elementName];
                }
            }
        }

        //清空默认值
        if(!params.defaultval){
            val = null;
        }

        //格式化日历显示
        laydate.render({
            elem: selectorOption,
            type: params.type,
            format: params.format,
            trigger: params.trigger,
            value: val,
            btns: params.btns
        });

    }
}

/**
 * 为url增加参数，param格式可为js对象或者字符串
 * 示例：joinUrlParam('/param/edit', 'id=123&key=a1') 或 joinUrlParam('/param/edit', {id:'123',key:'a1'})
 * @param url 原始URL
 * @param params 需要增加的参数
 * @returns
 */
function joinUrlParam(url, params){
    if(url.indexOf('?') > 0){
        url += '&';
    }else{
        url += '?';
    }
    if(params instanceof Object){
        for(var i in params){
            url += i + '=' + params[i];
        }
    }else if(params instanceof String){
        url += params;
    }
    return url;
}

/**
 * 将form的参数名和值，转换成json对象格式
 * @param formSelector form的选择器
 * @param args 参数（可选对象），为提交的表单增加新的参数和值，若与表单存在同名的参数名，则被表单覆盖
 * @returns
 */
function formToObject(formSelector, args){
    var params = {};
    if(args && (args instanceof Object)){
        $.extend(params, args);
    }
    var formArray = $(formSelector).serializeArray();
    $.each(formArray, function() {
        params[this.name] = this.value;
    });
    return params;
}

/**
 *查询参数放入缓存
 * @param formSelector	form选择器
 */
function setLocalCache(formSelector, cacheId) {
    var params = {};
    var formArray = $(formSelector).serializeArray();
    $.each(formArray, function() {
        params[this.name] = this.value;
    });
    //存入本地缓存
    localData.set(cacheId,JSON.stringify(params));
}

/**
 * jqGrid格式化小数
 * @param cellvalue
 * @param options
 * @param rowdata
 * @returns
 */
function fmtNumber(cellvalue, options, rowdata){
    var f = parseFloat(cellvalue);
    if (isNaN(f)) {
        return '';
    }
    f = Math.round(cellvalue*100)/100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}

/**
 * 当参数不存在或为空时，返回空字符串
 * @param str
 * @returns
 */
function emptyString(str){
    return str ? str : '';
}

