function goodsInfoEditInit(options){
	//标签-初始化行数
	intiCoutn();
	//初始化校验
	initValidate();
	
	initTableValidate();
	//标签-添加行
	$("#batch-add").bind("click", addBatch);
	//标签-移除行
	$(".batch-remove").bind("click", removeBatch);
	
	//初始化轮播图片index数
	intiImageIndex(options.imgIndex);
	//添加轮播图片
	$("#add-img-btn").bind("click", addImageUpload);
	//删除轮播图片
	$(".delete-img-btn").bind("click", rmImageUpload);
	
    //datetimepicker
	$(".datepicker").datetimepicker({
    	format:"yyyy-mm-dd hh:ii",
    	language:"zh-CN",
    	todayHighlight:true,
    	todayBtn:true,
    	autoclose:true
    });
	
	//商品类型不可选
	$("input[name='goodsInfo.goodsClassify']").attr("disabled","disabled")
}

//初始化校验
function initValidate(){
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#edit-form").validate({
		rules: {
			"goodsInfo.goodsName":{
				required:true,
				maxlength: 20,
			},
			"goodsInfo.supplierId":{
				required:true,
			},
			"goodsInfo.sort":{
				required:true,
				digits:true,
			},
			"goodsInfo.goodsType":{
				required:true,
			},
			"goodsInfo.pointPrice":{
				required:true,
				digits:true,
				min:1,
				max:999999999,
			},
			"goodsInfo.amtPrice":{
				required:true,
				number:true,
				min:1,
				max:999999999,
			},
			startDate:{
				required:true,
			},
			endDate:{
				required:true,
			},
			"goodsInfo.virtualSaleTotal":{
				required:true,
				digits:true,
			},
			"goodsInfo.total":{
				required:true,
				digits:true,
			},
			"goodsInfo.expressFee":{
				required:true,
				min:1,
			},
			"goodsInfo.remark":{
				rangelength:[0,255],
			}
		},
		messages: {
			"goodsInfo.goodsName": {
				required:icon+'请输入商品名称',
				maxlength: icon+'商品名称最大20个字符',
			},
			"goodsInfo.supplierId": {
				required:icon+'请选择供应商',
			},
			"goodsInfo.sort": {
				required:icon+'请输入排序',
				digits:icon+'请输入整数',
			},
			"goodsInfo.goodsType": {
				required:icon+'请选择商品类型',
			},
			"goodsInfo.pointPrice": {
				required:icon+'请输入积分价格',
				digits:icon+'请输入整数',
				min: $.validator.format("请输入积分不小于 {0} 的数值"),
				max:icon+"积分价格不能超过999999999",
			},
			"goodsInfo.amtPrice": {
				required:icon+'请输入单价',
				number:icon+'请输入数字',
				max:icon+'单价不能超过999999999',
			},
			startDate: {
				required:icon+'请输入有效开始时间',
			},
			endDate: {
				required:icon+'请输入有效结束时间',
			},
			"goodsInfo.virtualSaleTotal":{
				required:icon+'请输入已售数量',
				digits:icon+'请输入整数',
			},
			"goodsInfo.total":{
				required:icon+'请输入库存数量',
				digits:icon+'请输入整数',
			},
			"goodsInfo.expressFee": {
				required:icon+'请输入运费',
			},
			"goodsInfo.remark":{
				rangelength:icon+'备注不能超过255个字',
			}
		}
	});
}

//运费设置联动运费
$("#expressFeeSet").change(function(){
	var set = $(this).val();	
	if(set == '0'){
		$("#expressFeeDiv").addClass("hide");
	}else if(set == '1'){
		$("#expressFeeDiv").removeClass("hide");
	}
});

//积分+现金
$("#inlineRadio3").bind("click", function(){
	$("#pointPriceDiv").removeClass("hide");
	$("#amtPriceDiv").removeClass("hide");
});

//积分
$("#inlineRadio4").bind("click", function(){
	$("#pointPriceDiv").removeClass("hide");
	$("#amtPriceDiv").addClass("hide");
});

//积分/现金
$("#inlineRadio5").bind("click", function(){
	$("#pointPriceDiv").addClass("hide");
	$("#amtPriceDiv").removeClass("hide");
});

//////////动态table开始//////////

//初始行数
var batchTrCount = 1;

//初始化行数
function intiCoutn(){
	batchTrCount = $("#tr-tpl tr").length;
}

//添加行
function addBatch(){
	//最多3行
	if(batchTrCount > 2){
		return;
	}
	batchTrCount += 1;
	var tr = $("#tbl-tpl tr").clone(false);
	tr.find(".batch-remove").removeClass("hide");	//显示移除按钮
	tr.find(".batch-remove").bind("click", removeBatch);	//绑定移除事件
	
	addBatchNameIncrease(tr);	//修改新添加行的name
	$("#tr-tpl").append(tr);	//添加克隆的行
	
	addBatchCheck(tr);		//添加校验
	
}

//移除行
function removeBatch(){
	if(batchTrCount>1){
		var tr = $(this).parent().parent();
		removeBatchCheck(tr);
		tr.remove();
		batchTrCount -= 1;
	}
}

//移除校验
function removeBatchCheck(tr){
	tr.find("input").rules("remove");
	tr.find("select").rules("remove");
}

//修改新添加行的name
function addBatchNameIncrease(tr){
	$.each(tr.find("input"), function(i,n){
		var curi = $(n);
		var name = curi.attr("name").replace("[0]", "[" + batchTrCount + "]");
		curi.attr("name", name);
	});
	$.each(tr.find("select"), function(i,n){
		var curs = $(n);
		var name = curs.attr("name").replace("[0]", "[" + batchTrCount + "]");
		curs.attr("name", name);
	});
}

function initTableValidate(){
	var table = $("#tr-tpl");
	$.each(table.find("tr"),function(i,n){
		var tr = $(n);
		addBatchCheck(tr);
	})
}

//添加校验
function addBatchCheck(tr){
	var icon = "<i class='fa fa-times-circle'></i> ";
	$.each(tr.find("input"), function(i,n){
		var curi = $(n);
		if(curi.hasClass("content")){
			curi.rules("add", {
				required:true, 
				maxlength:6,
				messages:{
					required:icon+"必须填写标签描述",
					maxlength:icon+'最大输入6个字符'
				}
			});
		}
	});
	
	$.each(tr.find("select"), function(i,n){
		var curs = $(n);
		if(curs.hasClass("tagImgId")){
			curs.rules("add", {
				required:true, 
				messages:{
					required:icon+"必须选择模版"
				}
			});
		}
	});
}

//////////动态table结束//////////

//////////动态添加轮播图片开始//////////

var imgIndex = 0;

//初始化轮播图片index数
function intiImageIndex(_imgIndex){
	imgIndex = _imgIndex;
	
	//初始化需要删除的id集合为""
	$("#deleteIds").val("");
}

function addImageUpload(){
	imgIndex++;
	var tmp = $("#carouselImg-tlp").clone();
	tmp.find("img").removeAttr('src');
	tmp.find('input[type=file]').attr('name','carouselStr['+imgIndex+']');
	tmp.removeAttr('id');
	tmp.find('button').bind('click', rmImageUpload);
	tmp.removeClass("hide");
	$("#carouselImg-tlp").before(tmp);
}

function rmImageUpload(){
	$(this).parent().parent().parent().parent().remove();
	var deleteStr = $("#deleteIds").val();
	var name = $(this).closest('.form-group').find('input[type=file]').attr("name");
	var str = name.replace("carouselStr['", "");
	str = str.replace("']", "");
	str = str.replace("carouselStr[", "");
	str = str.replace("]", "");
	deleteStr = deleteStr + ":" + str;
	$("#deleteIds").val(deleteStr);
	
}

//////////动态添加轮播图片结束//////////




