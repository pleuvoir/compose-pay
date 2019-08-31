	/*$(document).ready(function () {
		initValidate();
		initKeyTypeCheckbox();
		initParamTypeCheck();
		
		<c:if test="${merParam1!=null && merParam1.type=='1'}">
			addParamTypeRule('1');
		</c:if>
		<c:if test="${merParam2!=null && merParam2.type=='2'}">
			addParamTypeRule('2');
		</c:if>
		<c:if test="${merParam3!=null && merParam3.type=='3'}">
			addParamTypeRule('3');
		</c:if>
	}); 
	//初始化兑换模式
	function initKeyTypeCheckbox(){
		var keyTypeCheck = $('.exchange-type').iCheck({
	        checkboxClass: 'icheckbox_square-green',
	        radioClass: 'iradio_square-green',
	    });
		keyTypeCheck.on('ifChecked', function(evn){
	    	var keyType = $(this).val();
	    	switch (keyType) {
			case '1':
				$('.exchange-in-flag').removeClass('hide');
				break;
			case '2':
				$('.exchange-out-flag').removeClass('hide');
				break;
			}
	    });
		keyTypeCheck.on('ifUnchecked', function(evn){
	    	var keyType = $(this).val();
	    	switch (keyType) {
			case '1':
				$('.exchange-in-flag').addClass('hide');
				$('.exchange-in-flag input[type=text]').val('');
				break;
			case '2':
				$('.exchange-out-flag').addClass('hide');
				$('.exchange-out-flag input[type=text]').val('');
				break;
			}
	    }) 
	}

	//初始化接口类型
	function initParamTypeCheck(){
		var typeCheck = $('.param-type').iCheck({
	        checkboxClass: 'icheckbox_square-green',
	        radioClass: 'iradio_square-green',
	    });
		
		typeCheck.on('ifChecked', function(evn){
	    	var type = $(this).val();
	    	switch (type) {
			case '1':
				$('.type-1-flag').removeClass('hide');
				addParamTypeRule(type);
				break;
			case '2':
				$('.type-2-flag').removeClass('hide');
				addParamTypeRule(type);
				break;
			case '3':
				$('.type-3-flag').removeClass('hide');
				addParamTypeRule(type);
				break;
			}
	    });
		typeCheck.on('ifUnchecked', function(evn){
	    	var type = $(this).val();
	    	switch (type) {
			case '1':
				$('.type-1-flag').addClass('hide');
				removeParamTypeRule(type);
				break;
			case '2':
				$('.type-2-flag').addClass('hide');
				removeParamTypeRule(type);
				break;
			case '3':
				$('.type-3-flag').addClass('hide');
				removeParamTypeRule(type);
				break;
			}
	    }) 
	}


	//添加接口类型规则
	function addParamTypeRule(paramType){
		var icon = "<i class='fa fa-times-circle'></i> ";
		$('input[name="merParam['+paramType+'].url"]').rules('add', {
			required:true,
			url:true,
			maxlength:255,
			messages:{
				required: icon+'不能为空',
				url: icon+'URL格式错误',
				maxlength: icon+'长度不能超过{0}',	
			}
		});
		
		$('textarea[name="merParam['+paramType+'].extendParam"]').rules('add', {
			maxlength:4000,
			messages:{
				maxlength: icon+'长度不能超过{0}',
			}
		});

		$('select[name="merParam['+paramType+'].status"]').rules('add', {
			required:true,
			messages:{
				required: icon+'不能为空',
			}
		});
	}
	//删除接口类型规则
	function removeParamTypeRule(paramType){
		$('input[name="merParam['+paramType+'].url"]').rules('remove');
		$('textarea[name="merParam['+paramType+'].extendParam"]').rules('remove');
		$('select[name="merParam['+paramType+'].status"]').rules('remove');
	}


	function initValidate(){
		//自定义正则表达示验证方法  
	   	jQuery.validator.addMethod("checkMerName",function(value,element){  
	            var checkMerName = /^(?!\d*$)/; 
	            return this.optional(element)||(checkMerName.test(value));  
	        },"商户名称不能全为数字！");
	    //自定义正则表达示验证方法  
		   jQuery.validator.addMethod("checkRate",function(value,element){  
		            var checkRate = /^[0-9]*[1-9][0-9]*$/;  "^[0-9]*[1-9][0-9]*$" 
		            return this.optional(element)||(checkRate.test(value));  
		        },"输入的倍数必须为正整数！");
		var icon = "<i class='fa fa-times-circle'></i> ";
		$("#edit-form").validate({
			rules: {
				'busMer.merName':{
					required:true,
					maxlength:30,	
					checkMerName:true
				},
				'busMer.merAlias':{
					required:true,
					maxlength:30,	
					checkMerName:true
				},	
				'busMer.merMarketerId':{
					required:true,
				},
			},
			messages: {
				'busMer.merName':{
					required:'请输入商户名称',	
					maxlength:'长度不能超过30位字符',	
				},	
				'busMer.merAlias':{
					required:'请输入商户简称',	
					maxlength:'长度不能超过30位字符',
					checkMerName:'商户简称不能全为数字！'
				},	
				'busMer.merMarketerId': icon+'请选择渠道联系人',	
			}
		});
		$("#edit-form2").validate({
		    		rules: {
		    			'exchangeType':{
		    				required:true
		    			},
		    			'inMerRate':{
		    				required:true,
		    				digits:true,
		    				maxlength:10,
		    				checkRate:true
		    			},
		    			'inRate':{
		    				required:true,
		    			 	digits:true,
		    				maxlength:10,
		    				checkRate:true
		    			},
		    			'outMerRate':{
		    				required:true,
		    				digits:true,
		    				maxlength:10,
		    				checkRate:true
		    			},
		    			'outRate':{
		    				required:true,
		    				digits:true,
		    				maxlength:10,
		    				checkRate:true
		    			}
		    		},
		    		messages: {
	    			 'exchangeType': {
	    				 required:'请选择兑换类型'
					 },	
					 'inMerRate': {
						 required:'请输入商户兑入倍数',
						 digits:"必须为正整数",
						 maxlength:"不能大于10个字符",
					 },	
					 'inRate': {
						 required:'请输入聚积分兑入倍数',
						 digits:"必须为正整数",
						 maxlength:"不能大于10个字符",
					 },	
					 'outMerRate': {
						 required:'请输入商户兑入出倍数',
						 digits:"必须为正整数",
						 maxlength:"不能大于10个字符",
					 },	
					 'outRate': {
						 required:'请输入聚积分兑出倍数',
						 digits:"必须为正整数",
						 maxlength:"不能大于10个字符",
					 }	
		    	}
			});
		}
*/	