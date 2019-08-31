var _rule = template.defaults.rules[0];
_rule.test = new RegExp(_rule.test.source.replace('<%', '<\\\?').replace('%>', '\\\?>'));

var inventory = window.inventory || {};

inventory.goodsInfo = {};      //商品信息，从服务端获取的信息
inventory.specList = [];       //规格列表，从服务端获取的信息
//选择的一级规格和规格属性
inventory.chosenSpec1 = {
    specCode: null,
    node: '1',
    specPropertyList: []
};
//选择的二级规格和规格属性
inventory.chosenSpec2 = {
    specCode: null,
    node: '2',
    specPropertyList: []
};
//是否存在规格的标识，商品存在库存规格时为true
inventory.hasSpec = false;
//标识当前是新增还是修改操作
inventory.optionType = '';       //CREATE/EDIT，见：inventory.constant.optionType

/**
 * goodsId: 商品编号
 * goodsInfoUrl: 查询商品信息URL
 * specInfoUrl: 查询规格URL
 * saveNospecUrl: 添加/修改库存规格URL（不存在规格）
 * saveSpecUrl: 添加/修改库存规格URl（存在规格）
 * saveRedirectUrl: 保存成功后的重定向的URL
 */
inventory.createInventorySpec = function(option){
    //标识当前为新增
    inventory.optionType = inventory.constant.optionType.create;
    //初始化组件
    this.commonInit(option);
    //判断商品是否存在规格，存在时需要显示添加属性按钮
    this.getGoodsInfoAndCheck(option);
}

//获取商品信息，并判断商品是否存在规格
inventory.getGoodsInfoAndCheck = function(option){
    inventory.searchGoodsInfo(option, function(data){
        inventory.showCreateSpecForm(true, option);
    });
}

//获取规格，并生成select标签
//参数callback，是为了在生成select后执行其他操作时用的，会传入查询规格接口返回的参数
inventory.getSpecList = function (option, callback){
    inventory.searchSpecInfo(option, function(data){
        if(data && data.specList && $.isArray(data.specList)){

            var _tpl = template('show-spec-slt-tpl', {specList: data.specList});
            if(_tpl){
                var _s = $(_tpl);
                $('#show-spec-slt-content').append(_s);
                //绑定change事件，当选择了规格时，显示规格属性
                _s.on('change', function(){
                    var _specCode = $(this).find('option:selected').val();
                    inventory.showSpecProperty(_specCode);
                });
                //绑定添加规格按钮，生成库存规格表格
                _s.find('#add-goods-spec-btn').on('click', inventory.showInventorySpec);
                //调用回调
                if(callback && $.isFunction(callback)){
                    callback.call(this, data);
                }
            }
        }
    });
}

//选择规格属性后，生成库存规格表格
inventory.showInventorySpec = function(){
    var _checkbox = $('#spec-prop-content input[type=checkbox]:checked');
    var _specProps = [];
    $.each(_checkbox, function(i,n){
        _specProps.push(n.value);
    })
    //选中属性时，记录规格和规格属性
    if(_specProps.length>0){
        var _specCode = $('#goods-spec-slt option:selected').val();
        //记录规格和规格属性
        inventory.recordSpec(_specCode, _specProps);
        //生成规格属性表格
        inventory.createSpecPropTable();
        //生成库存规格表格
        inventory.createInventoryTable();
    }
}

//新增库存-生成规格属性表格
inventory.createSpecPropTable = function (){
    var _cotent = $('#show-spec-tbl-content');
    _cotent.empty();
    if(!inventory.utils.isChosenSpec1Null() && inventory.utils.isChosenSpec2Null()){
        //若一级规格已存储二级规格未存储时，生成一级规格属性表格，否则不生成
        var _spec1 = inventory.createSpec1Data();
        var _tpl = template('show-spec-tbl-tpl', {spec1: _spec1});
        _cotent.append(_tpl);
    }else if(!inventory.utils.isChosenSpec1Null() && !inventory.utils.isChosenSpec2Null()){
        //若二级规格已存储，生成包含一级和二级规格属性表格，否则不生成
        var _spec1 = inventory.createSpec1Data();
        var _spec2 = inventory.createSpec2Data();
        var _tpl = template('show-spec-tbl-tpl', {spec1: _spec1, spec2: _spec2});
        _cotent.append(_tpl);
    }else{
        return;
    }
    //绑定操作
    _cotent.find('.edit').on('click', inventory.editSpecProp);
    _cotent.find('.del').on('click', inventory.delSpecProp);
}

//创建一个一级规格对象，包含了选中的规格属性
inventory.createSpec1Data = function (){
    var _spec = {};
    _spec.specCode = inventory.chosenSpec1.specCode;
    _spec.specName = inventory.utils.spec.getSpecName(inventory.chosenSpec1.specCode);
    _spec.specPropertyList = [];
    for(var i in inventory.chosenSpec1.specPropertyList){
        var _specProp = (inventory.chosenSpec1.specPropertyList)[i];
        var _specPropName = inventory.utils.spec.getSpecPropertyName(inventory.chosenSpec1.specCode, _specProp.specPropertyCode);
        _spec.specPropertyList.push({
            specPropertyCode: _specProp.specPropertyCode,
            specPropertyName: _specPropName
        });
    }
    return _spec;
}
//创建一个二级规格对象，包含了选中的规格属性
inventory.createSpec2Data = function (){
    var _spec = {};
    _spec.specCode = inventory.chosenSpec2.specCode;
    _spec.specName = inventory.utils.spec.getSpecName(inventory.chosenSpec2.specCode);
    _spec.specPropertyList = [];
    for(var i in inventory.chosenSpec2.specPropertyList){
        var _specProp = (inventory.chosenSpec2.specPropertyList)[i];
        var _specPropName = inventory.utils.spec.getSpecPropertyName(inventory.chosenSpec2.specCode, _specProp.specPropertyCode);
        _spec.specPropertyList.push({
            specPropertyCode: _specProp.specPropertyCode,
            specPropertyName: _specPropName
        });
    }
    return _spec;
}

//暂不使用
inventory.editSpecProp = function (){
    var _specCode = $(this).data('spec_code');
    var _specPropList;
    if(inventory.chosenSpec1.specCode == _specCode){
        //编辑的是一级规格
        _specPropList = inventory.chosenSpec1.specPropertyList;
    }else if(inventory.chosenSpec2.specCode == _specCode){
        //编辑的是二级规格
        _specPropList = inventory.chosenSpec2.specPropertyList;
    }else{
        lay.msg('未能获取选定的规格编号');
        return;
    }
    //根据选中的规格编号将select设置为选中
    var _select = $('#goods-spec-slt');
    _select.val(_specCode).trigger('change');
    //根据选中的规格属性将checkbox选中
    var _checkboxContent = $('#spec-prop-content');
    for(var i in _specPropList){
        var _specPropCode = _specPropList[i].specPropertyCode;
        _checkboxContent.find('input[value='+_specPropCode+']').prop('checked',true);
    }
}

inventory.delSpecProp = function (){
    var _specCode = $(this).data('spec_code');
    if(inventory.chosenSpec1.specCode == _specCode){
        //删除的是一级规格，二级规格降级为一级，清空二级规格记录，
        inventory.chosenSpec1.specCode = inventory.chosenSpec2.specCode;
        inventory.chosenSpec1.specPropertyList = inventory.chosenSpec2.specPropertyList;
        inventory.chosenSpec2.specCode = null;
        inventory.chosenSpec2.specPropertyList = [];
    }else if(inventory.chosenSpec2.specCode == _specCode){
        //删除的是二级规格，清空二级规格记录
        inventory.chosenSpec2.specCode = null;
        inventory.chosenSpec2.specPropertyList = [];
    }else{
        lay.msg('未能获取选定的规格编号');
        return;
    }
    //生成规格属性表格
    inventory.createSpecPropTable();
    //生成库存table
    inventory.createInventoryTable();
}

//新增库存-生成库存规格表格
inventory.createInventoryTable = function (){
    var _content = $('#show-inventory-tbl-content');
    _content.empty();
    var _t;
    if(!inventory.utils.isChosenSpec1Null() && inventory.utils.isChosenSpec2Null()) {
        //若一级规格已存储二级规格未存储时，生成一级库存规格表格，否则不生成
        var _inventory1 = inventory.createInventorySpec1Data();
        var _tpl = template('show-inventory-tbl-tpl', _inventory1);
        _t = $(_tpl);
        _content.append(_t);
    }else if(!inventory.utils.isChosenSpec1Null() && !inventory.utils.isChosenSpec2Null()){
        //若二级规格已存储，生成包含一级和二级库存规格表格，否则不生成
        var _inventory2 = inventory.createInventorySpec2Data();
        var _tpl = template('show-inventory-tbl2-tpl', _inventory2);
        _t = $(_tpl);
        _content.append(_t);
    }else{
        return;
    }
    if(_t){
        _t.find('.sync-purchase').on('click', inventory.syncPurchase);
        _t.find('.sync-sale').on('click', inventory.syncSale);
        _t.find('.sync-total').on('click', inventory.syncTotal);
    }
}

//创建一个一级库存规格表格数据
inventory.createInventorySpec1Data = function (){
    var _inventory = {goods: inventory.goodsInfo};
    _inventory.spec = {
        specCode: inventory.chosenSpec1.specCode,
        specName: inventory.utils.spec.getSpecName(inventory.chosenSpec1.specCode)
    };
    _inventory.specPropertyList = [];
    for(var i in inventory.chosenSpec1.specPropertyList){
        var _specProp = (inventory.chosenSpec1.specPropertyList)[i];
        var _specPropName = inventory.utils.spec.getSpecPropertyName(inventory.chosenSpec1.specCode, _specProp.specPropertyCode);
        _inventory.specPropertyList.push({
            specPropertyCode: _specProp.specPropertyCode,
            specPropertyName: _specPropName
        });
    }
    return _inventory;
}

//创建一个包含了一级和二级库存规格的表格数据
inventory.createInventorySpec2Data = function (){
    var _inventory = {goods: inventory.goodsInfo};
    _inventory.spec1 = {
        specCode: inventory.chosenSpec1.specCode,
        specName: inventory.utils.spec.getSpecName(inventory.chosenSpec1.specCode)
    };
    _inventory.spec2 = {
        specCode: inventory.chosenSpec2.specCode,
        specName: inventory.utils.spec.getSpecName(inventory.chosenSpec2.specCode)
    };
    _inventory.specPropertyList1 = [];
    _inventory.specPropertyList2 = [];
    for(var i in inventory.chosenSpec1.specPropertyList){
        var _specProp = (inventory.chosenSpec1.specPropertyList)[i];
        var _specPropName = inventory.utils.spec.getSpecPropertyName(inventory.chosenSpec1.specCode, _specProp.specPropertyCode);
        _inventory.specPropertyList1.push({
            specPropertyCode: _specProp.specPropertyCode,
            specPropertyName: _specPropName
        });
    }
    for(var i in inventory.chosenSpec2.specPropertyList){
        var _specProp = (inventory.chosenSpec2.specPropertyList)[i];
        var _specPropName = inventory.utils.spec.getSpecPropertyName(inventory.chosenSpec2.specCode, _specProp.specPropertyCode);
        _inventory.specPropertyList2.push({
            specPropertyCode: _specProp.specPropertyCode,
            specPropertyName: _specPropName
        });
    }
    return _inventory;
}

//同步输入的采购价
inventory.syncPurchase = function (){
    var $purchase = $(this).closest('th').find('.sync-purchase-input');
    var _val = $.trim($purchase.val());
    if(!_val){
        inventory.utils.msg.toast('同步采购价必须不能为空');
        return;
    }
    if(isNaN(_val)){
        inventory.utils.msg.toast('同步采购价必须为数字');
        return;
    }
    if(_val < 0){
        inventory.utils.msg.toast('同步采购价不能小于0');
        return;
    }
    $('.sync-purchase-retinue').val(_val);
}
//同步输入的销售价
inventory.syncSale = function (){
    var _th = $(this).closest('th');
    var _input = _th.find('.sync-sale-input');      //积分输入
    var _input2 = _th.find('.sync-sale-input2');        //现金输入
    var _val1 = $.trim(_input.val());
    var _val2 = $.trim(_input2.val());
    if(_input && _input.length>0){
        if(!_val1){
            inventory.utils.msg.toast('同步销售积分必须不能为空');
            return;
        }
        if(isNaN(_val1)){
            inventory.utils.msg.toast('同步销售积分必须为数字');
            return;
        }
        if(_val1 < 0){
            inventory.utils.msg.toast('同步销售积分不能小于0');
            return;
        }
        if(_val1%1 !== 0){
            inventory.utils.msg.toast('同步销售积分必须为整数');
            return;
        }
    }
    if(_input2 && _input2.length>0){
        if(!_val2){
            inventory.utils.msg.toast('同步销售价必须不能为空');
            return;
        }
        if(isNaN(_val2)){
            inventory.utils.msg.toast('同步销售价必须为数字');
            return;
        }
        if(_val2 < 0){
            inventory.utils.msg.toast('同步销售价不能小于0');
            return;
        }
    }
    $('.sync-sale-retinue').val(_val1);
    $('.sync-sale-retinue2').val(_val2);
}
//同步输入的库存数
inventory.syncTotal = function (){
    var _val = $(this).closest('th').find('.sync-total-input').val();
    _val = $.trim(_val);
    if(!_val){
        inventory.utils.msg.toast('同步变更库存必须不能为空');
        return;
    }
    if(isNaN(_val)){
        inventory.utils.msg.toast('同步变更库存必须为数字');
        return;
    }
    if(_val%1 !== 0){
        inventory.utils.msg.toast('同步变更库存必须为整数');
        return;
    }
    $('.sync-total-retinue').val(_val);
}

//记录规格与规格属性
inventory.recordSpec = function (specCode, specProps){
    //若未记录过一级规格，则保存在一级中
    if(inventory.utils.isChosenSpec1Null()){
        inventory.chosenSpec1.specCode = specCode;
        for(var i in specProps){
            inventory.chosenSpec1.specPropertyList.push({specPropertyCode: specProps[i]});
        }
        return;
    }
    //若记录过一级规格未记录过二级规格，则保存在二级中
    if(!inventory.utils.isChosenSpec1Null() && inventory.utils.isChosenSpec2Null()){
        //若将要存储的二级规格编号与已存储的一级编号相同，不允许存储
        if(inventory.chosenSpec1.specCode == specCode){
            layer.msg("当前规格已选择为一级规格")
            return;
        }
        inventory.chosenSpec2.specCode = specCode;
        for(var i in specProps){
            inventory.chosenSpec2.specPropertyList.push({specPropertyCode: specProps[i]});
        }
        return;
    }
    //若一级和二级规格都记录过，则不保存
    if(!inventory.utils.isChosenSpec1Null() && !inventory.utils.isChosenSpec2Null()){
        layer.msg('已选择过一级和二级规格');
        return;
    }
}

//选择规格后，显示规格属性checkbox
inventory.showSpecProperty = function (specCode){
    if(inventory.specList){
        var _content = $('#spec-prop-content');
        _content.empty();
        //从存储的规格和规格属性中提取选中的规格属性
        for(var i in inventory.specList){
            var _spec = inventory.specList[i];
            if(_spec.specCode==specCode){
                var _specPropTpl = template('show-specProp-cbx-tpl', {specPropertyList: _spec.specPropertyList});
                $('#spec-prop-content').append(_specPropTpl);
            }
        }
    }
}


//创建库存时，显示库存表单
//商品为卡券时，不允许设置规格
inventory.showCreateSpecForm = function (isShow, option){
    //显示无规格的库存表单
    var _form = template('show-nospec-tpl', {goods: inventory.goodsInfo});
    _form = $(_form);
    $('#show-nospec-content').append(_form);
    //若商品是实物类，可以修改库存
    if(inventory.goodsInfo.goodsClassify == '2'){
        _form.find('input[name=total]').removeAttr('readonly');
        _form.find('input[name=total]').val('');
    }else{
        //商品为卡券时，不允许设置规格
        return;
    }

    //显示商品规格选择按钮
    var _btn = template('show-spec-form-btn-tpl', {});
    if(_btn){
        var _b = $(_btn);
        //当点击添加属性时，显示商品规格select，同时清除无规格的库存表单，并设置规格库存标识为true
        _b.on('click', function(){
            $(this).remove();
            //设置标识为true
            inventory.hasSpec = true;
            //清除无规格的库存表单
            $('#show-nospec-content').empty();
            //获取并显示商品规格下拉
            inventory.getSpecList(option);
        }) ;
        $('#show-spec-form-btn').append(_b);
    }
}

//保存库存
inventory.saveInventory = function(option){
    if(inventory.hasSpec){
        if(inventory.utils.isChosenSpec1Null()){
            inventory.utils.msg.toast('商品存在规格，需要添加商品规格');
            return;
        }
        //有规格的库存表单校验
        var isPass = validate.spec({
            content: '#show-inventory-tbl-content',
        });
        //通过校验，提交有规格的库存数据
        if(isPass){
            var params = {
                goodsId: inventory.goodsInfo.goodsId,
                specList: [],
                inventoryList: []
            };
            //只选择了一级规格
            if(!inventory.utils.isChosenSpec1Null() && inventory.utils.isChosenSpec2Null()){
                params.specList.push({
                    specCode: inventory.chosenSpec1.specCode,
                    node: inventory.chosenSpec1.node,
                    specPropertyList: inventory.chosenSpec1.specPropertyList
                });
                var _trList = $('#show-inventory-tbl-content tbody tr');
                _trList.each(function(i,n){
                    var _tr = $(n);
                    params.inventoryList.push({
                        specPropertyCode1: _tr.find('input.spec-prop-code').val(),
                        specPropertyCode2: null,
                        buyPrice: _tr.find('input.sync-purchase-retinue').val(),
                        pointPrice: _tr.find('input.sync-sale-retinue').val(),
                        amtPrice: _tr.find('input.sync-sale-retinue2').val(),
                        total: _tr.find('input.sync-total-retinue').val()
                    });
                });
            }else if(!inventory.utils.isChosenSpec1Null() && !inventory.utils.isChosenSpec2Null()){
                //选择了一级和二级规格
                params.specList.push({
                    specCode: inventory.chosenSpec1.specCode,
                    node: inventory.chosenSpec1.node,
                    specPropertyList: inventory.chosenSpec1.specPropertyList
                });
                params.specList.push({
                    specCode: inventory.chosenSpec2.specCode,
                    node: inventory.chosenSpec2.node,
                    specPropertyList: inventory.chosenSpec2.specPropertyList
                });
                var _trList = $('#show-inventory-tbl-content tbody tr');
                _trList.each(function(i,n){
                    var _tr = $(n);
                    params.inventoryList.push({
                        specPropertyCode1: _tr.find('input.spec-prop-code1').val(),
                        specPropertyCode2: _tr.find('input.spec-prop-code2').val(),
                        buyPrice: _tr.find('input.sync-purchase-retinue').val(),
                        pointPrice: _tr.find('input.sync-sale-retinue').val(),
                        amtPrice: _tr.find('input.sync-sale-retinue2').val(),
                        total: _tr.find('input.sync-total-retinue').val()
                    });
                });
            }else if(inventory.utils.isChosenSpec1Null() && !inventory.utils.isChosenSpec2Null()){
                //未选择一级规格，但选择了二级规格
                inventory.utils.msg.toast('检测到选择了二级规格，但未选择一级规格，此处应为BUG', 5000);
                return;
            }else{
                //一级和二级规格都未选择
                inventory.utils.msg.warning('一级规格和二级规格均未选择');
                return;
            }
            inventory.saveSpec(option, params, function(data){
                window.location.href = option.saveRedirectUrl;
            });
        }
    }else{
        //商品为实物类，需要检查库存
        var needTotal = (inventory.goodsInfo.goodsClassify == '2') ? true : false;
        //无规格的库存表单校验
        var isPass = validate.nospec({
            content: '#show-nospec-content',
            needTotal: needTotal
        });
        //通过校验，提交无规格的库存数据
        if(isPass){
            var params = {
                goodsId: inventory.goodsInfo.goodsId,
                buyPrice: $('#show-nospec-content').find('input[name=buyPrice]').val(),
                pointPrice: $('#show-nospec-content').find('input[name=pointPrice]').val(),
                amtPrice: $('#show-nospec-content').find('input[name=amtPrice]').val(),
                total: $('#show-nospec-content').find('input[name=total]').val(),
            };
            inventory.saveNospec(option, params, function(data){
                window.location.href = option.saveRedirectUrl;
            });
        }
    }
}


/**
 * 初始化组件
 */
inventory.commonInit = function (option){
    //ajax全局配置
    $.ajaxSetup({
        beforeSend: function(XMLHttpRequest){
            animation.load();
        },
        error: function(xMLHttpRequest, textStatus, errorThrown){
            swal({
                icon: 'error',
                title: textStatus + '',
                text: errorThrown + '',
                closeOnClickOutside: false,
                buttons: {
                    cancel: '关闭',
                    confirm: {
                        text: '详情',
                        value: '1'
                    }
                },
            }).then(function(v){
                if(v=='1'){
                    swal({
                        title: '错误详情',
                        text: xMLHttpRequest.responseText,
                        buttons: {
                            cancel: '关闭'
                        }
                    });
                }
            });
        },
        complete: function(XMLHttpRequest, textStatus){
            animation.close();
        },
    });
    //绑定保存按钮
    $('#save-inventory').on('click', function(){
        try{
            $('#save-inventory').prop('disabled', true);
            inventory.saveInventory(option);
        }catch(e){
            inventory.utils.msg.error(e.name, '错误' + e.message + '发生在' + e.lineNumber + '行');
        }finally{
            $('#save-inventory').prop('disabled', false);
        }
    });
}

/**
 * 查询商品信息
 */
inventory.searchGoodsInfo = function (option, callback){
    $.post(option.goodsInfoUrl, {goodsId: option.goodsId}, function(data){
        if(data){
            if(data.code!='SUCCESS'){
                swal (data.code, data.msg, "error");
            }else{
                inventory.goodsInfo = data;
                if(callback && $.isFunction(callback)){
                    callback.call(this, data);
                }
            }
        }
    }, 'json');
}
/**
 * 查询规格
 */
inventory.searchSpecInfo = function (option, callback){
    $.post(option.specInfoUrl, null, function(data){
        if(data){
            if(data.code!='SUCCESS'){
                swal (data.code, data.msg, "error");
            }else{
                inventory.specList = data.specList;
                if(callback && $.isFunction(callback)){
                    callback.call(this, data);
                }
            }
        }
    }, 'json');
}

/**
 * 添加/修改库存规格（不存在规格）
 */
inventory.saveNospec = function (option, params, callback){
    $.ajax({
        type:'POST',
        url: option.saveNospecUrl,
        data: JSON.stringify(params),
        contentType: 'application/json;charset=utf-8',
        dataType: 'json',
        success: function(data){
            if(data.code!='SUCCESS'){
                swal (data.code, data.msg, "error");
            }else{
                inventory.utils.msg.success(data.msg, 1200);
                setTimeout(function(){
                    if(callback && $.isFunction(callback)){
                        callback.call(data, data);
                    }
                }, 1200);
            }
        }
    })
}

/**
 * 添加/修改库存规格（存在规格）
 */
inventory.saveSpec = function (option, params, callback){
    $.ajax({
        type:'POST',
        url: option.saveSpecUrl,
        data: JSON.stringify(params),
        contentType: 'application/json;charset=utf-8',
        dataType: 'json',
        success: function(data){
            if(data.code!='SUCCESS'){
                swal (data.code, data.msg, "error");
            }else{
                inventory.utils.msg.success(data.msg, 1200);
                setTimeout(function(){
                    if(callback && $.isFunction(callback)){
                        callback.call(data, data);
                    }
                }, 1200);
            }
        }
    })
}

inventory.utils = {
    show: function(selector){
        $(selector).removeClass('hide');
    },
    hide: function(selector){
        $(selector).addClass('hide');
    },
    array: {
        insert: function(arr, index, value){
            arr.splice(index, 0, value);
        }
    },
    spec: {
        getSpecName: function(specCode){
            if(inventory.specList){
                for(var i in inventory.specList){
                    var _spec = inventory.specList[i];
                    if(specCode==_spec.specCode){
                        return _spec.specName;
                    }
                }
            }
            return null;
        },
        getSpecPropertyName: function(specCode, specPropertyCode){
            if(inventory.specList){
                for(var i in inventory.specList){
                    var _spec = inventory.specList[i];
                    if(_spec){
                        var _specPropList = _spec.specPropertyList;
                        if(_specPropList){
                            for(var j in _specPropList){
                                var _specProp = _specPropList[j];
                                if(specPropertyCode==_specProp.specPropertyCode){
                                    return _specProp.specPropertyName;
                                }
                            }
                        }
                    }
                }
            }
            return null;
        },
        //根据规格属性编号获取规格信息，规格信息包含编号和名称
        getSpecFromSpecPropertyCode: function(specPropertyCode){
            if(inventory.specList){
                for(var i in inventory.specList){
                    var _spec = inventory.specList[i];
                    if(_spec){
                        var _specPropList = _spec.specPropertyList;
                        if(_specPropList){
                            for(var j in _specPropList){
                                var _specProp = _specPropList[j];
                                if(specPropertyCode==_specProp.specPropertyCode){
                                    return _spec;
                                }
                            }
                        }
                    }
                }
            }
            return {};
        }
    },
    isChosenSpec1Null: function(){      //一级规格是否未选择
        return !inventory.chosenSpec1.specCode && !(inventory.chosenSpec1.specPropertyList && inventory.chosenSpec1.specPropertyList.length > 0);
    },
    isChosenSpec2Null: function(){      //二级规格是否未选择
        return !inventory.chosenSpec2.specCode && !(inventory.chosenSpec2.specPropertyList && inventory.chosenSpec2.specPropertyList.length > 0);
    },
    msg: {
        success: function(message, time){
            swal ({
                icon: 'success',
                button: time ? false : '关闭',
                text: message + '',
                timer: time ? time : null
            });
        },
        warning: function(message, time){
            swal ({
                icon: 'warning',
                button: time ? false : '关闭',
                text: message + '',
                timer: time ? time : null
            });
        },
        error: function(title, message, time){
            swal ({
                icon: 'error',
                button: time ? false : '关闭',
                title: title + '',
                text: message + '',
                timer: time ? time : null
            });
        },
        toast: function(message, time){
            layer.msg(message + '', {time: time ? time : 2000});
        },
    }
}
/**
 * 常量
 */
inventory.constant = {
    //操作类型
    optionType: {
        create: 'CREATE',
        edit: 'EDIT'
    },
}



