/**
 * 依赖于sessionStorageUtil.js
 *
 * options.submitBtn  提交按钮选择器
 * options.resetBtn  清除按钮选择器
 * options.cacheId  缓存ID，默认为window.document.location.pathname
 * options.init  初始化时调用
 * options.first  第一次时调用的函数
 * options.initDateRange  初始化时间范围的函数
 * options.beforeReset  执行清除前调用的函数
 * options.afterReset  执行完清除后调用的函数
 */
(function(){

    $.fn.queryParamsRecord = function(options){
        var _this = this;

        var _options = {
            submitBtn: '',
            resetBtn: '',
            cacheId: 'globalCacheId',
        };
        $.extend(_options, options);

        //初始化
        initQuery(_this, _options);
        //绑定查询
        if(options.submitBtn){
            var _btn = $(options.submitBtn);
            _btn.on('click', function(){
                submitExec(_this, _btn, _options);
            });
        }
        //绑定清除
        if(options.resetBtn){
            var _btn = $(options.resetBtn);
            _btn.on('click', function(){
                resetExec(_this, _btn, _options);
            });
        }
    }

    function initQuery(form, options){
        if(options.init && $.isFunction(options.init)){
            options.init.call();
        }

        var params = localData.get(options.cacheId);
        if(!params){
            if(options.first && $.isFunction(options.first)){
                options.first.call();
            }
            return;
        }
        params = JSON.parse(params);
        //form赋值
        if(params){
            $.each(params, function(i,n){
                var _input = form.find('[name=' + n.name + ']');
                if(_input && _input.length > 0){
                    _input.val(n.value);
                }
            });
        }
    }

    function submitExec(form, btn, options){
        var params = JSON.stringify(form.serializeArray());
        localData.remove(options.cacheId);
        localData.set(options.cacheId, params);
        form.submit();
    }

    function resetExec(form, btn, options){
        if(options.beforeReset && $.isFunction(options.beforeReset)){
            options.beforeReset.call();
        }
        localData.remove(options.cacheId);
        form[0].reset();
        if(options.afterReset && $.isFunction(options.afterReset)){
            options.afterReset.call();
        }
    }
})();