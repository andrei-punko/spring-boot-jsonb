package by.andd3dfx.templateapp;

import by.andd3dfx.templateapp.dto.ArticleDto;
import by.andd3dfx.templateapp.persistence.dao.ArticleRepository;
import by.andd3dfx.templateapp.persistence.entities.Article;
import by.andd3dfx.templateapp.services.impl.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ContextConfiguration(initializers = IntegrationTestInitializer.class)
@SpringBootTest
class SpringBootTemplateApplicationTests {

	@Autowired
	ArticleService articleService;

	@Test
	void contextLoads() {
		ArticleDto article = new ArticleDto();
		article.setTitle("Voina i mir");
		article.setAuthor("Lev Tolstoj");
		article.setText("Some text");
		article.setJsonData("{\"key\": \"value\"}");
		ArticleDto createdArticle = articleService.create(article);

		ArticleDto savedArticle = articleService.get(createdArticle.getId());
		assertThat(savedArticle.getTitle(), is(article.getTitle()));
		assertThat(savedArticle.getAuthor(), is(article.getAuthor()));
		assertThat(savedArticle.getText(), is(article.getText()));
		assertThat(savedArticle.getJsonData(), is(article.getJsonData()));
	}
}
