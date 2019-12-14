//页面加载时启动,主要目的:1.设置登录按钮为不可用状态;2.加载需要展示的文件的超链接;
function list_onload(){
    $.ajax({
        url:"list.do",
        data:"",
        type:"get",
        dataType:"json",
        success:function (obj) {
            var list_files=obj.data;
            for(var i=0;i<list_files.length;i++){
                //这里的file是String数组类型的
                var file=list_files[i];
                //解决特殊字符不能作为参数的问题
                var s=file[1];
                s=s.replace(/\%/g,"%25");
                s=s.replace(/\#/g,"%23");
                s=s.replace(/\&/g,"%26");
                s=s.replace(/\ /g,"%20");
                s=s.replace(/\+/g,"%2B");
                s=s.replace(/\//g,"%2F");
                s=s.replace(/\?/g,"%3F");
                s=s.replace(/\=/g,"%3D");
                var childFile = "<li><a href='../yyy/look.do?fileName="+s+"' target='_Blank'>"+file[1]+"</a></li>";
                $("#ul1").append(childFile);
            }
        }
    });
    //设置"登录"按钮不可用,因为用户还没有输入用户名
    $("#user_login").attr("disabled",true);
    //设置"注册"按钮不可用
    $("#user_reg").attr("disabled",true);
}



//页面加载时的首要任务，加载选项卡容器，容器中的默认选项卡加载列表，加载tree
window.onload=function () {
    list_onload();
}
