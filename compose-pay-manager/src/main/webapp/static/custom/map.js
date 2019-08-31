/**
 * 实现Map类型
 */
function Map() {
    this.elements = new Array();
    /**
     * 获取Map元素个数
     */
    this.size = function() {
        return this.elements.length;
    },
    // 判断Map是否为空
    this.isEmpty = function() {
        return (this.elements.length < 1);
    },

    // 删除Map所有元素
    this.clear = function() {
        this.elements.splice(0, this.elements.length);
    },

    /**
     * 向Map中增加元素（key, value)
     */
    this.put = function(_key, _value) {
        //检查map中是否存在key，存在则更新value
        var isUpdated = false;
        for(var i in this.elements){
            var _entry = this.elements[i];
            if(_entry.key === _key){
                this.elements[i].value = _value;
                isUpdated = true;
                break;
            }
        }
        //若更新了value，表示map中存在key，否则不存在则放入map
        if(!isUpdated){
            this.elements.push({
                key : _key,
                value : _value
            });
        }
    },

    // 删除指定key的元素，成功返回true，失败返回false
    this.remove = function(_key) {
        var bln = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key === _key) {
                    this.elements.splice(i, 1);
                    return true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    },

    // 获取指定key的元素值value，失败返回null
    this.get = function(_key) {
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key === _key) {
                    return this.elements[i].value;
                }
            }
        } catch (e) {
            return null;
        }
    },

    // 获取指定索引的元素（entry.key，entry.value获取key和value），失败返回null
    this.entry = function(_index) {
        if (_index < 0 || _index >= this.elements.length) {
            return null;
        }
        return this.elements[_index];
    },

    // 判断Map中是否含有指定key的元素
    this.containsKey = function(_key) {
        var bln = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key === _key) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    },

    // 判断Map中是否含有指定value的元素
    this.containsValue = function(_value) {
        var bln = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].value === _value) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    },

    // 获取Map中所有key的数组（array）
    this.keys = function() {
        var arr = new Array();
        for (var i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].key);
        }
        return arr;
    },

    // 获取Map中所有value的数组（array）
    this.values = function() {
        var arr = new Array();
        for (var i in this.elements) {
            arr.push(this.elements[i].value);
        }
        return arr;
    };

    this.forEach = function(callback){
        if(typeof callback === 'function'){
            for(var i in this.elements){
                var _entry = this.elements[i];
                callback.call(_entry, _entry.key, _entry.value);
            }
        }
    }
}