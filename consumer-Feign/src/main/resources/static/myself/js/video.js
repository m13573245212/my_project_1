window.onload=function () {
    //加载视频列表

    //1.拿到返回的文件的文件名和ID
    var videosList;
    var u=window.location.href+"/list";
    $.ajax({
        url:u,
        type:"get",
        dataType:"json",
        success:function (obj) {
            if(obj.state==1){
                videosList=obj.data;
                //2.循环得到的集合
                for (var i=0;i<videosList.length;i++){
                    //3.获得id
                    var id=videosList[i].id;
                    //4.创建连接
                    var video="<li><a onclick='f_videoPlay("+id+")'>"+videosList[i].fileName+"</a></li>";
                    $("#ul_video").append(video);
                }
            }else{
                alert("获取视频列表失败");
            }
        }
    });
}

//播放视频所需要的方法
function f_videoPlay(id){
    var u=window.location.href+"/getVideo?id="+id;
    document.getElementById("video_player")                                                                                                                                             .src=u;
}