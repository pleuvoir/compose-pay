$(document).ready(function () {
	
	//初始化校验
	initValidate();
	//标签-添加行
	$("#batch-add").bind("click", addBatch);
	//标签-移除行
	$(".batch-remove").bind("click", removeBatch);
	
	//添加轮播图片
	$("#add-img-btn").bind("click", addImageUpload);
	
    //datetimepicker
	$(".datepicker").datetimepicker({
    	format:"yyyy-mm-dd hh:ii",
    	language:"zh-CN",
    	todayHighlight:true,
    	todayBtn:true,
    	autoclose:true
    });
	
	//库存不可填
	readOnlyGoodsTotal();
	
}); 



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

//库存不可填
function readOnlyGoodsTotal(){
	$("#goodsInfoTotal").val("0");
	$("#goodsInfoTotal").attr("readonly","readonly")
}

//虚拟卡券
//$("#inlineRadio1").bind("click", readOnlyGoodsTotal);
//实物商品
$("#inlineRadio1").bind("click", function(){
	$("#goodsInfoTotal").removeAttr("readonly")
});
//实物商品
$("#inlineRadio2").bind("click", function(){
	$("#goodsInfoTotal").removeAttr("readonly")
});

//////////动态table开始//////////

//初始行数
var batchTrCount = 1;

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
	var tr = $(this).parent().parent();
	removeBatchCheck(tr);
	tr.remove();
	batchTrCount -= 1;
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

function addImageUpload(){
	imgIndex++;
	var tmp = $("#carouselImg-tlp").clone();
	tmp.find("img").removeAttr('src');
	tmp.find('input[type=file]').attr('name','carousel['+imgIndex+']');
	tmp.removeAttr('id');
	tmp.find('button').bind('click', rmImageUpload);
	tmp.removeClass("hide");
	$("#carouselImg-tlp").before(tmp);
}

function rmImageUpload(){
	$(this).parent().parent().parent().parent().remove();
}

//////////动态添加轮播图片结束//////////




