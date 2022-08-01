$(function(){
    $("form").submit(check_data);
    $("input").focus(clear_error);
    $("#uploadForm").submit(upload);  //点击表单的提交按钮，触发提交事件时，事件由 upload 函数进行处理
});

function check_data() {
    var pwd1 = $("#new-password").val();
    var pwd2 = $("#confirm-password").val();
    if(pwd1 != pwd2) {
        $("#confirm-password").addClass("is-invalid");
        return false;
    }
    return true;
}

function clear_error() {
    $(this).removeClass("is-invalid");
}

function upload() {
    $.ajax({
        url: "http://upload.qiniup.com",  // 声明提交路径，由七牛云规定
        method: "post",                      //请求方式
        processData: false,               // 声明不要把表单内容转换成字符串
        contentType: false,               // 声明JQuery 不要设置上传的类型，浏览器会自动设置。
        data: new FormData($("#uploadForm")[0]),  //$("#uploadForm") 是JQuery对象，$("#uploadForm")[0] 是js对象
        success: function(data) {
            // 操作成功了
            if(data && data.code == 0) {
                // 更新头像访问路径，仍然是异步处理，只不过是提交给服务器
                $.post(                   // 异步提交给服务器的 更新头像路径
                    CONTEXT_PATH + "/user/header/url",
                    {"fileName":$("input[name='key']").val()},  // 传入参数
                    function(data) {                            // 服务器的返回值
                        data = $.parseJSON(data);
                        if(data.code == 0) {
                            window.location.reload();
                        } else {
                            alert(data.msg);
                        }
                    }
                );
            } else {
                alert("上传失败!");
            }
        }
    });
    return false;  // 声明不要再提交了，上面的逻辑已经对表单进行了处理
}
