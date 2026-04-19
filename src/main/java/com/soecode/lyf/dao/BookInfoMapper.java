package com.soecode.lyf.dao;

import com.soecode.lyf.entity.BookInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图书 CRUD Mapper 接口（对应 Oracle book 表）
 */
public interface BookInfoMapper {

    /**
     * 新增图书（主键由 Oracle sequence seq_book_id 生成）
     */
    int insert(BookInfo book);

    /**
     * 修改图书信息
     */
    int update(BookInfo book);

    /**
     * 根据 ID 删除图书
     */
    int deleteById(Long id);

    /**
     * 根据 ID 查询单本图书
     */
    BookInfo selectById(Long id);

    /**
     * 查询所有图书（不分页）
     */
    List<BookInfo> selectAll();

    /**
     * 按书名模糊查询（不分页）
     */
    List<BookInfo> selectByNameLike(String name);

    /**
     * 分页查询（Oracle 11g ROWNUM 写法），支持可选书名模糊过滤
     *
     * @param name     书名关键词，可为 null 表示不过滤
     * @param startRow ROWNUM 起始行（= (pageNo-1)*pageSize + 1）
     * @param endRow   ROWNUM 结束行（= pageNo*pageSize）
     */
    List<BookInfo> selectPage(@Param("name") String name,
                              @Param("startRow") int startRow,
                              @Param("endRow") int endRow);

    /**
     * 分页总数，支持可选书名模糊过滤
     */
    long selectCount(@Param("name") String name);
}
