package by.andd3dfx.templateapp.persistence.dao;

import by.andd3dfx.templateapp.IntegrationTestInitializer;
import by.andd3dfx.templateapp.persistence.entities.Article;
import by.andd3dfx.templateapp.persistence.entities.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@ContextConfiguration(initializers = IntegrationTestInitializer.class)
@SpringBootTest
public class ArticleRepositoryCustomImplTest {

    @Autowired
    private ArticleRepository repository;

    private Article entity;
    private Article entity2;
    private Article entity3;

    @BeforeEach
    void setUp() {
        entity = buildArticle("Ivan", "HD", "FR", "Brest");
        entity2 = buildArticle("Vasily", "HD", "BY", "Brest");
        entity3 = buildArticle("Ivan", "4K", "BY", "Minsk");
        repository.saveAll(List.of(entity, entity2, entity3));
    }

    public static Article buildArticle(String title, String summary, String country, String city) {
        Article article = new Article();
        article.setTitle(title);
        article.setSummary(summary);
        article.setText("any text");
        article.setTimestamp(LocalDateTime.now());
        article.setAuthor("Pushkin");
        article.setLocation(new Location(country, city));
        return article;
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll(List.of(entity, entity2, entity3));
    }

    @Test
    void getArticleByLocationByCountryNCity() {
        List<Article> result = repository.getArticleByCountryNCity("FR", "Brest");

        assertThat(result.size(), is(1));
        Article article = result.get(0);
        assertThat("Wrong title", article.getTitle(), is("Ivan"));
        assertThat("Wrong summary", article.getSummary(), is("HD"));
        assertThat("Wrong location.country", article.getLocation().getCountry(), is("FR"));
        assertThat("Wrong location.city", article.getLocation().getCity(), is("Brest"));
    }

    @Test
    void getArticleByLocationByCountry() {
        List<Article> result = repository.getArticleByCountryNCity("BY", null);

        assertThat(result.size(), is(2));

        Article article = result.get(0);
        assertThat("Wrong [0].title", article.getTitle(), is("Vasily"));
        assertThat("Wrong [0].summary", article.getSummary(), is("HD"));
        assertThat("Wrong [0].location.country", article.getLocation().getCountry(), is("BY"));
        assertThat("Wrong [0].location.city", article.getLocation().getCity(), is("Brest"));

        Article article2 = result.get(1);
        assertThat("Wrong [1].title", article2.getTitle(), is("Ivan"));
        assertThat("Wrong [1].summary", article2.getSummary(), is("4K"));
        assertThat("Wrong [1].location.country", article2.getLocation().getCountry(), is("BY"));
        assertThat("Wrong [1].location.city", article2.getLocation().getCity(), is("Minsk"));
    }

    @Test
    void getArticleByLocationByCity() {
        List<Article> result = repository.getArticleByCountryNCity(null, "Brest");

        assertThat(result.size(), is(2));

        Article article = result.get(0);
        assertThat("Wrong [0].title", article.getTitle(), is("Ivan"));
        assertThat("Wrong [0].summary", article.getSummary(), is("HD"));
        assertThat("Wrong [0].location.country", article.getLocation().getCountry(), is("FR"));
        assertThat("Wrong [0].location.city", article.getLocation().getCity(), is("Brest"));

        Article article2 = result.get(1);
        assertThat("Wrong [1].title", article2.getTitle(), is("Vasily"));
        assertThat("Wrong [1].summary", article2.getSummary(), is("HD"));
        assertThat("Wrong [1].location.country", article2.getLocation().getCountry(), is("BY"));
        assertThat("Wrong [1].location.city", article2.getLocation().getCity(), is("Brest"));
    }

    @Test
    void getArticleByLocationWhenParamsAreNull() {
        var result = repository.getArticleByCountryNCity(null, null);

        assertThat(result.size(), greaterThan(2));
    }
}
