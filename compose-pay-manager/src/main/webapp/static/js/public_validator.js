//以下为修改jQuery Validation插件兼容Bootstrap的方法
$.validator.setDefaults({
    highlight: function (element) {
        $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
    },
    success: function (element) {
        element.closest('.form-group').removeClass('has-error').addClass('has-success');
    },
    errorElement: "span",
    errorPlacement: function (error, element) {
    	if(element.parent().hasClass('input-group')){
    		element.parent().nextAll('.help-block').remove();
    		error.appendTo(element.parent().parent());
    	}else{
    		element.nextAll('.help-block').remove();
    		if (element.is(":radio") || element.is(":checkbox")) {
                error.appendTo(element.parent().parent().parent());
            } else {
                error.appendTo(element.parent());
            }
    	}
        
    },
    errorClass: "help-block m-b-none",
//    validClass: "help-block m-b-none"
});
