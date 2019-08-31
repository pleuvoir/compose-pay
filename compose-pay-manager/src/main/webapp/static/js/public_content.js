var $parentNode = window.parent.document;

function $childNode(name) {
    return window.frames[name]
}

/**
 * 当ajax请求时，若返回的json为{code:'',msg:''}，将会弹出提示框
 * 依赖swal
 */
$.ajaxSetup({
	dataFilter: function(data, type){
		if(type=='json'){
			if(data.indexOf('data-auth-url=') > 0){
				swal ({
					title: "登录超时",
					text: "点击确定按钮重新登录",
					icon: "warning",
					button: "确定",
					closeOnClickOutside: false
				}).then(function(will){
					if(will){
						window.location.href = $(data).data('auth-url');
					}
				});
				return data;
			}
			if(data.indexOf('code')>0 && data.indexOf('msg')>0){
				var json = $.parseJSON(data);
				if(json.code!='SUCCESS'){
                    sweetAlert(json.code, json.msg, 'warning');
				}
			}
		}
		return data;
	},
});

