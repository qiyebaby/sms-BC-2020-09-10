$(function () {
    layui.use(['form', 'table'], function (){
        var $ = layui.jquery,
            form = layui.form,
            layer = layui.layer,
        table = layui.table;
        //1、展示信息
        build_welcome_info();
        advice();
        var advice_all_info = "";
        //Ajax请求获取数据，展示首页用户统计，浏览统计..等
        function build_welcome_info() {
            $.ajax({
                url:"user/getIndexInfo",
                type:"get",
                contentType:"application/json;charset=UTF-8",
                success:function (result) {
                    //展示用户总数
                    $(".panel-content h1:first").append(result.extend.total)
                    $(".panel-content div:first").append(result.extend.total)
                    //展示学生总数
                    $(".panel-content h1:eq(2)").append(result.extend.total_student)
                    $(".panel-content div:eq(2)").append(result.extend.total_student)
                    //展示教师总数
                    $(".panel-content h1:last").append(result.extend.total_tearcher)
                    $(".panel-content div:last").append(result.extend.total_tearcher)
                    $.each(result.extend.userList,function (index,item) {
                        if (index > 3){
                            return false;
                        }
                        var usertype = "管理员";
                        if (item.usertype == 1){
                            usertype = "学生";
                        }else if (item.usertype == 2) {
                            usertype = "教师";
                        }
                        var trEle = $("<tr></tr>").
                        append($("<td></td>").append(item.username)).
                        append($("<td></td>").append(item.date)).
                        append($("<td></td>").append("<font color='red'>"+usertype+"</font>"));
                        $("#show_info").append(trEle)
                    })
                }
            });
        }
        function advice() {
            $(function () {
                $.ajax({
                    url:"advice/global_advice",
                    type:"GET",
                    success:function (result) {
                        console.log(result)
                        if (result.extend.level_five != null){
                            //有全局通知【五级】
                            var loopObj = result.extend.level_five;
                            var i = 0;
                            if (loopObj[i] != null ){
                                advice_gg(i,loopObj)
                            }
                        }
                        //提示有通知在首页信息通知的地方
                        if (result.extend.count == 0 || result.extend.count == null){
                            $("#advice_num").html(0);
                        }else {
                            $("#advice_num").html(result.extend.count);
                            $("#advice_info").html("您有新的通知，请注意查收");
                            advice_all_info = result;
                        }
                    }
                })
            })
        }
        function advice_gg(i,loopObj) {
            var time = loopObj[i].date;
            var person = "何磊";
            var content = loopObj[i].content
            var aid = loopObj[i].aid
            layer.open({
                type: 1
                ,title: false //不显示标题栏
                ,closeBtn: false
                ,area: '400px;'
                ,shade: 0.8
                ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                ,btn: ['确认查看']
                ,btnAlign: 'c'
                ,moveType: 1 //拖拽模式，0或者1
                ,content: '<div style=" padding: 30px;line-height: 22px;  background-color: floralwhite; color: black; font-weight: 400;">' +
                    '<center><font size="6px">最新通知</font></center>' +
                    '<br><font size="3px">' +
                    '通知时间:<span>'+time+'</span>' +
                    '<br><br>' +
                    '通知人:<span>'+person+'</span>' +
                    '<br><br>' +
                    '通知内容:<span>'+content+'</span>' +
                    '<br><br>' +
                    '</font></div>'
                ,success: function(layero){
                    var btn = layero.find('.layui-layer-btn');
                    btn.find('.layui-layer-btn0').click(function () {
                        //查看以后删除。
                        // 发送ajax删除操作
                        $.ajax({
                            url:"advice/deleteadvice",
                            data:{
                                aid:aid
                            },
                            type:"POST",
                            success:function (result) {
                                if (result.code == 1){
                                    layer.msg("查看信息失败",{
                                        anim:6,
                                        icon:2,
                                        time:2000
                                    })
                                    return false;
                                }
                            }
                        })
                        //查看下一条
                        if (loopObj[i+1] != null ){
                        layer.msg("查看通知成功，正在加载下一条！",{
                            icon:1,
                            time:3000,
                        },function () {
                                advice_gg(i+1,loopObj)
                        })
                        }
                    })
                }
            });
        }
        $("#advice_btn").click(function () {
            $("#show_head").empty()
            if (advice_all_info.count != 0 ){
                $("#show_head").append("<tr></tr>").
                append("<th>通知编号</th>").
                append("<th>通知人</th>").
                append("<th>通知时间</th>").
                append("<th>通知内容</tdh").
                append("<th>操作</th>");
            }

        //循环展示不为null的
            if (advice_all_info.extend.level_four != null){
                loop_show(advice_all_info.extend.level_four);
            }
            if (advice_all_info.extend.level_three != null){
                loop_show(advice_all_info.extend.level_three);
            }
            if (advice_all_info.extend.level_two != null){
                loop_show(advice_all_info.extend.level_two);
            }
            if (advice_all_info.extend.level_one != null){
                loop_show(advice_all_info.extend.level_one);
            }
        })

        function loop_show(date) {
            $("#show_advice").empty()
            $.each(date,function (index,item) {
                $("#show_advice").append("<tr></tr>").
                append("<td>"+item.aid+"</td>").
                append("<td>"+item.person+"</td>").
                append("<td>"+item.date+"</td>").
                append("<td>"+item.content+"</td>").
                append("<td>" +
                    "<button class='layui-btn layui-btn-normal' id='del"+item.aid+"' >已接收通知并删除</button>" +
                    "<button class='layui-btn layui-btn-normal' id='show"+item.aid+"' >查看详细内容</button>" +
                    "</td>");
                $("#del"+item.aid+"").click(function () {
                    alert(1231231)
                    layer.confirm("确定删除吗？",function () {
                        $.ajax({
                            url:"advice/deleteadvice",
                            data:{
                                aid:item.aid
                            },
                            type:"POST",
                            success:function (result) {
                                if (result.code == 1){
                                    layer.msg("删除失败",{
                                        anim:6,
                                        icon:2,
                                        time:2000
                                    })
                                    return false;
                                }
                                advice();
                                layer.msg("删除成功",{
                                    icon:1,
                                    time:1000
                                },function () {
                                    $("#advice_btn").click()
                                })

                            }
                        })
                    })
                })
                $("#show"+item.aid+"").click(function () {
                    layer.open({
                        title: '详细信息',
                        type: 1,
                        maxmin:true,
                        shadeClose: true,
                        area: ['30%', '40%'],
                        content:"<font size='4px'>通知详情："+ item.content+"</font>",
                    });
                    $(window).on("resize", function () {
                        layer.full(index);
                    });
                })
            })
        }
    });
})