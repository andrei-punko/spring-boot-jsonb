package by.andd3dfx.templateapp.persistence.dao;

import by.andd3dfx.templateapp.IntegrationTestInitializer;
import by.andd3dfx.templateapp.persistence.entities.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ContextConfiguration(initializers = IntegrationTestInitializer.class)
@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository repository;

    private Article entity;
    private Article entity2;
    private Article entity3;

    @BeforeEach
    public void setup() {
        entity = buildArticle("Ivan", "HD", LocalDateTime.parse("2010-12-03T10:15:30"));
        entity2 = buildArticle("Vasily", "HD", LocalDateTime.parse("2011-12-03T10:15:30"));
        entity3 = buildArticle("Ivan", "4K", LocalDateTime.parse("2012-12-03T10:15:30"));
        repository.saveAll(List.of(entity, entity2, entity3));
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll(List.of(entity, entity2, entity3));
    }

    @Test
    public void findAll() {
        var result = repository.findAll(Pageable.ofSize(2));

        assertThat("Wrong records amount", result.getNumberOfElements(), is(2));
    }

    @Test
    public void findAll_withPageNSizeNSorting() {
        var result = repository.findAll(PageRequest.of(0, 2, Sort.by("title", "summary")));

        assertThat("Wrong records amount", result.getNumberOfElements(), is(2));
        var articles = result.getContent();

        assertThat(articles.get(0).getTitle(), is(entity3.getTitle()));
        assertThat(articles.get(0).getSummary(), is(entity3.getSummary()));

        assertThat(articles.get(1).getTitle(), is(entity.getTitle()));
        assertThat(articles.get(1).getSummary(), is(entity.getSummary()));
    }

    private static Article buildArticle(String title, String summary, LocalDateTime timestamp) {
        var result = new Article();
        result.setTitle(title);
        result.setSummary(summary);
        result.setText("any text");
        result.setTimestamp(timestamp);
        result.setAuthor("Some author");
        return result;
    }
}
