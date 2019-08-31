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

/**
 * goodsId: 商品编号
 * goodsInfoUrl: 查询商品信息URL
 * specInfoUrl: 查询规格URL
 */
inventory.editInventorySpec = function(option){
    //初始化组件
    this.commonInit(option);
    //获取商品信息，并显示库存表单
    this.getGoodsInfoAndInventory(option);
    
    //生成规格属性表格
    inventory.showSpecPropTable();
    //当前操作是新增或修改页面时，调用的生成库存table方法不一样
    inventory.showInventoryTable();
}

//获取商品信息，根据商品是否存在规格，展示不同的库存表单
inventory.getGoodsInfoAndInventory = function(option){
    inventory.searchGoodsInfo(option, function(data){
        //商品存在规格时，设置标识为true
        if(data.specFlag=='1'){
            inventory.hasSpec = true;
        }else{
            inventory.hasSpec = false;
        }
        inventory.showSpecForm(option);
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

//生成规格属性表格
inventory.showSpecPropTable = function (){
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



//生成库存规格表格
inventory.showInventoryTable = function (){
    var _content = $('#show-inventory-tbl-content');
    _content.empty();
    var _t;
    if(!inventory.utils.isChosenSpec1Null() && inventory.utils.isChosenSpec2Null()) {
        //若一级规格已存储二级规格未存储时，生成一级库存规格表格，否则不生成
        var _inventory1 = {
            goods: inventory.goodsInfo,
            spec: {
                specCode: inventory.chosenSpec1.specCode,
                specName: inventory.utils.spec.getSpecName(inventory.chosenSpec1.specCode)
            },
            inventoryList: []
        };
        var _inventoryList = [];
        for(var i in inventory.goodsInfo.inventoryList){
            var _inv = inventory.goodsInfo.inventoryList[i];
            //由于在页面删除规格后，二级规格会升级为一级，故当前只规格可能是库存中的一级也可能是二级
            var _spec1 = inventory.utils.spec.getSpecFromSpecPropertyCode(_inv.specPropertyCode1);
            var _spec2 = inventory.utils.spec.getSpecFromSpecPropertyCode(_inv.specPropertyCode2);
            if(_spec1.specCode == inventory.chosenSpec1.specCode){
                _inv.originalNode = '1';        //原规格属性是一级节点
                _inv.specPropertyName = inventory.utils.spec.getSpecPropertyName(inventory.chosenSpec1.specCode, _inv.specPropertyCode1);
                _inventoryList.push(_inv);
            }else if(_spec2.specCode == inventory.chosenSpec1.specCode){
                _inv.originalNode = '2';        //原规格属性是二级节点
                _inv.specPropertyName = inventory.utils.spec.getSpecPropertyName(inventory.chosenSpec1.specCode, _inv.specPropertyCode2);
                _inventoryList.push(_inv);
            }
        }
        //合并库存，key:specPropertyCode, value:库存
        var _inventoryMap = new Map();
        for(var i in _inventoryList){
            var _inv = _inventoryList[i];
            if(_inv.originalNode == '1') {       //原规格属性是一级节点
                if(_inventoryMap.containsKey(_inv.specPropertyCode1)){
                    var _tmpInv = _inventoryMap.get(_inv.specPropertyCode1);
                    _tmpInv.buyPrice += _inv.buyPrice;
                    _tmpInv.pointPrice += _inv.pointPrice;
                    _tmpInv.amtPrice += _inv.amtPrice;
                    _tmpInv.total += _inv.total;
                }else{
                    _inventoryMap.put(_inv.specPropertyCode1, _inv);
                }
            }else if(_inv.originalNode == '2'){     //原规格属性是二级节点
                if(_inventoryMap.containsKey(_inv.specPropertyCode2)){
                    var _tmpInv = _inventoryMap.get(_inv.specPropertyCode2);
                    _tmpInv.buyPrice += _inv.buyPrice;
                    _tmpInv.pointPrice += _inv.pointPrice;
                    _tmpInv.amtPrice += _inv.amtPrice;
                    _tmpInv.total += _inv.total;
                }else{
                    _inventoryMap.put(_inv.specPropertyCode2, _inv);
                }
            }
        }
        _inventory1.inventoryList = _inventoryMap.values();

        var _tpl = template('show-inventory-tbl-tpl', _inventory1);
        _t = $(_tpl);
        _content.append(_t);
    }
	if(!inventory.utils.isChosenSpec1Null() && !inventory.utils.isChosenSpec2Null()){
        //若二级规格已存储，生成包含一级和二级库存规格表格，否则不生成
        var _inventory2 = {
            goods: inventory.goodsInfo,
            spec1: {
                specCode: inventory.chosenSpec1.specCode,
                specName: inventory.utils.spec.getSpecName(inventory.chosenSpec1.specCode)
            },
            spec2: {
                specCode: inventory.chosenSpec2.specCode,
                specName: inventory.utils.spec.getSpecName(inventory.chosenSpec2.specCode)
            },
            inventoryList: [],
            spec2PropCount: 0       //二级规格的属性数量
        };
        for(var i in inventory.goodsInfo.inventoryList){
            var _inv = inventory.goodsInfo.inventoryList[i];
            _inv.specPropertyName1 = inventory.utils.spec.getSpecPropertyName(inventory.chosenSpec1.specCode, _inv.specPropertyCode1);
            _inv.specPropertyName2 = inventory.utils.spec.getSpecPropertyName(inventory.chosenSpec2.specCode, _inv.specPropertyCode2);
            _inventory2.inventoryList.push(_inv);
        }
        for(var i in inventory.goodsInfo.specList){
            var _spec = inventory.goodsInfo.specList[i];
            if(_spec.node=='2'){
                _inventory2.spec2PropCount = _spec.specPropertyList ? _spec.specPropertyList.length : 0;
            }
        }
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

//显示库存表单
inventory.showSpecForm = function(option){
    //存在规格时，显示包含规格属性的table
    if(inventory.hasSpec){
        //存储选中的规格
        for(var i in inventory.goodsInfo.specList){
            var _spec = inventory.goodsInfo.specList[i];
            if(_spec){
                if(_spec.node=='1'){
                    inventory.chosenSpec1.specCode = _spec.specCode;
                    inventory.chosenSpec1.specPropertyList = _spec.specPropertyList;
                }else if(_spec.node=='2'){
                    inventory.chosenSpec2.specCode = _spec.specCode;
                    inventory.chosenSpec2.specPropertyList = _spec.specPropertyList;
                }
            }
        }
        //生成商品规格select
        inventory.getSpecList(option, function(specResult){
            //生成规格属性table
            inventory.showSpecPropTable();
            //生成库存规格table
            inventory.showInventoryTable();
        });
    }else{
        //不存在规格，显示没有规格属性的表单
        var _tpl = template('show-nospec-tpl', {goods: inventory.goodsInfo});
        $('#show-nospec-content').append(_tpl);

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
    })
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
    $.post(option.specInfoUrl, {goodsId: option.goodsId}, function(data){
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
        },
        /**
         * 判断值是否存在于数组中
         * @param arr 数组
         * @param comparison 判断的值，基本类型或函数，为函数时，函数拥有2个参数，第一个为数组的下标，第二个为当前的数组中的值，this为当前的数组中的值
         * @returns {boolean} true/false
         */
        contain: function(arr, comparison){
            if(typeof comparison === "function"){
                for(var i in arr){
                    var rs = comparison.call(arr[i], i, arr[i]);
                    if(rs){
                        return true;
                    }
                }
            }else{
                for(var i in arr){
                    if(arr[i] === value){
                        return true;
                    }
                }
            }
            return false;
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