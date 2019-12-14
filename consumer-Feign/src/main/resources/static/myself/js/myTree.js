//加载tree
$(function(){
    //向后台请求加载tree
    $.ajax({
        url:"show/tree",
        type:"get",
        dataType:"text",
        success: function (obj) {
            var t=eval(obj);
            $("#treeview").treeview({
                data: t,
                levels: 3,
                color: "#428bca",
                onNodeSelected: function (event,node) {
                    var a=" <div id='set_tdiv'>" +
                        "       <input type='text' id='node_text_input' placeholder='节点名称'>\n" +
                        "       <input id='z' type='button' onclick='add_node("+JSON.stringify(node)+")' value='新增'>" +
                        "       <input id='x' type='button' onclick='update_node("+JSON.stringify(node)+")' value='修改'>" +
                        "       <input id='s' type='button' onclick='del_node("+JSON.stringify(node)+")' value='删除'>" +
                        "       <p id='node_information'>&nbsp;</p>"+
                        "       <select id='select2_sample' style='width:74%;height:30px;'><option>---请选择---</option></select>"+
                        "       <input type='button' onclick='conn("+JSON.stringify(node)+")' value='建立链接'>"+
                        "   </div>";
                    layer.alert(a);
                    $.ajax({
                        url:"list.do",
                        data:"",
                        type:"get",
                        dataType:"json",
                        success:function (obj) {
                            var list_files=obj.data;
                            for(var i=0;i<list_files.length;i++){
                                $("#select2_sample").append("<option>"+list_files[i][1]+"</option>");
                            }
                        }
                    });

                }
            });
        }
    });
});
//获取所有节点
function getAllNodes(){
    var treeViewObject = $('#treeview').data('treeview'),
        allCollapsedNodes = treeViewObject.getCollapsed(),
        allExpandedNodes = treeViewObject.getExpanded(),
        allNodes = allCollapsedNodes.concat(allExpandedNodes);
    return allNodes;
}

//建立链接
function conn(node) {

    if ($("#select2_sample").val()==""||$("#select2_sample").val()=="---请选择---"){
        $('#node_information').text("请选择链接文件");
        $("#node_information").css("color","red");
        return;
    }else{
        $('#node_information').text("  ");
    }

    //只有根节点可以关联
    var allN=getAllNodes();
    for(var i=0;i<allN.length;i++){
        if(allN[i].parentId==node.nodeId){
            $('#node_information').text("只有根节点可以关联MD文件");
            $("#node_information").css("color","red");
            return;
        }
    }
    //向后台发起建立链接的请求
    $.ajax({
        url:"show/conn",
        data:"nodeId="+node.id+"&&des="+$("#select2_sample").val(),
        type:"get",
        dataType:"json",
        success: function (obj) {
            if(obj.state==0){
                $('#node_information').text("链接成功");
                $("#node_information").css("color","green");

                //需要在页面上标识
                $("#treeview").treeview("editNode", [node.nodeId,{ text: node.text+" ( "+$("#select2_sample").val()+" ) ", id: node.id }]);
            }else if(obj.state==1){
                $('#node_information').text(obj.message[0]);
                $("#node_information").css("color","red");
            }else if(obj.state==2){
                $('#node_information').text("链接成功");
                $("#node_information").css("color","green");

                //需要在页面上标识,对于本来就存在链接的需要将原来的替换
                var final=node.text.replace(/\([\s\S]*\)/g,"( "+$("#select2_sample").val()+" )");
                $("#treeview").treeview("editNode", [node.nodeId,{ text: final, id: node.id }]);
            }
        }
    });

}
//增
function add_node(node) {
    if($("#node_text_input").val()==""){
        $('#node_information').text("未输入节点名称");
        $("#node_information").css("color","red");
    }else{
        $('#node_information').text(" ");
        // console.log(node);

        //向后台发起请求,请求新的节点ID
        var newId=0;
        $.ajax({
            url:"show/getNewNodeId",
            type:"get",
            dataType:"text",
            async: false,
            success: function (res) {
                console.log(res);
                if(res==-1){
                    alert("登录超时,需要重新登录");
                }else{
                    newId=res;
                }
            }
        });

        var nodeText=$("#node_text_input").val();

        //页面上添加节点
        $("#treeview").treeview("addNode",[node.nodeId,{ node: { text: nodeText, id: newId }, silent: true }]);
        $.ajax({
            url:"show/addPoint",
            data:"fartherId="+node.id+"&&nodeText="+nodeText+"&&nodeId="+newId,
            type:"get",
            dataType: "json",
            async: false,
            success: function (obj) {
                if(obj.state==0){
                    $('#node_information').text("添加成功");
                    $("#node_information").css("color","green");
                }else{
                    $('#node_information').text(obj.message[0]);
                    $("#node_information").css("color","red");
                }
            }
        });
    }
}
//删
function del_node(node) {
    if(node.id==0){
        $('#node_information').text("根节点不能删除");
        $("#node_information").css("color","red");
    }else{
        //通知后台进行删除
        $.ajax({
            url:"show/deletePoint",
            data:"nodeId="+node.id, //需要注意这里的nodeId取自node对象的id属性
            type:"get",
            dataType:"json",
            success:function (obj) {
                if(obj.state==0){
                    //页面上删除
                    $("#treeview").treeview("deleteNode", [node.nodeId, { silent: true }]);
                    $('#node_information').text(obj.message[0]);
                    $("#node_information").css("color","green");
                }else{
                    $('#node_information').text(obj.message[0]);
                    $("#node_information").css("color","red");
                }
            }
        });
    }


}
//改
function update_node(node) {
    var tex=$("#node_text_input").val();
    var a=$('#treeview').treeview('getParent', node);
    if(tex==""){
        $('#node_information').text("修改时需要输入节点名称");
        $("#node_information").css("color","red");
    }else{
        $.ajax({
            url:"show/updatePoint",
            data:"nodeId="+node.id+"&&nodeText="+tex,
            type:"get",
            dataType:"json",
            success: function (obj) {
                if (obj.state==0){
                    $("#treeview").treeview("editNode", [node.nodeId,{ text: tex, id: node.id }]);
                    $('#node_information').text(obj.message[0]);
                    $("#node_information").css("color","green");
                } else if(obj.state==1){
                    $('#node_information').text(obj.message[0]);
                    $("#node_information").css("color","red");
                }else if(obj.state==2){
                    var tex2=tex+" ( "+obj.message[1]+" ) ";
                    $("#treeview").treeview("editNode", [node.nodeId,{ text: tex2, id: node.id }]);
                    $('#node_information').text(obj.message[0]);
                    $("#node_information").css("color","green");
                }
            }
        });
    }


}

