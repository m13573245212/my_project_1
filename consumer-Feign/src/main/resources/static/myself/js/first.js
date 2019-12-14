var header = $("meta[name='_csrf_header']").attr("content");
var token =$("meta[name='_csrf']").attr("content");

function search_local() {
   $("a").css("color","");
    var text=document.getElementById("search_input_text").value;

    $.ajax({
        url:"search.do",
        data:"search_text="+text,
        type:"post",
        dataType:"json",
        success:function (obj) {
            if(obj.state==0){
                alert(obj.message[0]);
            }
            if(obj.state==1){
                var reds=obj.data;
                for(var i=0;i<reds.length;i++){
                    //得到“查看文件”列表中的li
                    var ul=document.getElementById("ul1");
                    var li_out=ul.getElementsByTagName("li");
                    for(var j=0;j<li_out.length;j++){
                        var a=li_out[j].firstChild;
                        if(a.innerHTML==reds[i]){
                            a.style.color="red";
                        }
                    }
                }
            }
        }
    });
}
function upload_local() {
    //创建一个设置表单数据的对象
    var formData = new FormData();
    //获取上传文件的文件对象
    var file = document.getElementById("fileUpload").files[0];
    formData.append("file", file);
    $.ajax({
        url:"upload.do",
        data:formData,
        type:"post",
        dataType:"json",
        //让系统不处理数据,原样上传
        contentType:false,
        processData:false,
        success:function (obj) {
            if(obj.state==0){
                $("#text_info").css("color","red");
            }
            if(obj.state==1){
                $("#text_info").css("color","green");
            }
            document.getElementById("text_info").innerHTML=obj.message[0];

            var timer=setInterval("f_countDown()",1000);
        }
    });
};

function upload_local_picture() {
    var master=document.getElementById("master").value;
    var formData=new FormData();
    var list=document.getElementById("fileUpload2").files;

    for(var i=0;i<list.length;i++){
        formData.append("pictures",list[i]);
    }
    formData.append("master",master);
    $.ajax({
        url:"uploadPictures.do",
        data:formData,
        type:"post",
        dataType:"json",
        //让系统不处理数据,原样上传
        contentType:false,
        processData:false,
        success:function (obj) {
            var strList=obj.message;
            for(var i=0;i<strList.length;i++){
                var str=strList[i];
                //有一个数组元素就说明有一条上传结果
                //每有一条结果就在p标签下生成两个子标签

                //判断结果,并设置对应的样式,已经提前在css设置
                if(str.indexOf("成功")!=-1){
                    $("#pictures_result").append("<span class='picture_result_1'>"+str+"</span><br/>");
                }
                if(str.indexOf("拒绝")!=-1){
                    $("#pictures_result").append("<span class='picture_result_2'>"+str+"</span><br/>");
                }
                if(str.indexOf("失败")!=-1){
                    $("#pictures_result").append("<span class='picture_result_3'>"+str+"</span><br/>");
                }
            }
            $("#pictures_result").append("<p class='text_count' id='text_count2'> </p>");
            var timer=setInterval("f_countDown2()",1000);
        }
    });
}

//设置倒计时最大时间
var maxTimer=8;
//主文件上传之后,倒计时
function f_countDown() {
    if(maxTimer>=0){
        //设置css协同
        var c=document.getElementById("text_info").style.color;
        document.getElementById("text_count").style.color=c;

        document.getElementById("text_count").innerHTML="XXXX  "+maxTimer+"秒后刷新";
        maxTimer--;
    }else{
        //倒计时结束后调用f_reload(),刷新页面,刷新页面同时又刷新maxTimer
        f_reload();
        clearInterval(timer);
    }
}
//从属文件上传之后,倒计时
function f_countDown2() {
    if(maxTimer>=0){

        document.getElementById("text_count2").innerHTML="   "+maxTimer+"秒后刷新";
        maxTimer--;
    }else{
        //倒计时结束后调用f_reload(),刷新页面,刷新页面同时又刷新maxTimer
        f_reload();
        clearInterval(timer);
    }
}

function f_reload() {
    //刷新页面
    location.reload();
}

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

//显示文件描述
function f_showDescribe(){
    var s=document.getElementById("describe_fileName").value;
    $.ajax({
        url:"showDescribe.do",
        data:"fileName="+s,
        type:"get",
        dataType:"json",
        success:function (obj) {
            document.getElementById("describe").innerText=obj.message[0];
        }
    });
}
//修改文件描述
function f_updateDescribe(){
    $.ajax({
        url:"updateDescribe.do",
        data:$("#describe_form").serialize(),
        type:"post",
        dataType:"json",
        success:function (obj) {
            if(obj.state==1){
                alert("更改成功,请重新查询")
            }else{
                alert("更改失败");
            }
        }
    });
}

$(document).ready( function () {
    //当模态彻底隐藏后触发,刷新
    $('#myModal #myModal3').on('hidden.bs.modal', function () {
        f_reload();
    });
    var header = $("meta[name='_csrf_header']").attr("content");
    var token =$("meta[name='_csrf']").attr("content");
    //表格
    $('#table_id_example').DataTable({
        ajax:{
            url:"table.do",
            type:"post",
            //防止SpringSecurity的拦截
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            //服务器返回的数据
            dataSrc:function (json) {
                return json.data;
            }
        },
        columns:[
            {data:'number'},
            {data:'mainFile'},
            {data:'describe'},
            {data:'sum',
                render:function (data,type,row,meta) {
                //获得主文件名
                var a=row.mainFile;

                //这里有一个变量定义时使用单引号和双引号的问题
                // var html='<a data-toggle="modal" data-target="#myModal3" onclick="f_to(\''+a+'\')">'+data+'</a>';

                //这里需要注意f_to()内部需要转义的是双引号,而不是单引号,因为已经使用了单引号了.
                var html="<a data-toggle='modal' data-target='#myModal3' onclick='f_to(\""+a+"\");'>"+data+"</a>";
                return html;
            }}
        ],
        destroy:true,//解决不能重复初始化的问题
        processing : true, //打开数据加载时的等待效果
        serverSide : false,//关闭后台分页
        ordering : true,//是否启用排序
        searching : false,//是否  启用模糊搜索
        //当处理大数据时，延迟渲染数据，有效提高DataTables处理能力
        deferRender : true,
        //设置中文
        language:{
            //左上角的分页步数显示
            lengthMenu : '<div style="float:left;height:33px;line-height:33px;overflow:hidden;text-align:center" >每页条数：</div><div style="float:left"><select class="form-control input-xsmall" style="float:right">'
            + '<option value="1">1</option>'
            + '<option value="5">5</option>'
            + '<option value="10">10</option>'
            + '<option value="20">20</option>'
            + '<option value="30">30</option>'
            + '<option value="40">40</option>'
            + '<option value="50">50</option>'
            + '</select></div>',
            paginate : {//分页的样式内容。
                previous : "上一页",
                next : "下一页",
                first : "第一页",
                last : "最后"
            },
            zeroRecords : "没有内容",//table tbody内容为空时，tbody的内容。
            //下面三者构成了总体的左下角的内容。
            info : "共 _PAGES_ 页, 显示第 _START_ 到第 _END_ 条, 共 _MAX_ 条 ",//左下角的信息显示，大写的词为关键字。
            infoEmpty : "0条记录",//筛选为空时左下角的显示。
            infoFiltered : " "//筛选之后的左下角筛选提示，
        }
    });
});

//查看图片
function f_lookPicture(pName,master) {
    master=master.replace(/\%/g,"%25");
    master=master.replace(/\#/g,"%23");
    master=master.replace(/\&/g,"%26");
    master=master.replace(/\ /g,"%20");
    master=master.replace(/\+/g,"%2B");
    master=master.replace(/\//g,"%2F");
    master=master.replace(/\?/g,"%3F");
    master=master.replace(/\=/g,"%3D");
    var img="<img class='picture_single' src='/yyy/lookPicture.do?pName="+pName+"&master="+master+"'>";
    $("#picture_area").html(img);
}

//删除图片
function f_deletePicture(pName,master) {
    $.ajax({
        url:"deletePicture.do",
        data:"pName="+pName+"&master="+master,
        type:"get",
        dataType:"json",
        success:function (obj) {
            alert(obj.message[0]);
            if(obj.state==1){
                //删除成功时,调用f_to刷新表格
                f_to(master);
            }
        }
    });
}

//图片集模态内容设置
function f_to(obj) {
    $("#myModalLabel3").html(obj);
    $('#table_pictures').DataTable({
        ajax:{
            url:"pictures_model.do",
            type:"post",
            data:function(d){
                d.masterName=obj;
            },
            //服务器返回的数据
            dataSrc:function (json) {
                return json.data;
            }
        },
        columns:[
            {data:'number'},
            {data:'pictureName'},
            {data:null,
                render:function (data,type,row,meta) {
                    var a=row.pictureName;
                    var html="<input type='button' value='查看' data-toggle='modal' data-target='#myModal4' onclick='f_lookPicture(\""+a+"\",\""+obj+"\");'>" +
                        "<input type='button' value='删除' onclick='f_deletePicture(\""+a+"\",\""+obj+"\");'>";
                    return html;
                }}
        ],
        destroy:true,//解决不能重复初始化的问题
        processing : false, //打开数据加载时的等待效果
        serverSide : false,//设置为客户端模式,关闭后台分页
        ordering : true,//是否启用排序
        searching : false,//是否  启用模糊搜索
        //当处理大数据时，延迟渲染数据，有效提高DataTables处理能力
        deferRender : true,
        //设置中文
        language:{
            //左上角的分页步数显示
            lengthMenu : '<div style="float:left;height:33px;line-height:33px;overflow:hidden;text-align:center" >每页条数：</div><div style="float:left"><select class="form-control input-xsmall" style="float:right">'
            + '<option value="1">1</option>'
            + '<option value="5">5</option>'
            + '<option value="10">10</option>'
            + '<option value="20">20</option>'
            + '<option value="30">30</option>'
            + '<option value="40">40</option>'
            + '<option value="50">50</option>'
            + '</select></div>',
            paginate : {//分页的样式内容。
                previous : "上一页",
                next : "下一页",
                first : "第一页",
                last : "最后"
            },
            zeroRecords : "没有内容",//table tbody内容为空时，tbody的内容。
            //下面三者构成了总体的左下角的内容。
            info : "共 _PAGES_ 页, 显示第 _START_ 到第 _END_ 条, 共 _MAX_ 条 ",//左下角的信息显示，大写的词为关键字。
            infoEmpty : "0条记录",//筛选为空时左下角的显示。
            infoFiltered : " "//筛选之后的左下角筛选提示，
        }
    });
}
//导航栏相关
function daoHang(){
    var element = "</br>" +
        "<div><button class='btn_s' id='btn_word' onclick='f_word()'>文档</button></div>" +
        "</br>" +
        "<div><button class='btn_s' id='btn_video' onclick='f_video()'>视频</button></div>" +
        "</br>" +
        "<div><button class='btn_s' id='btn_secret' onclick='f_show()'>设置</button></div>" +
        "</br>"+
        "<div><button class='btn_s' id='btn_secret' onclick='f_manage()'>注入</button></div>" +
        "</br>"+
        "<div><button class='btn_s' id='btn_secret' onclick='f_z()'>展示</button></div>" +
        "</br>"+
        "<div><button class='btn_s' id='btn_quit' onclick='#'>退出</button></div>"+
        "</br>";
    $('#DaoHangMenu').append(element);
}
//展示
function f_z(){
    $.ajax({
        url:"zhan/isLogin",
        type:"get",
        dataType: "json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (obj) {
            if("0"==obj){
                alert("请先登录");
            }else{
                var url = window.location.href + "zhan";
                if ($('#main_tab').tabs('exists', '展示')) {
                    $('#main_tab').tabs('select', '展示');
                } else {
                    $('#main_tab').tabs('add', {
                        title: '展示',
                        selected: true,
                        closable: true,
                        content: '<iframe id="iframe_manage" src="' + url + '" style="width:100%;height:100%;" ' +
                            ' frameborder="yes" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>'
                    });
                }
            }
        }
    });


}


//设置
function f_show(){
    $.ajax({
        url:"show/isLogin",
        type:"get",
        dataType: "json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (obj) {
            if("0"==obj){
                alert("请先登录");
            }else{
                var url = window.location.href + "show";
                if ($('#main_tab').tabs('exists', '设置')) {
                    $('#main_tab').tabs('select', '设置');
                } else {
                    $('#main_tab').tabs('add', {
                        title: '设置',
                        selected: true,
                        closable: true,
                        content: '<iframe id="iframe_manage" src="' + url + '" style="width:100%;height:100%;" ' +
                            ' frameborder="yes" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>'
                    });
                }
            }
        }
    });

}

//文档
function f_word() {
    $('#main_tab').tabs('select', '文档');
}
//视频
function f_video() {
    var url = window.location.href + "video";
    if ($('#main_tab').tabs('exists', '视频')) {
        $('#main_tab').tabs('select', '视频');
    } else {
        $('#main_tab').tabs('add', {
            title: '视频',
            selected: true,
            closable: true,
            content: '<iframe id="iframe_video" src="' + url + '" style="width:100%;height:100%;" ' +
            ' frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>'
        });
    }
}
//注入
function f_manage(){
    var url = window.location.href + "manage";
    if ($('#main_tab').tabs('exists', '注入')) {
        $('#main_tab').tabs('select', '注入');
    } else {
        $('#main_tab').tabs('add', {
            title: '注入',
            selected: true,
            closable: true,
            content: '<iframe id="iframe_manage" src="' + url + '" style="width:100%;height:100%;" ' +
                ' frameborder="yes" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>'
        });
    }
}

//页面加载时的首要任务，加载选项卡容器，容器中的默认选项卡加载列表，加载tree
window.onload=function () {
    list_onload();
    daoHang();
}
