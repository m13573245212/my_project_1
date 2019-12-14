//当登录时异步验证用户名是否存在
$(function () {
    $('#userName').blur(function () {
        var userName=$('#userName').val();
        var header = $("meta[name='_csrf_header']").attr("content");
        var token =$("meta[name='_csrf']").attr("content");
        $.ajax({
            url:"user/checkUserName.do",
            data:"userName="+userName,
            type:"post",
            dataType:"json",
            //防止SpringSecurity的拦截
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success:function (obj) {
                //用户名有效时,启用按钮
                if(obj.state==1){
                    $("#user_login").attr("disabled",false);
                    //需要清空信息栏,否则用户如果一开始输入无效用户名,后来输入有效用户名,还是会有那一句"用户名不存在"
                    $("#information_login").text("");
                }else{
                    $("#user_login").attr("disabled",true);
                }

                $("#information_login").css("color","red");
                $("#information_login").css("font-family","微软雅黑");
                $("#information_login").css("font-weight","bold");

                if(obj.state==0){
                    $("#information_login").text(obj.message[0]);
                }
            }
        });
    });
});

//登录
$(function () {
    $("#user_login").click(function () {
        var userName=$('#userName').val();
        var password=$('#password').val();
        var header = $("meta[name='_csrf_header']").attr("content");
        var token =$("meta[name='_csrf']").attr("content");
        $.ajax({
            url:"user/login.do",
            data:"userName="+userName+"&password="+password,
            type:"post",
            dataType:"json",
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success:function (obj) {
                $("#information_login").css("font-family","微软雅黑");
                $("#information_login").css("font-weight","bold");
                if(obj.state==0){
                    $("#information_login").text(obj.message[0]);
                    $("#information_login").css("color","red");
                }else{
                    $("#myModal").modal("hide");
                    layer.alert("登录成功");
                }
            },
        });
    });
});

//注册时验证用户名是否存在
$(function () {
    $("#userName_reg").blur(function () {
        if($("#userName_reg").val()==""){
            $("#reg_checkUserName_information").html("用户名不能为空");
        }else{
            $("#reg_checkUserName_information").html("");
        }
        var header = $("meta[name='_csrf_header']").attr("content");
        var token =$("meta[name='_csrf']").attr("content");
        $.ajax({
            url:"user/checkUserName.do",
            data:"userName="+$('#userName_reg').val(),
            type:"post",
            dataType:"json",
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success:function (obj) {
                if(obj.state==1) {
                    $("#reg_checkUserName_information").html(obj.message[0]);
                    $("#user_reg").attr("disabled",true);
                }
            }
        });
    });
});

//注册提交
$(function () {
    $("#user_reg").click(function () {
        var userName=$("#userName_reg").val();
        var password=$("#password_reg").val();
        $.ajax({
            url:"user/reg.do",
            data:"userName="+userName+"&password="+password,
            type:"post",
            dataType:"json",
            success:function (obj) {
                $("#reg_result").append(obj.message[0]);
                $("#reg_result").css("font-family","微软雅黑");
                $("#reg_result").css("font-weight","bold");
                if(obj.state==0){
                    $("#reg_result").css("color","red");
                }
                if(obj.state==1){
                    $("#reg_result").css("color","green");
                }
           }
        });
    });
});

//验证密码是否为空
$(function () {
    $("#password_reg").blur(function () {
        if($("#password_reg").val()==""){
            $("#reg_password_information").html("密码不能为空");
        }else{
            $("#reg_password_information").html("");
        }
    });
});

//验证注册界面输入是否符合要求.(1.用户名是否为空;2.密码验证;)
$(function () {
    $("#password_reg_confirm").blur(function () {
        if($("#password_reg_confirm").val()!=$("#password_reg").val()){
            $("#reg_password_confirm_information").html("密码不一致");
            $("#password_reg_confirm").html("");
            $("#password_reg").html("");
        }else{
            $("#reg_password_confirm_information").html("");
        }
        if($("#userName_reg").val()!=""
            &&$("#password_reg").val()!=""
            &&$("#password_reg_confirm").val()!=""
            &&$("#reg_checkUserName_information").text()==""
            &&$("#reg_password_confirm_information").text()==""
            ){
            $("#user_reg").attr("disabled",false);
        }else{
            $("#user_reg").attr("disabled",true);
        }
    });
});
