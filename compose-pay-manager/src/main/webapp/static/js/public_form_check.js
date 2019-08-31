/**
 * 检查form表单是否被修改
 * options.form 字符串(必须)，form表单选择器
 * options.elem 字符串(必须)，触发click事件的元素的选择器
 * options.modified 函数，当检查到已修改时被调用，
 * 			包含参数：form(表单jquery对象)、elem(触发click事件的元素jquery对象)、oldFormDataArray(原表单数据，格式见$form.serializeArray())、newFormDataArray(修改后的表单数据，格式见$form.serializeArray())
 * options.notModified 函数，当检查到未发生修改时被调用
 * 			包含参数：form(表单jquery对象)、elem(触发click事件的元素jquery对象)
 */
function checkFormModify(options){
	var $form = $(options.form);
	var $elem = $(options.elem);
	//保存原表单内容
	var oldFormDataArray = $form.serializeArray();
	//绑定单击事件
	$elem.on('click', function(){
		var newFormDataArray = $form.serializeArray();
		//检查表单是否被修改
		if(isFormModify(oldFormDataArray, newFormDataArray)){
			if($.isFunction(options.modified)){
				options.modified.call($form, $form, $elem, oldFormDataArray, newFormDataArray);
			}
		}else{
			if($.isFunction(options.notModified)){
				options.notModified.call($form, $form, $elem);
			}
		}
	});
}

function isFormModify(oldFormDataArray, newFormDataArray){
	for(var i in oldFormDataArray){
		var oldField = oldFormDataArray[i];
		for(var j in newFormDataArray){
			var newField = newFormDataArray[j];
			if(oldField.name==newField.name){
				if(oldField.value!=newField.value){
					return true;
				}
				break;
			}			
		}
	}
	return false;
}