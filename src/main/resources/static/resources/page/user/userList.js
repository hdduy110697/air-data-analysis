layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //user list
    var tableIns = table.render({
        elem: '#userList',
        url : '../../json/userList.json',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'userName', title: 'Username', minWidth:100, align:"center"},
            {field: 'userEmail', title: 'Email', minWidth:200, align:'center',templet:function(d){
                return '<a class="layui-blue" href="mailto:'+d.userEmail+'">'+d.userEmail+'</a>';
            }},
            {field: 'userSex', title: 'Gender', align:'center'},
            {field: 'userStatus', title: 'Status',  align:'center',templet:function(d){
                return d.userStatus == "0" ? "Active" : "Limited";
            }},
            {field: 'userGrade', title: 'Grade', align:'center',templet:function(d){
                if(d.userGrade == "0"){
                    return "Registered Member";
                }else if(d.userGrade == "1"){
                    return "Intermediate Member";
                }else if(d.userGrade == "2"){
                    return "Senior Member";
                }else if(d.userGrade == "3"){
                    return "Diamond membership";
                }else if(d.userGrade == "4"){
                    return "Super member";
                }
            }},
            {field: 'userEndTime', title: 'Last Login Time', align:'center',minWidth:150},
            {title: 'Action', minWidth:175, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });

    //Search [This function requires background cooperation, so there is no dynamic effect demonstration for the time being]
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("newsListTable",{
                page: {
                    curr: 1 //Start over from page 1
                },
                where: {
                    key: $(".searchVal").val()  //Searched keywords
                }
            })
        }else{
            layer.msg("Please input 搜索的内容");
        }
    });

    //Add user
    function addUser(edit){
        var index = layui.layer.open({
            title : "Add User",
            type : 2,
            content : "userAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".userName").val(edit.userName); 
                    body.find(".userEmail").val(edit.userEmail);
                    body.find(".userSex input[value="+edit.userSex+"]").prop("checked","checked");  
                    body.find(".userGrade").val(edit.userGrade); 
                    body.find(".userStatus").val(edit.userStatus); 
                    body.find(".userDesc").text(edit.userDesc);  
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //When changing the window size, reset the width and height of the pop-up window to prevent it from exceeding the visible area (such as the F12 debug operation)
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }
    $(".addNews_btn").click(function(){
        addUser();
    })

    //batch deletion
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('userListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                // $.get("Delete Article Interface",{
                //     newsId : newsId  //Pass the newsId to be deleted as a parameter
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            })
        }else{
            layer.msg("Please select the user you want to delete");
        }
    })

    //List operations
    table.on('tool(userList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){
            addUser(data);
        }else if(layEvent === 'usable'){
            var _this = $(this),
                usableText = "Are you sure you want to disable this user?",
                btnText = "Disabled";
            if(_this.text()=="Disabled"){
                usableText = "Are you sure you want to enable this user?",
                btnText = "Activated";
            }
            layer.confirm(usableText,{
                icon: 3,
                title:'System Confirm',
                cancel : function(index){
                    layer.close(index);
                }
            },function(index){
                _this.text(btnText);
                layer.close(index);
            },function(index){
                layer.close(index);
            });
        }else if(layEvent === 'del'){ //删除
            layer.confirm('Are you sure you want to delete this user?',{icon:3, title:'Information'},function(index){
                // $.get("删除文章接口",{
                //     newsId : data.newsId  //Pass the newsId to be deleted as a parameter
                // },function(data){
                    tableIns.reload();
                    layer.close(index);
                // })
            });
        }
    });

})
