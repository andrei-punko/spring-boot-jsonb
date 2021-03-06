package by.andd3dfx.templateapp.persistence.dao;

import by.andd3dfx.templateapp.persistence.entities.Article;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long>, ArticleRepositoryCustom {

    List<Article> getArticleByCountryNCity(String country, String city);
}
