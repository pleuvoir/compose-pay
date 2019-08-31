/**
 * 本地存储
 * @type {{hname: string, isLocalStorage: boolean, dataDom: null, initDom: localData.initDom, set: localData.set, get: localData.get, remove: localData.remove}}
 */
localData = {
    isStorage: window.sessionStorage?true:false,
    storage: window.sessionStorage,

    set:function(key,value){
        if(this.isStorage){
            localData.storage.setItem(key,value);
        }
    },
    get:function(key){
        if(this.isStorage){
            return localData.storage.getItem(key);
        }
    },
    remove:function(key){
        if(this.isStorage){
            localData.storage.removeItem(key);
        }
    },
    clear:function(){
        if(this.isStorage){
            localData.storage.clear();
        }
    }
}