package com.zbw.controller;

import com.zbw.domain.Admin;
import com.zbw.domain.BookCategory;
import com.zbw.domain.User;
import com.zbw.domain.Vo.BookVo;
import com.zbw.service.IAdminService;
import com.zbw.service.IBookCategoryService;
import com.zbw.service.IUserService;
import com.zbw.utils.PasswordUtil;
import com.zbw.utils.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Resource
    private IAdminService adminService;
    @Resource
    private IBookCategoryService bookCategoryService;
    @Resource
    private IUserService userService;

    /**
     * 判断admin是否存在
     *
     * @param adminName
     * @return
     */
    @RequestMapping("/isAdminExist")
    @ResponseBody
    public String adminIsExist(@Param("adminName") String adminName) {
        boolean b = adminService.adminIsExist(adminName);
        if (b) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 管理员登陆
     *
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/adminLogin")
    public String adminLogin(@Param("userName") String userName, @Param("password") String password, HttpServletRequest request) {
        Admin admin = adminService.adminLogin(userName, password);

        if (admin == null) {
            // flag 为 1 表示 登录失败 
            request.getSession().setAttribute("flag", 1);
            return "index";
        }

        // flag = 0 表示用户名密码校验成功
        request.getSession().setAttribute("flag", 0);
        request.getSession().setAttribute("admin", admin);
        return "admin/index";
    }

    /**
     * 返回添加书籍页面
     */
    @RequestMapping("/addBookPage")
    public String addBookPage() {
        return "admin/addBook";
    }

    /**
     * 返回添加类别页面
     */
    @RequestMapping("/addCategoryPage")
    public String addCategoryPage(@RequestParam("pageNum") int pageNum, Model model) {
        Page<BookCategory> page = bookCategoryService.selectBookCategoryByPageNum(pageNum);
        model.addAttribute("page", page);
        return "admin/addCategory";
    }

    /**
     * 返回查询状态页面
     */
    @RequestMapping("/showStausPage")
    public String showStatusPage() {
        return "admin/showStaus";
    }

    /**
     * 返回管理员首页
     */
    @RequestMapping("/adminIndex")
    public String returnAdminIndexPage() {
        return "admin/index";
    }


    /**
     * 返回查询用户页面
     */
    @RequestMapping("/showUsersPage")
    public String showUsersPage(Model model, @RequestParam("pageNum") int pageNum) {
        Page<User> page = userService.findUserByPage(pageNum);
        model.addAttribute("page", page);
        return "admin/showUsers";
    }

    /**
     * 返回&emsp;&emsp;查询书籍页面
     */
    @RequestMapping("/showBooksPage")
    public String showBooksPage(Model model) {
        Page<BookVo> page = new Page<BookVo>();
        page.setPageCount(1);
        page.setPageNum(1);
        model.addAttribute("page", page);
        return "admin/showBooks";
    }


    /**
     * 管理员退出登陆
     *
     * @param request
     * @return
     */
    @RequestMapping("/adminLogOut")
    public String userLogOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    /**
     * 返回新增用户页面
     */
    @RequestMapping("/addUserPage")
    public String addUserPage() {
        return "admin/addUser";
    }

    @RequestMapping("/adminInfoPage")
    public String adminInfo() {
        return "admin/adminInfo";
    }
    
    /**
     * 更新管理员信息
     *
     * @param admin
     * @param request
     * @return
     */
    @RequestMapping("/updateAdmin")
    @ResponseBody
    public boolean updateAdmin(Admin admin, HttpServletRequest request) {
        return adminService.updateAdmin(admin, request);
    }

    /**
     * 返回管理员安全设置页面
     */
    @RequestMapping("/adminSecurityPage")
    public String adminSecurityPage() {
        return "admin/adminSecurity";
    }

    /**
     * 更新管理员信息
     */
    @RequestMapping("/updateAdminMessage")
    @ResponseBody
    public String updateAdminMessage(Admin admin, HttpServletRequest request) {
        boolean result = adminService.updateAdmin(admin, request);
        return result ? "success" : "fail";
    }

    /**
     * 更新管理员密码
     */
    @RequestMapping("/updateAdminPassword")
    @ResponseBody
    public String updateAdminPassword(@RequestParam("adminId") int adminId,
                                      @RequestParam("oldPassword") String oldPassword,
                                      @RequestParam("newPassword") String newPassword,
                                      HttpServletRequest request) {
        Admin admin = adminService.findAdminById(adminId);
        if (admin == null) {
            return "fail";
        }
        
        String encryptedOldPassword = PasswordUtil.encrypt(oldPassword);
        if (!encryptedOldPassword.equals(admin.getAdminPwd())) {
            return "wrong";
        }
        
        String encryptedNewPassword = PasswordUtil.encrypt(newPassword);
        admin.setAdminPwd(encryptedNewPassword);
        
        boolean result = adminService.updateAdmin(admin, request);
        return result ? "success" : "fail";
    }

}
