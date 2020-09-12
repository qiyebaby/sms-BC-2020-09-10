<%@ taglib prefix="if" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="utf-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>基本资料</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../lib/layui-v2.5.5/css/layui.css" media="all">
    <link rel="stylesheet" href="../../css/public.css" media="all">
    <style>
        .layui-form-item .layui-input-company {width: auto;padding-right: 10px;line-height: 38px;}
    </style>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <div class="layui-form layuimini-form">
            <div class="layui-form-item">
                <label class="layui-form-label required">用户编号</label>
                <div class="layui-input-block">
                    <input type="text" name="uid" readonly   value="" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label required">用户类型</label>
                <div class="layui-input-block">
                    <input type="text" name="usertype" readonly  value="" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label required">用户权限</label>
                <div class="layui-input-block">
                    <input type="text" style="color: green" name="authority" readonly  value="" class="layui-input">
                </div>
            </div>

            <if:if test="${userInfo.usertype == 1}">
                <div class="layui-form-item">
                    <label class="layui-form-label required">学生姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="sname"  readonly  value="" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">寝室地址</label>
                    <div class="layui-input-block">
                        <input type="text" name="dorm"  readonly  value="" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">联系方式</label>
                    <div class="layui-input-block">
                        <input type="text" name="phone" readonly lay-verify="required" lay-reqtext="手机不能为空" placeholder="请输入手机"  value="" class="layui-input">
                    </div>
                </div>
            </if:if>
            <if:if test="${userInfo.usertype == 2}">
                <div class="layui-form-item">
                    <label class="layui-form-label required">教师姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="realname" readonly  value="" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">入职日期</label>
                    <div class="layui-input-block">
                        <input type="text" name="date" readonly lay-verify="required" lay-reqtext="手机不能为空" placeholder="请输入手机"  value="" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">联系方式</label>
                    <div class="layui-input-block">
                        <input type="text" readonly name="phone" lay-verify="required" lay-reqtext="手机不能为空" placeholder="请输入手机"  value="" class="layui-input">
                    </div>
                </div>
            </if:if>
            <div class="layui-form-item">
                <label class="layui-form-label required">登录账号</label>
                <div class="layui-input-block">
                    <input type="text" name="username" readonly  value="" class="layui-input">
                </div>
            </div>
        </div>
    </div>
</div>
<script src="../../lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<script src="../../js/lay-config.js?v=1.0.4" charset="utf-8"></script>
<script>
    layui.use(['form','miniTab'], function () {
        var form = layui.form,
            $ = layui.$,
            layer = layui.layer,
            miniTab = layui.miniTab;

        $.ajax({
            url:"../../../system/query",
            type:"get",
            success:function (result) {
                console.log(result)
                $(":text[name='uid']").val(result.extend.userInfo.uid);
                if (result.extend.userInfo.usertype == 0){
                    $(":text[name='usertype']").val("管理员");
                }else if (result.extend.userInfo.usertype == 1){
                    $(":text[name='usertype']").val("学生");
                    $(":text[name='dorm']").val(result.extend.student.dorm);
                    $(":text[name='sname']").val(result.extend.student.sname);
                    $(":text[name='phone']").val(result.extend.student.phone);
                }else {
                    $(":text[name='usertype']").val("教师");
                    $(":text[name='phone']").val(result.extend.tearcher.phone);
                    $(":text[name='realname']").val(result.extend.tearcher.realname);
                    $(":text[name='date']").val(result.extend.userInfo.date);
                }


                $(":text[name='username']").val(result.extend.userInfo.username);



                if (result.extend.authority.level == 1){
                    $(":text[name='authority']").val("您仅有查看信息的权限");
                }else if (result.extend.authority.level == 2){
                    $(":text[name='authority']").val("您有查看和添加信息的权限");
                }else if (result.extend.authority.level == 3){
                    $(":text[name='authority']").val("您有部分增删改的权限");
                }else {
                    $(":text[name='authority']").val("您除了不能删除自己，其他什么都行");
                }
            }
        })

        //监听提交
        form.on('submit(saveBtn)', function (data) {
            var index = layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            }, function () {
                layer.close(index);
                miniTab.deleteCurrentByIframe();
            });
            return false;
        });

    });
</script>
</body>
</html>