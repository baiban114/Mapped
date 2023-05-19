package com.github.paicoding.forum.service.article.conveter;

import com.github.paicoding.forum.api.model.vo.article.SearchColumnArticleReq;
import com.github.paicoding.forum.service.article.repository.params.SearchColumnArticleParams;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SearchColumnArticleMapper {
    SearchColumnArticleMapper INSTANCE = Mappers.getMapper( SearchColumnArticleMapper.class );

    SearchColumnArticleParams toSearchParams(SearchColumnArticleReq req);
}
