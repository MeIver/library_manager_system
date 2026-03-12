layui.use(['form', 'layer'], function () {
    let form = layui.form;
    let layer = layui.layer;

    // 编辑按钮点击事件
    $('#editInfoBtn, #editProfileBtn').on('click', function() {
        openEditModal();
    });

    // 打开编辑模态框
    function openEditModal() {
        let adminName = $('#editAdminName').val();
        let adminEmail = $('#editAdminEmail').val();

        layer.open({
            type: 1,
            title: ['编辑管理员资料', 'font-size:18px; font-weight:600;'],
            area: ['450px', 'auto'],
            skin: 'layui-layer-molv',
            shadeClose: true,
            content: $('#editModal'),
            btn: ['保存', '取消'],
            yes: function(index, layero) {
                saveAdminInfo(index);
            },
            btn2: function(index, layero) {
                // 取消时恢复原始值
                $('#editAdminName').val(adminName);
                $('#editAdminEmail').val(adminEmail);
                layer.close(index);
            },
            cancel: function() {
                // 关闭时恢复原始值
                $('#editAdminName').val(adminName);
                $('#editAdminEmail').val(adminEmail);
            }
        });
    }

    // 保存管理员信息
    function saveAdminInfo(layerIndex) {
        let adminName = $('#editAdminName').val().trim();
        let adminEmail = $('#editAdminEmail').val().trim();

        // 表单验证
        if (!adminName) {
            layer.msg('请输入管理员姓名', {icon: 2});
            $('#editAdminName').focus();
            return;
        }

        if (adminName.length < 2 || adminName.length > 20) {
            layer.msg('管理员姓名长度应在2-20个字符之间', {icon: 2});
            $('#editAdminName').focus();
            return;
        }

        if (!adminEmail) {
            layer.msg('请输入电子邮箱', {icon: 2});
            $('#editAdminEmail').focus();
            return;
        }

        // 邮箱格式验证
        let emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(adminEmail)) {
            layer.msg('请输入正确的邮箱格式', {icon: 2});
            $('#editAdminEmail').focus();
            return;
        }

        // 显示加载中
        let loadIndex = layer.load(2);

        // 发送请求
        $.ajax({
            type: 'POST',
            url: '/updateAdmin',
            data: {
                adminName: adminName,
                adminEmail: adminEmail
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
