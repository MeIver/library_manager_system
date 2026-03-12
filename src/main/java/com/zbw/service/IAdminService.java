package com.zbw.service;

import com.zbw.domain.Admin;
import com.zbw.domain.Book;
import com.zbw.domain.BookCategory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAdminService {

    boolean adminIsExist(String name);
    
    Admin adminLogin(String name, String password);

    boolean addBook(Book book);

    List<BookCategory> getBookCategories();

    boolean addBookCategory(BookCategory bookCategory);

    boolean updateAdmin(Admin admin, HttpServletRequest request);

    Admin findAdminById(int adminId);
}
