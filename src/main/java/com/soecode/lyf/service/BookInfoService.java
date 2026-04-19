package com.soecode.lyf.service;

import com.soecode.lyf.dto.PageResult;
import com.soecode.lyf.entity.BookInfo;

import java.util.List;

/**
 * 图书管理业务接口（CRUD + 分页 + 模糊查询）
 */
public interface BookInfoService {

    /**
     * 新增图书
     */
    void add(BookInfo book);

    /**
     * 修改图书
     */
    void update(BookInfo book);

    /**
     * 根据 ID 删除图书
     */
    void delete(Long id);

    /**
     * 根据 ID 查询单本图书
     */
    BookInfo getById(Long id);

    /**
     * 查询所有图书（不分页）
     */
    List<BookInfo> listAll();

    /**
     * 分页查询（支持可选书名模糊过滤）
     *
     * @param name     书名关键词，null 或空串表示不过滤
     * @param pageNo   页码，从 1 开始
     * @param pageSize 每页条数
     */
    PageResult<BookInfo> page(String name, int pageNo, int pageSize);
}
