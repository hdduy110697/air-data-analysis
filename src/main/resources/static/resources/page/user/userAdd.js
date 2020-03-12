layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(addUser)",function(data){
        //loading
        var index = top.layer.msg('Data submission, please wait',{icon: 16,time:false,shade:0.8});
       
        setTimeout(function(){
            top.layer.close(index);
            top.layer.msg("User added successfully!");
            layer.closeAll("iframe");
            
            parent.location.reload();
        },2000);
        return false;
    })
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }
    var time = new Date();
    var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());

})