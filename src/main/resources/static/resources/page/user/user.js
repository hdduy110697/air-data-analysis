layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    form.verify({
        oldPwd : function(value, item){
            if(value != "123456"){
                return "Password is wrong, please re-enter!";
            }
        },
        newPwd : function(value, item){
            if(value.length < 6){
                return "Password must be at least 6 characters";
            }
        },
        confirmPwd : function(value, item){
            if(!new RegExp($("#oldPwd").val()).test(value)){
                return "Password entered twice is not the same, please re-enter!";
            }
        }
    })

    table.render({
        elem: '#userGrade',
        url : '../../json/userGrade.json',
        cellMinWidth : 95,
        cols : [[
            {field:"id", title: 'ID', width: 60, fixed:"left",sort:"true", align:'center', edit: 'text'},
            {field: 'gradeIcon', title: 'Icon', templet:'#gradeIcon', align:'center'},
            {field: 'gradeName', title: 'Level', edit: 'text', align:'center'},
            {field: 'gradeValue', title: 'Rank', edit: 'text',sort:"true", align:'center'},
            {field: 'gradeGold', title: 'Gold', edit: 'text',sort:"true", align:'center'},
            {field: 'gradePoint', title: 'Point', edit: 'text',sort:"true", align:'center'},
            {title: 'Status',minWidth:100, templet:'#gradeBar',fixed:"right",align:"center"}
        ]]
    });

    form.on('switch(gradeStatus)', function(data){
        var tipText = 'Are you sure you want to disable the current membership level?';
        if(data.elem.checked){
            tipText = 'Are you sure you want to enable the current membership level?'
        }
        layer.confirm(tipText,{
            icon: 3,
            title:'System Confirm',
            cancel : function(index){
                data.elem.checked = !data.elem.checked;
                form.render();
                layer.close(index);
            }
        },function(index){
            layer.close(index);
        },function(index){
            data.elem.checked = !data.elem.checked;
            form.render();
            layer.close(index);
        });
    });
    //Add level
    $(".addGrade").click(function(){
        var $tr = $(".layui-table-body.layui-table-main tbody tr:last");
        if($tr.data("index") < 9) {
            var newHtml = '<tr data-index="' + ($tr.data("index") + 1) + '">' +
                '<td data-field="id" data-edit="text" align="center"><div class="layui-table-cell laytable-cell-1-id">' + ($tr.data("index") + 2) + '</div></td>' +
                '<td data-field="gradeIcon" align="center" data-content="icon-vip' + ($tr.data("index") + 2) + '"><div class="layui-table-cell laytable-cell-1-gradeIcon"><span class="seraph vip' + ($tr.data("index") + 2) + ' icon-vip' + ($tr.data("index") + 2) + '"></span></div></td>' +
                '<td data-field="gradeName" data-edit="text" align="center"><div class="layui-table-cell laytable-cell-1-gradeName">Please input grade name</div></td>' +
                '<td data-field="gradeValue" data-edit="text" align="center"><div class="layui-table-cell laytable-cell-1-gradeValue">0</div></td>' +
                '<td data-field="gradeGold" data-edit="text" align="center"><div class="layui-table-cell laytable-cell-1-gradeGold">0</div></td>' +
                '<td data-field="gradePoint" data-edit="text" align="center"><div class="layui-table-cell laytable-cell-1-gradePoint">0</div></td>' +
                '<td data-field="' + ($tr.data("index") + 1) + '" align="center" data-content="" data-minwidth="100"><div class="layui-table-cell laytable-cell-1-' + ($tr.data("index") + 1) + '"> <input type="checkbox" name="gradeStatus" lay-filter="gradeStatus" lay-skin="switch" lay-text="Enable|Disable" checked=""><div class="layui-unselect layui-form-switch layui-form-onswitch" lay-skin="_switch"><em>Enable</em><i></i></div></div></td>' +
                '</tr>';
            $(".layui-table-body.layui-table-main tbody").append(newHtml);
            $(".layui-table-fixed.layui-table-fixed-l tbody").append('<tr data-index="' + ($tr.data("index") + 1) + '"><td data-field="id" data-edit="text" align="center"><div class="layui-table-cell laytable-cell-1-id">' + ($tr.data("index") + 2) +'</div></td></tr>');
            $(".layui-table-fixed.layui-table-fixed-r tbody").append('<tr data-index="' + ($tr.data("index") + 1) + '"><td data-field="' + ($tr.data("index") + 1) + '" align="center" data-content="" data-minwidth="100"><div class="layui-table-cell laytable-cell-1-' + ($tr.data("index") + 1) + '"> <input type="checkbox" name="gradeStatus" lay-filter="gradeStatus" lay-skin="switch" lay-text="Enable|Disable" checked=""><div class="layui-unselect layui-form-switch layui-form-onswitch" lay-skin="_switch"><em>Enable</em><i></i></div></div></td></tr>');
            form.render();
        }else{
            layer.alert("In actual development, it can be modified according to the actual situation. Don't forget to increase the color of the corresponding level.",{maxWidth:300});
        }
    });

    $("body").on("click",".layui-table-body.layui-table-main tbody tr td",function(){
        $(this).find(".layui-table-edit").addClass("layui-"+$(this).attr("align"));
    });

})