
//全局JS工具类，只定义方法，不负责调用
//需要使用$函数，【使用该文件之前，请先声明Jquery文件】

var Util = function () {

    //   查询对应院系的班级

    this.ajax_dept_class = function (deptno,handleFun) {
        $.ajax({
            url:"../../../class/queryClass",
            type:"GET",
            data:{
                deptno:deptno
            },
            success:handleFun
        });
    };

// 查询所有院系，封装院系信息 handleFun是成功的处理回调函数
    this.ajax_dept = function (handleFun) {
            $.ajax({
                   url:"../../../dept/query",
                   type:"GET",
                   success:handleFun
               });
            };

// ajax校验学院名是否存在
    this.cname_exists_ajax = function (value) {
        $.ajax({
            url:"../../../class/byname",
            data:{
                cname:value
            },
            type:"GET",
            success:function (result) {
                if (result.code == 1){//失败
                    layer.msg(result.info, {
                        anim:6,
                        icon: 2,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        //do something
                    });
                    return;
                }
                layer.msg(result.info, {
                    icon: 1,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
                return;
            }

        })

    }


// ===========================================================================分割符

//   查询班级的班主任

    this.ajax_class_headmaster = function (cno,handleFun) {
        $.ajax({
            url:"../../../tearcher/queryheadmaster",
            type:"GET",
            data:{
                cno:cno
            },
            success:handleFun
        });
    };


// 查询所有不是班主任的教师，封装教师信息
    this.ajax_tearcher = function (handleFun) {
            $.ajax({
                url:"../../../tearcher/query",
                type:"GET",
                success:handleFun
            });
    }

// Ajax判断教师信息是否已经重复
    this.tearcherInfo_exists_ajax = function (realname) {
        $.ajax({
            url:"../../../tearcher/exists",
            data:{
                realname:realname,
            },
            type:"GET",
            success:function (result) {
                if (result.code == 1){//失败
                    layer.msg(result.info, {
                        anim:6,
                        icon: 2,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        //do something
                    });
                    return;
                }
                layer.msg(result.info, {
                    icon: 1,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
                return;
            }
        })
    }



// ===========================================================================分割符

// Ajax判断用户信息是否已经重复
    this.userInfo_exists_ajax = function (username,usertype,handleFun) {
        $.ajax({
            url:"../../../user/exists",
            data:{
                username:username,
                usertype:usertype
            },
            type:"GET",
            success:handleFun
        })
    }



// ===========================================================================分割符
//班级相关

// Ajax查询所有的班级，展示给学生注册页面的院系选择框
  this.ajax_class = function (handleFun) {
      $.ajax({
          url:"../../../class/query",
          type:"GET",
          success:handleFun
      });
  }
// ===========================================================================分割符
//学生相关+寝室相关

// 根据班级查询学生信息
    this.ajax_class_student = function (cno,handleFun) {
        $.ajax({
            url:"../../../student/querycno",
            type:"GET",
            data:{
                cno:cno
            },
            success:handleFun
        });
    };
// 验证学号是否已经存在，如果这个学号对应的那个登录账号也是已经存在的，那么也是不可用学号
    this.studentInfo_exists_ajax = function (sno,handleFun) {
        $.ajax({
            url:"../../../student/exists",
            data:{
                sno:sno,
            },
            type:"GET",
            success:handleFun
        })
    }


// 验证寝室该寝室是否已经住满【缺点：未分学院，未分男女】
    this.student_address_full_ajax = function (num,floor,room,handleFun) {
        $.ajax({
            url:"../../../room/isAvailable",//是可用的意思
            data:{
                num:num,
                floor:floor,
                room:room
            },
            type:"GET",
            success:handleFun
        })
    }

// ajax查询数据展示在表格
    this.room_data = function (url,handleFun) {
        $.ajax({
            url:url,//
            type:"GET",
            success:handleFun
        })
    }



// ===========================================================================分割符


// ============定义循环函数
    this.start = function(fun){
        return window.setInterval(fun,1000)
    }
    this.stop = function(c){
        window.clearInterval(c);
    }

}

var U = new Util();









