<%@page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <%
        String path = request.getScheme() + "://" +
                request.getServerName() + ":" +
                request.getServerPort() +
                request.getContextPath() + "/";
    %>
    <base href="<%=path%>">
    <meta charset="utf-8">
    <title>主页一</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <script src="static/lib/layui-v2.5.5/layui.js" charset="utf-8">
    </script>
    <script src="static/js/JQuery-v3.5.1.js"></script>
    <script src="static/js/welcome.js" charset="utf-8"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="static/lib/layui-v2.5.5/css/layui.css" media="all">
    <link rel="stylesheet" href="static/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="static/css/public.css" media="all">
</head>
<style>
    .layui-top-box {
        padding: 40px 20px 20px 20px;
        color: #fff
    }

    .panel {
        margin-bottom: 17px;
        background-color: #fff;
        border: 1px solid transparent;
        border-radius: 3px;
        -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
        box-shadow: 0 1px 1px rgba(0, 0, 0, .05)
    }

    .panel-body {
        padding: 15px
    }

    .panel-title {
        margin-top: 0;
        margin-bottom: 0;
        font-size: 14px;
        color: inherit
    }

    .label {
        display: inline;
        padding: .2em .6em .3em;
        font-size: 75%;
        font-weight: 700;
        line-height: 1;
        color: #fff;
        text-align: center;
        white-space: nowrap;
        vertical-align: baseline;
        border-radius: .25em;
        margin-top: .3em;
    }

    .main_btn > p {
        height: 40px;
    }

</style>
<body>
<div class="layuimini-container">
    <div class="layuimini-main layui-top-box">
        <div class="layui-row layui-col-space10">

            <div class="layui-col-md3">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-cyan">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-blue">实时</span>
                                <h5>用户统计</h5>
                            </div>
                            <div class="panel-content" >
                                <h1 class="no-margins"></h1>
                                <div class="stat-percent font-bold text-gray">
                                    <i class="fa fa-commenting"></i>

                                </div>
                                <small>当前分类总记录数</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-col-md3">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-green">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-orange">实时</span>
                                <h5>浏览统计</h5>
                            </div>
                            <div class="panel-content">
                                <h1 class="no-margins">${infoStatistics+1 }</h1>
                                <div class="stat-percent font-bold text-gray"><i class="fa fa-commenting"></i>
                                    ${infoStatistics+1 }
                                </div>
                                <small>当前分类总记录数</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md3">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-blue">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-cyan">实时</span>
                                <h5>学生统计</h5>
                            </div>
                            <div class="panel-content">
                                <h1 class="no-margins"></h1>
                                <div class="stat-percent font-bold text-gray"><i class="fa fa-commenting"></i>
                                </div>
                                <small>当前分类总记录数</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md3">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-orange">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-green">实时</span>
                                <h5>教师统计</h5>
                            </div>
                            <div class="panel-content">
                                <h1 class="no-margins"></h1>
                                <div class="stat-percent font-bold text-gray"><i class="fa fa-commenting"></i>
                                </div>
                                <small>当前分类总记录数</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-box">
        <div class="layui-row layui-col-space10">
            <div class="layui-col-md12">
                <blockquote class="layui-elem-quote main_btn">
                    <button id="advice_btn" class="layui-btn layui-bg-black">
                        <i class="layui-icon" style="color: white">
                            &#xe667;
                        </i>
                        <span class="layui-badge" id="advice_num">
                        </span>
                    </button>
                    &nbsp;&nbsp;&nbsp;
                    <span style="color: green;" id="advice_info">
                    </span>
                    <br>
                    <table class="layui-table">
                        <colgroup>
                            <col width="150">
                            <col width="200">
                            <col>
                        </colgroup>
                        <thead id="show_head">

                        </thead>
                        <tbody id="show_advice">

                        </tbody>
                    </table>
                </blockquote>

            </div>
        </div>
    </div>
    <div class="layui-box">
        <div class="layui-row layui-col-space10">
            <div class="layui-col-md6">
                <table class="layui-table">
                    <colgroup>
                        <col width="150">
                        <col width="200">
                        <col>
                    </colgroup>
                    <thead>
                        <tr>
                            <th>用户名</th>
                            <th>加入时间</th>
                            <th>用户类型</th>
                        </tr>
                    </thead>
                    <tbody id="show_info">
                    </tbody>
                </table>
            </div>
            <div class="layui-col-md6">
                <ul class="layui-timeline">
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">9月7日</h3>
                            </p>
                            <ul>
                                <li>安全退出</li>
                                <li>模糊查询</li>
                                <li>通知模块</li>
                            </ul>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">期间</h3>
                            </p>
                            <ul>
                                <li>有</li>
                                <li>事</li>
                                <li>情</li>
                            </ul>

                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">9月3日</h3>
                            </p>
                            <ul>
                                <li>完成寝室管理模块</li>
                                <li>开始批量删除分析</li>
                                <li>准备通知模块设计+学生教师登录分析</li>
                            </ul>

                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">9月2日</h3>
                            </p>
                            <ul>
                                <li>完成各类新增之间逻辑设计</li>
                                <li>新增学生寝室管理模块</li>
                            </ul>

                        </div>
                    </li>

                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">8月30日</h3>
                            </p>
                            <ul>
                                <li>完成所有信息查看模块</li>
                                <li>关于用户禁用权限设置</li>
                                <li>关于总得浏览统计的设计</li>
                                <li>用户登录的过滤器设计</li>
                                <li>html下静态资源直接访问的拦截器登录判断</li>
                            </ul>

                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">8月29日</h3>
                            </p>
                            <ul>
                                <li>完成院系模块</li>
                                <li>完成班级模块</li>
                                <li>完善ajax请求细节+正则表达式取值</li>
                            </ul>

                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">8月28日</h3>
                            </p>
                            <ul>
                                <li>完成基本配置</li>
                                <li>解决所有乱码</li>
                                <li>展示首页</li>
                            </ul>

                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">8月25日</h3>
                            <p>
                                正式准备页面
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <div class="layui-timeline-title">开始构建</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>