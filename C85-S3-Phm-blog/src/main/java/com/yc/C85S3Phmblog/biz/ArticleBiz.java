package com.yc.C85S3Phmblog.biz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.C85S3Phmblog.bean.Article;
import com.yc.C85S3Phmblog.dao.ArticleMapper;



@Service
public class ArticleBiz {

	@Resource
	private ArticleMapper aMapper;

	public int create(Article art) {
		return aMapper.insert(art);
	}

}
