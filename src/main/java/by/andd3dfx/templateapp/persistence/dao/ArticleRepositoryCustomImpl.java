package by.andd3dfx.templateapp.persistence.dao;

import by.andd3dfx.templateapp.persistence.entities.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

    @Autowired
    private EntityManager em;

    /**
     * See for reference: https://medium.com/hackernoon/how-to-query-jsonb-beginner-sheet-cheat-4da3aa5082a3
     */
    @Override
    public List<Article> getArticleByCountryNCity(String country, String city) {
        return em.createNativeQuery("SELECT * " +
                        "FROM articles a " +
                        buildLocationCondition(country, city),
                Article.class)
                .getResultList();
    }

    private String buildLocationCondition(String country, String city) {
        List<String> items = new ArrayList<>();
        if (country != null) {
            items.add("\"country\": \"" + country + "\"");
        }
        if (city != null) {
            items.add("\"city\": \"" + city + "\"");
        }
        if (items.isEmpty()) return "";

        return "WHERE a.location @> '{ " + items.stream().collect(Collectors.joining(",")) + " }'";
    }
}
