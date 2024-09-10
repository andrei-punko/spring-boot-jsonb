package by.andd3dfx.templateapp.persistence.dao;

import by.andd3dfx.templateapp.dto.LocationDto;
import by.andd3dfx.templateapp.persistence.entities.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

    private final static String SELECT_ARTICLES_BY_LOCATION_QUERY = "SELECT * FROM articles a WHERE a.location @> '%s'";

    private final EntityManager em;
    private final ObjectMapper mapper;

    /**
     * See for reference: https://medium.com/hackernoon/how-to-query-jsonb-beginner-sheet-cheat-4da3aa5082a3
     */
    @SneakyThrows
    @Override
    public List<Article> getArticleByCountryNCity(String country, String city) {
        var location = mapper.writeValueAsString(new LocationDto(country, city));
        return em.createNativeQuery(SELECT_ARTICLES_BY_LOCATION_QUERY.formatted(location), Article.class)
                .getResultList();
    }
}
