$(document).ready(function () {
	//初始化校验
	initValidate();
	
	var selectPubBank = $('#select-pubBank');
	selectPubBank.select2();
	selectPubBank.on('select2:select', function(e){
		var elem = e.params.data.element;
		if(elem && elem.value){
			addPubBank(elem.value, elem.innerHTML);
		}
	});
});

var pubBankIndex = 0;
function addPubBank(code, name){
	if(!code){
		return;
	}
	//重复添加银行判断
	var count = $('#pubBank-hide input[value='+code+']').length;
	if(count>0){
		return;
	}
	var pubBankBtn = $('<button type="button" id="pubBank_' + pubBankIndex + '" class="btn btn-info btn-sm btn-outline btn-rounded m-r-xs">' + name + ' <i class="fa fa-close"></i></button>');
	pubBankBtn.on('click', rmPubBank);
	$('#pubBank-show').append(pubBankBtn);
	$('#pubBank-hide').append('<input type="hidden" id="hide_pubBank_' + pubBankIndex + '" name="code[' + pubBankIndex + ']" value="' + code + '">');
	pubBankIndex++;
}


function rmPubBank(){
	var btn = $(this);
	var btnId = btn.attr('id');
	btn.remove();
	$('#hide_'+btnId).remove();
}



//初始化校验
function initValidate(){
	
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#create-form").validate({
		rules: {
			"payTypeBank.payType":{
				required:true,
			},
			"payTypeBank.bankCode":{
				required:true,
			}
		
			
		},
		messages: {
			"payTypeBank.payType": {
				required:icon+'请选择支付产品',
			},
			"payTypeBank.bankCode": {
				required:icon+'请选择银行名称',
			}
			
		}
	});
}

