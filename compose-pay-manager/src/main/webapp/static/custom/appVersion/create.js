//初始化，传入一些参数
var appVersionOpt = {
	uploadUrl: '',			//上传的URL
	uploadSwf: '',			//上传组件的swf文件地址
	apkIndex: 0				
}

function initOptions(opt){
	$.extend(appVersionOpt, opt);
	return appVersionOpt;
}


function addUpload(){
	appVersionOpt.apkIndex++;
	var tmpl = $("#upload-template").clone();
	
	tmpl.removeClass('hide');
	tmpl.removeAttr('id');
	
	tmpl.find('#picker-X').attr('id','picker-'+appVersionOpt.apkIndex);
	tmpl.find('#apk-show-X').attr('id', 'apk-show-'+appVersionOpt.apkIndex);
	
	renameAddUpload(tmpl);
	rmUpload(tmpl);
	$("#upload-template").before(tmpl);
	
	webUploaderControl({
	    swf: appVersionOpt.uploadSwf,
	    server: appVersionOpt.uploadUrl,
		btnSelector: '#picker-'+appVersionOpt.apkIndex,
		showSelector: '#apk-show-'+appVersionOpt.apkIndex,
		appUrlInput: 'input[name="details['+appVersionOpt.apkIndex+'].appUrl"]', 
		appNameInput: 'input[name="details['+appVersionOpt.apkIndex+'].appName"]',
		name: 'file'
	});
}

function renameAddUpload(tmpl){
	tmpl.find('select[data-name="marketId"]').attr('name', 'details['+appVersionOpt.apkIndex+'].marketId');
	tmpl.find('input[data-name="appUrl"]').attr('name','details['+appVersionOpt.apkIndex+'].appUrl');
	tmpl.find('input[data-name="appName"]').attr('name','details['+appVersionOpt.apkIndex+'].appName');
}

function rmUpload(tmpl){
	tmpl.find('.rm-upload').on('click', function(){
		$(this).closest('.form-group').remove();
	})
}

/*
 * option.swf swf文件路径
 * option.server 上传服务器地址
 * option.btnSelector	上传按钮选择器
 * option.showSelector 上传进度选择器
 * option.appUrlInput 成功上传后纪录返回的文件URL
 * option.appNameInput 成功上传后纪录返回的文件名
 * option.name 上传文件的参数名
 */
function webUploaderControl(option){
	var _uploader = WebUploader.create({
	    // swf文件路径
	    swf: option.swf,
	    // 文件接收服务端。
	    server: option.server,
	    pick: {
	    	id: option.btnSelector,
	    	innerHTML: '上传文件',
	    	multiple: false
	    },
	    fileVal: option.name,
	    compress: null,
	    resize: false,
	    auto: true,
	    chunked: false,
	    fileNumLimit: 1,
	    fileSizeLimit: 1073741824,
	    accept: {
	    	title:'Apk',
	    	extensions: 'apk',
	    }
	});
	
	_uploader.on('error', function(type){
		switch (type) {
		case 'Q_TYPE_DENIED':
			sweetAlert("文件类型错误", "只接受apk","error");
			break;
		case 'Q_EXCEED_NUM_LIMIT':
			sweetAlert("文件数量错误", "只能上传一个文件","error");
			break;
		case 'Q_EXCEED_SIZE_LIMIT':
			sweetAlert("文件大小错误", "超过文件大小限制","error");
			break;
		default:
			break;
		}
	});
	
	_uploader.on('uploadError', function(file, reason){
		sweetAlert("文件上传失败", 'err', "error");
	});
	
	var _appUrlInput = $(option.appUrlInput);
	var _appNameInput = $(option.appNameInput);
	_uploader.on('uploadSuccess', function(file, response){
		if(response && response.status=='success'){
    		swal("成功", response.message, "success");
    		
    		_appUrlInput.val(response.data.fileId);
    		_appNameInput.val(response.data.filename);
		}
		if(response && response.status=='error'){
			swal("失败", response.message, "error");
		}
	});
	
	var _apkShow = $(option.showSelector);
	_uploader.on('uploadProgress', function(file, percentage){
		_apkShow.html(Math.round( percentage * 100 ) + '%');
	});
	
	return _uploader;
}

//校验初始化
function initValidator(versionCheckUrl){
	$.validator.addMethod("version", function(value, element) {   
	    var ver = /^(\d+\.){2}\d+$/;
	    return this.optional(element) || (ver.test(value));
	}, "请输入正确的版本号，例：1.0.0");
	
	var icon = "<i class='fa fa-times-circle'></i> ";
	var publicRules = {
		'appVersion.description':{
			required:true,
			maxlength:4000
		},	
		'appVersion.type':'required',
	};
	var publicMessages = {
		'appVersion.description': {
			required: icon+'不能为空',
			maxlength: icon+'长度不能超过{0}'
		},
		'appVersion.version': {
			required: icon+'不能为空',
			maxlength: icon+'长度不能超过{0}',
			version: icon+"请输入正确的版本号，例：1.0.0",
			remote: icon+'版本号已存在'
		},
		'appVersion.type': icon+'请选择升级类型',
	};
	
	var androidRules = {
		'appVersion.version':{
			required: true,
			maxlength: 30,
			version: true,
			remote: {
				url: versionCheckUrl,
				data: {
					version: function(){
						return $('#android-form input[name="appVersion.version"]').val();
					},
					clientSystem: '1'
				}
			}
		}
	};
	$.extend(androidRules, publicRules);
	
	//android 的表单
	$("#android-form").validate({
		rules: androidRules,
		messages: publicMessages,
	});
	
	
	var iosRules = {
		'details[0].appUrl':{
			required: true,
			url:true,
			maxlength: 255
		},
		'appVersion.version':{
			required: true,
			maxlength: 30,
			version: true,
			remote: {
				url: versionCheckUrl,
				data: {
					version: function(){
						return $('#ios-form input[name="appVersion.version"]').val();
					},
					clientSystem: '2'
				}
			}
		}
	};
	$.extend(iosRules, publicRules);
	
	var iosMessages = {
		'details[0].appUrl': {
			required: icon+'不能为空',
			url: icon+'不是有效的发布地址',
			maxlength: icon+'长度不能超过{0}'
		}	
	};
	$.extend(iosMessages, publicMessages);
	//ios 的表单
	$("#ios-form").validate({
		rules: iosRules,
		messages: iosMessages,
	});
}

function initEditValidator(versionCheckUrl, oldVersion){
	$.validator.addMethod("version", function(value, element) {   
	    var ver = /^(\d+\.){2}\d+$/;
	    return this.optional(element) || (ver.test(value));
	}, "请输入正确的版本号，例：1.0.0");
	
	var icon = "<i class='fa fa-times-circle'></i> ";
	var publicRules = {
		'appVersion.description':{
			required:true,
			maxlength:4000
		},	
		'appVersion.type':'required',
	};
	var publicMessages = {
		'appVersion.description': {
			required: icon+'不能为空',
			maxlength: icon+'长度不能超过{0}'
		},
		'appVersion.version': {
			required: icon+'不能为空',
			maxlength: icon+'长度不能超过{0}',
			version: icon+"请输入正确的版本号，例：1.0.0",
			remote: icon+'版本号已存在'
		},
		'appVersion.type': icon+'请选择升级类型',
	};
	
	var androidRules = {
		'appVersion.version':{
			required: true,
			maxlength: 30,
			version: true,
			remote: {
				url: versionCheckUrl,
				data: {
					version: function(){
						return $('#android-form input[name="appVersion.version"]').val();
					},
					excludeVersion: oldVersion,
					clientSystem: '1'
				}
			}
		}
	};
	$.extend(androidRules, publicRules);
	
	//android 的表单
	$("#android-form").validate({
		rules: androidRules,
		messages: publicMessages,
	});
	
	
	var iosRules = {
		'details[0].appUrl':{
			required: true,
			url:true,
			maxlength: 255
		},
		'appVersion.version':{
			required: true,
			maxlength: 30,
			version: true,
			remote: {
				url: versionCheckUrl,
				data: {
					version: function(){
						return $('#ios-form input[name="appVersion.version"]').val();
					},
					excludeVersion: oldVersion,
					clientSystem: '2'
				}
			}
		}
	};
	$.extend(iosRules, publicRules);
	
	var iosMessages = {
		'details[0].appUrl': {
			required: icon+'不能为空',
			url: icon+'不是有效的发布地址',
			maxlength: icon+'长度不能超过{0}'
		}	
	};
	$.extend(iosMessages, publicMessages);
	//ios 的表单
	$("#ios-form").validate({
		rules: iosRules,
		messages: iosMessages,
	});
}

