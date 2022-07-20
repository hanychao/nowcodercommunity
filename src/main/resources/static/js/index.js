$(function () {
    $("#publishBtn").click(publish);
});

function publish() {
    //点击发布按钮，隐藏发布页面
    $("#publishModal").modal("hide");

      //发送AJAX请求之前，将CSRF令牌设置到消息头中
    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    // $(document).ajaxSend(function (e,xhr,options) {
    //         xhr.setRequestHeader(header,token);
    // })

    // 获取标题和内容
    var title = $("#recipient-name").val();
    var content = $("#message-text").val();

    //发送异步请求
    $.post(
    	//访问路径
        CONTEXT_PATH + "/discuss/add",

		//以json字符串的形式传输的内容参数
        {"title": title, "content": content},

        // 回调完函数后，将服务器返回的数据传到data里
        function (data) {
        	//将json字符串转化成js对象
            data = $.parseJSON(data);
            // 在提示框中显示返回消息
            $("#hintBody").text(data.msg);
            // 显示提示框, 提示已发布完成
            $("#hintModal").modal("show");
            // 2s 后自动隐藏提示框
            setTimeout(function () {
                $("#hintModal").modal("hide");
                // 刷新页面
                if (data.code == 0) {
                   window.location.reload();
                }
            }, 2000);
        }
    );
}