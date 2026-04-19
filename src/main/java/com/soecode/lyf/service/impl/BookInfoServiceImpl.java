package com.soecode.lyf.service.impl;

import com.soecode.lyf.dao.BookInfoMapper;
import com.soecode.lyf.dto.PageResult;
import com.soecode.lyf.entity.BookInfo;
import com.soecode.lyf.service.BookInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookInfoServiceImpl implements BookInfoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Override
    @Transactional
    public void add(BookInfo book) {
        bookInfoMapper.insert(book);
        logger.info("新增图书: {}", book);
    }

    @Override
    @Transactional
    public void update(BookInfo book) {
        bookInfoMapper.update(book);
        logger.info("修改图书: {}", book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookInfoMapper.deleteById(id);
        logger.info("删除图书 id={}", id);
    }

    @Override
    public BookInfo getById(Long id) {
        return bookInfoMapper.selectById(id);
    }

    @Override
    public List<BookInfo> listAll() {
        return bookInfoMapper.selectAll();
    }

    @Override
    public PageResult<BookInfo> page(String name, int pageNo, int pageSize) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 1) {
            pageSize = 5;
        }
        // Oracle 11g ROWNUM 分页参数
        int startRow = (pageNo - 1) * pageSize + 1;
        int endRow   = pageNo * pageSize;

        long total = bookInfoMapper.selectCount(name);
        List<BookInfo> list = bookInfoMapper.selectPage(name, startRow, endRow);
        return new PageResult<>(pageNo, pageSize, total, list);
    }
}
