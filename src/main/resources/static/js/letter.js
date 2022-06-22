$(function(){
	$("#sendBtn").click(send_letter);
	$(".close").click(delete_msg);
});

function send_letter() {
	$("#sendModal").modal("hide");

	//// 获取对方用户名和内容
	var toName = $("#recipient-name").val();
	var content = $("#message-text").val();

	$.post(
		//访问路径
		CONTEXT_PATH + "/letter/send",

		//以json字符串的形式传输的内容参数
		{"toName": toName, "content": content},

		// 回调完函数后，将服务器返回的数据传到data里
		function (data){
			//将json字符串转化成js对象
			data = $.parseJSON(data);
			if(data.code==0){
				$("#hintBody").text("发送成功！");
			}else{
				$("#hintBody").text(data.msg);
			}
			$("#hintModal").modal("show");
			setTimeout(function(){
				$("#hintModal").modal("hide");
				location.reload();
			}, 2000);
		}
	);
}

function delete_msg() {
	// TODO 删除数据
	$(this).parents(".media").remove();
}