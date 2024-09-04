package by.andd3dfx.templateapp.persistence.dao;

import by.andd3dfx.templateapp.persistence.entities.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long>, ArticleRepositoryCustom {

    @Override
    List<Article> getArticleByCountryNCity(String country, String city);

    Slice<Article> findAll(Pageable pageable);
}
