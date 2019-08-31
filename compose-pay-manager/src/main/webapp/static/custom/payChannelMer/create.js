$(document).ready(function () {
	//初始化校验
	//initValidate();
	initSignKeyModeRadio();
	//initDataKeyModeCheck();
	
	
});

//初始化，签名密钥切换
function initSignKeyModeRadio(){
	var signKeyModeCheck = $('.sign-key-mode').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });
    signKeyModeCheck.on('ifChecked', function(evn){
    	var keyMode = $(this).val();
    	switch (keyMode) {
		case '1':
			$('.sign-sym').removeClass('hide');
			$('.sign-asym').addClass('hide');
			break;
		case '2':
			$('.sign-sym').addClass('hide');
			$('.sign-asym').removeClass('hide');
			break;
		}
    });
}


function initDataKeyModeCheck(){
	var dataKeyModeCheck = $('.data-key-mode').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });
    dataKeyModeCheck.on('ifChecked', function(evn){
    	var keyMode = $(this).val();
    	switch (keyMode) {
		case '1':
			$('.data-sym').removeClass('hide');
			$('.data-asym').addClass('hide');
			break;
		case '2':
			$('.data-sym').addClass('hide');
			$('.data-asym').removeClass('hide');
			break;
		}
    });
}


//初始化校验
function initValidate(){
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#create-form").validate({
		rules: {
			"ticketMer.merName":{
				required:true,
				maxlength:60
			},
			"ticketMer.merMarketerId":{
				required:true
			},
			'ticketMer.busLinkman':{
				maxlength:30
			},
			'ticketMer.busContact':{
				maxlength:20
			}
		},
		messages: {
			"ticketMer.merName": {
				required:icon+'请填写合作商名称',
				maxlength:icon+'不能大于60个字符'
			},
			"ticketMer.merMarketerId": {
				required:icon+'请选择渠道联系人'
			},
			'ticketMer.busLinkman':{
				maxlength:icon+'不能大于30个字符'
			},
			'ticketMer.busContact':{
				maxlength:icon+'不能大于20个字符'
			}
		}
	});
}

