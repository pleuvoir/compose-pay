var validate = window.validate || {}

/**
 * 不能存在规格的校验，返回true/false表示通过或不通过校验
 * content：父级容器的选择器
 * needTotal: 是否需要填库存，默认不需要
 */
validate.nospec = function(option){
    var _content = $(option.content);
    var _buyPrice = _content.find('input[name=buyPrice]');
    var _amtPrice = _content.find('input[name=amtPrice]');
    var _pointPrice = _content.find('input[name=pointPrice]');
    var _total = _content.find('input[name=total]');
    //校验采购价
    validate.clear.formGroup(_buyPrice);
    if(!validate.validator.notBlank(_buyPrice)){
        validate.error.formGroup(_buyPrice, '未输入商品采购单价');
        return false;
    }
    if(!validate.validator.number(_buyPrice)){
        validate.error.formGroup(_buyPrice, '必须输入数字');
        return false;
    }
    if(!validate.validator.range(_buyPrice, 0.01, 9999999999)){
        validate.error.formGroup(_buyPrice, '最小数为0.01，最大不超过10位');
        return false;
    }
    //校验销售价
    if(validate.isExist(_amtPrice)){
        validate.clear.formGroup(_amtPrice);
        if(!validate.validator.notBlank(_amtPrice)){
            validate.error.formGroup(_amtPrice, '未输入商品销售单价');
            return false;
        }
        if(!validate.validator.number(_amtPrice)){
            validate.error.formGroup(_amtPrice, '销售单价必须输入数字');
            return false;
        }
        if(!validate.validator.range(_amtPrice, 1, 9999999999)){
            validate.error.formGroup(_amtPrice, '销售单价最小数为1，最大不超过10位');
            return false;
        }
    }
    //校验销售积分
    if(validate.isExist(_pointPrice)){
        validate.clear.formGroup(_pointPrice);
        if(!validate.validator.notBlank(_pointPrice)){
            validate.error.formGroup(_pointPrice, '未输入商品销售积分');
            return false;
        }
        if(!validate.validator.integer(_pointPrice)){
            validate.error.formGroup(_pointPrice, '销售积分必须输入整数');
            return false;
        }
        if(!validate.validator.range(_pointPrice, 1, 9999999999)){
            validate.error.formGroup(_pointPrice, '销售积分最小数为1，最大不超过10位');
            return false;
        }
    }
    //需要填写库存，校验库存
    if(option.needTotal){
        validate.clear.formGroup(_total);
        if(!validate.validator.notBlank(_total)){
            validate.error.formGroup(_total, '未输入商品库存');
            return false;
        }
        if(!validate.validator.integer(_total)){
            validate.error.formGroup(_total, '必须是整数');
            return false;
        }
        if(!validate.validator.range(_total, -9999999999, 9999999999)){
            validate.error.formGroup(_total, '库存不超过10位');
            return false;
        }
    }
    return true;
}

/**
 * 存在规格的校验，返回true/false表示通过或不通过校验
 * content：父级容器的选择器
 * specLevel: 规格级别，1：一级规格校验，2：一级和二级规格校验
 */
validate.spec = function(option){
    var _content = $(option.content);
    var _buyPrices = _content.find('input.sync-purchase-retinue');
    var _pointPrices = _content.find('input.sync-sale-retinue');
    var _amtPrices = _content.find('input.sync-sale-retinue2');
    var _totals = _content.find('input.sync-total-retinue');
    var _isValid = true;
    //校验采购价
    validate.clear.table(_buyPrices);
    _buyPrices.each(function(i,n){
        var _n = $(n);
        if(!validate.validator.notBlank(_n)){
            validate.error.table(_n, '未输入商品采购单价');
            _isValid = false;
            return;
        }
        if(!validate.validator.number(_n)){
            validate.error.table(_n, '必须输入数字');
            _isValid = false;
            return;
        }
        if(!validate.validator.range(_n, 0.01, 9999999999)){
            validate.error.table(_n, '最小数为0.01，最大不超过10位');
            _isValid = false;
            return;
        }
    });
    if(!_isValid){
        return false;
    }
    //校验销售积分
    if(validate.isExist(_pointPrices)){
        validate.clear.table(_pointPrices);
        _pointPrices.each(function(i,n){
            var _n = $(n);
            if(!validate.validator.notBlank(_n)){
                validate.error.table(_n, '未输入商品销售积分');
                _isValid = false;
                return;
            }
            if(!validate.validator.integer(_n)){
                validate.error.table(_n, '销售积分必须输入整数');
                _isValid = false;
                return;
            }
            if(!validate.validator.range(_n, 1, 9999999999)){
                validate.error.table(_n, '销售积分最小数为1，最大不超过10位');
                _isValid = false;
                return;
            }
        });
        if(!_isValid){
            return false;
        }
    }
    //校验销售价
    if(validate.isExist(_amtPrices)){
        validate.clear.table(_amtPrices);
        _amtPrices.each(function(i,n){
            var _n = $(n);
            if(!validate.validator.notBlank(_n)){
                validate.error.table(_n, '未输入商品销售单价');
                _isValid = false;
                return;
            }
            if(!validate.validator.number(_n)){
                validate.error.table(_n, '销售单价必须输入数字');
                _isValid = false;
                return;
            }
            if(!validate.validator.range(_n, 1, 9999999999)){
                validate.error.table(_n, '销售单价最小数为1，最大不超过10位');
                _isValid = false;
                return;
            }
        });
        if(!_isValid){
            return false;
        }
    }
    //校验库存
    validate.clear.table(_totals);
    _totals.each(function(i,n){
        var _n = $(n);
        if(!validate.validator.notBlank(_n)){
            validate.error.table(_n, '未输入商品库存');
            _isValid = false;
            return;
        }
        if(!validate.validator.integer(_n)){
            validate.error.table(_n, '必须是整数');
            _isValid = false;
            return;
        }
        if(!validate.validator.range(_n, -9999999999, 9999999999)){
            validate.error.table(_n, '库存不超过10位');
            _isValid = false;
            return;
        }
        if(!validate.validator.notIn(_n, ['0'])){
            validate.error.formGroup(_n, '不能为0');
            _isValid = false;
            return;
        }
    });
    if(!_isValid){
        return false;
    }
    return true;
}

/**
 * 存在规格的校验，返回true/false表示通过或不通过校验
 * content：父级容器的选择器
 * specLevel: 规格级别，1：一级规格校验，2：一级和二级规格校验
 */
validate.specEdit = function(option){
    var _content = $(option.content);
    var _buyPrices = _content.find('input.sync-purchase-retinue');
    var _pointPrices = _content.find('input.sync-sale-retinue');
    var _amtPrices = _content.find('input.sync-sale-retinue2');
    var _totals = _content.find('input.sync-total-retinue');
    var _isValid = true;
    //校验采购价
    validate.clear.table(_buyPrices);
    _buyPrices.each(function(i,n){
        var _n = $(n);
        if(!validate.validator.notBlank(_n)){
            validate.error.table(_n, '未输入商品采购单价');
            _isValid = false;
            return;
        }
        if(!validate.validator.number(_n)){
            validate.error.table(_n, '必须输入数字');
            _isValid = false;
            return;
        }
        if(!validate.validator.range(_n, 0.01, 9999999999)){
            validate.error.table(_n, '最小数为0.01，最大不超过10位');
            _isValid = false;
            return;
        }
    });
    if(!_isValid){
        return false;
    }
    //校验销售积分
    if(validate.isExist(_pointPrices)){
        validate.clear.table(_pointPrices);
        _pointPrices.each(function(i,n){
            var _n = $(n);
            if(!validate.validator.notBlank(_n)){
                validate.error.table(_n, '未输入商品销售积分');
                _isValid = false;
                return;
            }
            if(!validate.validator.integer(_n)){
                validate.error.table(_n, '销售积分必须输入整数');
                _isValid = false;
                return;
            }
            if(!validate.validator.range(_n, 1, 9999999999)){
                validate.error.table(_n, '销售积分最小数为1，最大不超过10位');
                _isValid = false;
                return;
            }
        });
        if(!_isValid){
            return false;
        }
    }
    //校验销售价
    if(validate.isExist(_amtPrices)){
        validate.clear.table(_amtPrices);
        _amtPrices.each(function(i,n){
            var _n = $(n);
            if(!validate.validator.notBlank(_n)){
                validate.error.table(_n, '未输入商品销售单价');
                _isValid = false;
                return;
            }
            if(!validate.validator.number(_n)){
                validate.error.table(_n, '销售单价必须输入数字');
                _isValid = false;
                return;
            }
            if(!validate.validator.range(_n, 1, 9999999999)){
                validate.error.table(_n, '销售单价最小数为1，最大不超过10位');
                _isValid = false;
                return;
            }
        });
        if(!_isValid){
            return false;
        }
    }
    //校验库存
    validate.clear.table(_totals);
    _totals.each(function(i,n){
        var _n = $(n);
        if(!validate.validator.notBlank(_n)){
            validate.error.table(_n, '未输入商品库存');
            _isValid = false;
            return;
        }
        if(!validate.validator.integer(_n)){
            validate.error.table(_n, '必须是整数');
            _isValid = false;
            return;
        }
        if(!validate.validator.range(_n, -9999999999, 9999999999)){
            validate.error.table(_n, '库存不超过10位');
            _isValid = false;
            return;
        }
    });
    if(!_isValid){
        return false;
    }
    return true;
}

validate.val = function(elem){
    var val;
    switch (elem.prop('tagName')){
        case 'SELECT':
            val = elem.find('option:selected').val();
            break;
        default:
            val = elem.val();
            break;
    }
    return val;
}

/**
 * 检测是否获取到jquery对象
 */
validate.isExist = function(elem){
    return elem && (elem instanceof jQuery) && elem.length > 0;
}
/**
 * 提供通用的校验器
 */
validate.validator = {
    notBlank: function(elem){
        var val = validate.val(elem);
        if(!val){
            return false;
        }
        return true;
    },
    number: function(elem){
        var val = validate.val(elem);
        if(val){
            return $.isNumeric(val);
        }
        return false;
    },
    integer: function(elem){
        var val = validate.val(elem);
        if(val && $.isNumeric(val)){
            return val%1 === 0;
        }
        return false;
    },
    range: function(elem, min, max){
        var val = validate.val(elem);
        if(val && $.isNumeric(val)){
            return min <= val && val <= max;
        }
        return false;
    },
    notIn: function(elem, arr){
        var val = validate.val(elem);
        return arr.indexOf(val) < 0;
    }
};

validate.error = {
    formGroup: function(elem, msg){
        var _parent = elem.closest('.form-group');
        _parent.addClass('has-error');
        var _help = _parent.find('.help-block');
        if(_help && _help.length>0){
            _help.addClass('hide');
        }
        var _elemParent;
        if(elem.parent().hasClass('input-group')){
            _elemParent = elem.parent().parent();
        }else{
            _elemParent = elem.parent();
        }
        _elemParent.append('<span class="help-block m-b-none error"><i class="fa fa-times-circle"></i> '+ msg +'</span>');
    },
    table: function(elem, msg){
        elem.addClass('error');
        elem.parent().append('<span class="help-block m-b-none error-msg"><i class="fa fa-times-circle"></i> '+ msg +'</span>');
    }
};

validate.clear = {
    formGroup: function(elem){
        var _parent = elem.closest('.form-group');
        _parent.removeClass('has-error');
        _parent.find('.help-block.hide').removeClass('hide');
        _parent.find('.help-block.error').remove();
    },
    table: function(elem){
        elem.removeClass('error');
        elem.nextAll('.error-msg').remove();
    }
}

validate.msg = {
    error: function(msg, time){
        layer.msg(msg, {icon: 2, time: time?time:2000});
    }
}


