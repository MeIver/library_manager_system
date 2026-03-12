layui.use(['form', 'element','layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
});

$(document).ready(function () {
    // 确认还书按钮点击事件
    $("#btn1").click(function () {
        let bookId = $("#bookId").val().toString().trim();

        if (bookId === null || bookId === '' || isNaN(bookId)) {
            layer.alert("请正确输入书籍编号");
            return false;
        }

        returnBook(bookId);
    });

    // 借阅列表中的归还按钮点击事件
    $(".return-book-btn").click(function () {
        let bookId = $(this).data("bookid");
        let bookName = $(this).data("bookname");

        layer.confirm('确定要归还《' + bookName + '》吗？', {
            btn: ['确定', '取消']
        }, function(index){
            returnBook(bookId);
            layer.close(index);
        });
    });
});

function returnBook(bookId) {
    $.ajax({
        async: false,
        type: "post",
        url: "/userReturnBook",
        dataType: "json",
        data: {bookId: bookId},
        success: function (data) {

            if (data.toString() == "true") {
                layer.msg('还书成功!!', {icon: 6, time: 2000}, function(){
                    // 刷新页面以更新借阅列表
                    window.location.reload();
                });
                $("#bookId").val('');
            } else {
                layer.msg('还书失败!', {icon: 7, time: 2000});
                $("#bookId").val('');
            }

        },
        error: function (data) {
            alert(data.result);
        }
    });
};