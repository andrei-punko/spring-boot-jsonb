package by.andd3dfx.templateapp;

import by.andd3dfx.templateapp.dto.ArticleDto;
import by.andd3dfx.templateapp.dto.LocationDto;
import by.andd3dfx.templateapp.services.impl.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ContextConfiguration(initializers = IntegrationTestInitializer.class)
@SpringBootTest
class SpringBootJsonbAppTest {

	@Autowired
	ArticleService articleService;

	@Test
	void contextLoads() {
		var article = new ArticleDto();
		article.setTitle("Voina i mir");
		article.setAuthor("Lev Tolstoj");
		article.setText("Some text");
		article.setLocation(new LocationDto("US", "New York"));
		var createdArticle = articleService.create(article);

		var savedArticle = articleService.get(createdArticle.getId());
		assertThat(savedArticle.getTitle(), is(article.getTitle()));
		assertThat(savedArticle.getAuthor(), is(article.getAuthor()));
		assertThat(savedArticle.getText(), is(article.getText()));
		assertThat(savedArticle.getLocation(), is(article.getLocation()));
	}
}
