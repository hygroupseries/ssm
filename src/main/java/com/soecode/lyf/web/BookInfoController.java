package com.soecode.lyf.web;

import com.soecode.lyf.dto.PageResult;
import com.soecode.lyf.entity.BookInfo;
import com.soecode.lyf.service.BookInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 图书管理 Controller（分页/增/删/改/查/模糊查询）
 * 访问路径：/bookinfo/*
 */
@Controller
@RequestMapping("/bookinfo")
public class BookInfoController {

    private static final int DEFAULT_PAGE_SIZE = 5;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookInfoService bookInfoService;

    /**
     * 图书列表页（支持分页 + 书名模糊查询）
     * GET /bookinfo/list?name=&pageNo=1
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                       @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                       Model model) {
        PageResult<BookInfo> page = bookInfoService.page(name, pageNo, DEFAULT_PAGE_SIZE);
        model.addAttribute("page", page);
        model.addAttribute("name", name);
        return "book/list";
    }

    /**
     * 跳转新增表单页
     * GET /bookinfo/add
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addForm(Model model) {
        model.addAttribute("book", new BookInfo());
        model.addAttribute("action", "add");
        return "book/form";
    }

    /**
     * 提交新增
     * POST /bookinfo/add
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addSubmit(@RequestParam("name") String name,
                            @RequestParam("author") String author,
                            @RequestParam("price") String price,
                            @RequestParam(value = "publishDate", required = false) String publishDate) {
        BookInfo book = buildBook(null, name, author, price, publishDate);
        bookInfoService.add(book);
        return "redirect:/bookinfo/list";
    }

    /**
     * 跳转编辑表单页
     * GET /bookinfo/edit/{id}
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") Long id, Model model) {
        BookInfo book = bookInfoService.getById(id);
        if (book == null) {
            return "redirect:/bookinfo/list";
        }
        model.addAttribute("book", book);
        model.addAttribute("action", "edit");
        return "book/form";
    }

    /**
     * 提交修改
     * POST /bookinfo/edit
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editSubmit(@RequestParam("id") Long id,
                             @RequestParam("name") String name,
                             @RequestParam("author") String author,
                             @RequestParam("price") String price,
                             @RequestParam(value = "publishDate", required = false) String publishDate) {
        BookInfo book = buildBook(id, name, author, price, publishDate);
        bookInfoService.update(book);
        return "redirect:/bookinfo/list";
    }

    /**
     * 删除图书（POST，防止 CSRF / 意外 GET 触发）
     * POST /bookinfo/delete
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("id") Long id) {
        bookInfoService.delete(id);
        return "redirect:/bookinfo/list";
    }

    private BookInfo buildBook(Long id, String name, String author, String price, String publishDate) {
        BookInfo book = new BookInfo();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        if (price != null && !price.trim().isEmpty()) {
            try {
                book.setPrice(new BigDecimal(price.trim()));
            } catch (NumberFormatException e) {
                logger.warn("价格格式无效，已忽略: {}", price);
            }
        }
        if (publishDate != null && !publishDate.trim().isEmpty()) {
            try {
                book.setPublishDate(new SimpleDateFormat("yyyy-MM-dd").parse(publishDate.trim()));
            } catch (ParseException e) {
                logger.warn("日期格式解析失败: {}", publishDate);
            }
        }
        return book;
    }
}
