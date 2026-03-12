layui.use(['form', 'layer'], function () {
    let form = layui.form;
    let layer = layui.layer;

    // 编辑按钮点击事件
    $('#editInfoBtn, #editProfileBtn').on('click', function() {
        openEditModal();
    });

    // 打开编辑模态框
    function openEditModal() {
        let userName = $('#editUserName').val();
        let userEmail = $('#editUserEmail').val();

        layer.open({
            type: 1,
            title: ['编辑个人资料', 'font-size:18px; font-weight:600;'],
            area: ['450px', 'auto'],
            skin: 'layui-layer-molv',
            shadeClose: true,
            content: $('#editModal'),
            btn: ['保存', '取消'],
            yes: function(index, layero) {
                saveUserInfo(index);
            },
            btn2: function(index, layero) {
                // 取消时恢复原始值
                $('#editUserName').val(userName);
                $('#editUserEmail').val(userEmail);
                layer.close(index);
            },
            cancel: function() {
                // 关闭时恢复原始值
                $('#editUserName').val(userName);
                $('#editUserEmail').val(userEmail);
            }
        });
    }

    // 保存用户信息
    function saveUserInfo(layerIndex) {
        let userName = $('#editUserName').val().trim();
        let userEmail = $('#editUserEmail').val().trim();

        // 表单验证
        if (!userName) {
            layer.msg('请输入用户姓名', {icon: 2});
            $('#editUserName').focus();
            return;
        }

        if (userName.length < 2 || userName.length > 20) {
            layer.msg('用户姓名长度应在2-20个字符之间', {icon: 2});
            $('#editUserName').focus();
            return;
        }

        if (!userEmail) {
            layer.msg('请输入电子邮箱', {icon: 2});
            $('#editUserEmail').focus();
            return;
        }

        // 邮箱格式验证
        let emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(userEmail)) {
            layer.msg('请输入正确的邮箱格式', {icon: 2});
            $('#editUserEmail').focus();
            return;
        }

        // 显示加载中
        let loadIndex = layer.load(2);

        // 发送请求
        $.ajax({
            type: 'POST',
            url: '/updateUser',
            data: {
                userName: userName,
                userEmail: userEmail
            },
            success: function(res) {
                layer.close(loadIndex);
                if (res === true || res === 'true') {
                    layer.alert('资料修改成功！', {
                        icon: 1,
                        title: '修改成功'
                    }, function() {
                        layer.close(layerIndex);
                        location.reload();
                    });
                } else {
                    layer.msg('修改失败，请稍后重试', {icon: 2});
                }
            },
            error: function() {
                layer.close(loadIndex);
                layer.msg('网络错误，请稍后重试', {icon: 2});
            }
        });
    }
});
