package by.andd3dfx.templateapp.controllers;

import by.andd3dfx.templateapp.IntegrationTestInitializer;
import by.andd3dfx.templateapp.dto.ArticleDto;
import by.andd3dfx.templateapp.dto.ArticleUpdateDto;
import by.andd3dfx.templateapp.persistence.dao.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static by.andd3dfx.templateapp.persistence.dao.ArticleRepositoryCustomImplTest.buildArticle;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ContextConfiguration(initializers = IntegrationTestInitializer.class)
@SpringBootTest
@WebAppConfiguration
class ArticleControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ArticleRepository repository;

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
            .build();
    }

    @Test
    public void createArticle() throws Exception {
        var articleDto = ArticleDto.builder()
                .title("Some tittle value")
                .summary("Some summary value")
                .text("Some text")
                .author("Some author")
                .build();

        mockMvc.perform(post("/articles")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is(articleDto.getTitle())))
                .andExpect(jsonPath("$.summary", is(articleDto.getSummary())))
                .andExpect(jsonPath("$.text", is(articleDto.getText())))
                .andExpect(jsonPath("$.author", is(articleDto.getAuthor())))
                .andExpect(jsonPath("$.dateCreated", notNullValue()))
                .andExpect(jsonPath("$.dateUpdated", notNullValue()));
    }

    @Test
    public void createArticleWithIdPopulated() throws Exception {
        var articleDto = ArticleDto.builder()
                .id(123L)
                .title("Some tittle value")
                .summary("Some summary value")
                .text("Some text")
                .author("Some author")
                .build();

        var exMsg = mockMvc.perform(post("/articles")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Article id shouldn't be present"));
    }

    @Test
    public void createArticleWithoutTitle() throws Exception {
        var articleDto = new ArticleDto();
        articleDto.setSummary("Some summary value");
        articleDto.setText("Some text");
        articleDto.setAuthor("Some author");

        var exMsg = mockMvc.perform(post("/articles")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Title should be populated"));
    }

    @Test
    public void createArticleWithEmptyTitle() throws Exception {
        var articleDto = new ArticleDto();
        articleDto.setTitle("");
        articleDto.setSummary("Some summary value");
        articleDto.setText("Some text");
        articleDto.setAuthor("Some author");

        var exMsg = mockMvc.perform(post("/articles")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Title length must be between 1 and 100"));
    }

    @Test
    public void createArticleWithTooLongTitle() throws Exception {
        var articleDto = new ArticleDto();
        articleDto.setTitle(createStringWithLength(101));
        articleDto.setSummary("Some summary value");
        articleDto.setText("Some text");
        articleDto.setAuthor("Some author");

        var exMsg = mockMvc.perform(post("/articles")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Title length must be between 1 and 100"));
    }

    @Test
    public void createArticleWithTooLongSummary() throws Exception {
        var articleDto = new ArticleDto();
        articleDto.setTitle("Some title");
        articleDto.setSummary(createStringWithLength(260));
        articleDto.setText("Some text");
        articleDto.setAuthor("Some author");

        var exMsg = mockMvc.perform(post("/articles")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Summary length shouldn't be greater than 255"));
    }

    @Test
    public void createArticleWithoutText() throws Exception {
        var articleDto = ArticleDto.builder()
                .title("Some title")
                .summary("Some summary value")
                .author("Some author")
                .build();

        var exMsg = mockMvc.perform(post("/articles")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Text should be populated"));
    }

    @Test
    public void createArticleWithEmptyText() throws Exception {
        var articleDto = new ArticleDto();
        articleDto.setTitle("Some title");
        articleDto.setSummary("Some summary value");
        articleDto.setText("");
        articleDto.setAuthor("Some author");

        var exMsg = mockMvc.perform(post("/articles")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Text length should be 1 at least"));
    }

    @Test
    public void createArticleWithoutAuthor() throws Exception {
        var articleDto = new ArticleDto();
        articleDto.setTitle("Some title");
        articleDto.setSummary("Some summary value");
        articleDto.setText("Some text");

        var exMsg = mockMvc.perform(post("/articles")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Author should be populated"));
    }

    @Test
    public void createArticleWithDateCreatedPopulated() throws Exception {
        var articleDto = new ArticleDto();
        articleDto.setTitle("Some tittle value");
        articleDto.setSummary("Some summary value");
        articleDto.setText("Some text");
        articleDto.setAuthor("Some author");
        articleDto.setDateCreated(LocalDateTime.now());

        var exMsg = mockMvc.perform(post("/articles")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("DateCreated shouldn't be populated"));
    }

    @Test
    public void createArticleWithDateUpdatedPopulated() throws Exception {
        var articleDto = new ArticleDto();
        articleDto.setTitle("Some tittle value");
        articleDto.setSummary("Some summary value");
        articleDto.setText("Some text");
        articleDto.setAuthor("Some author");
        articleDto.setDateUpdated(LocalDateTime.now());

        var exMsg = mockMvc.perform(post("/articles")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("DateUpdated shouldn't be populated"));
    }

    @Test
    public void deleteArticle() throws Exception {
        mockMvc.perform(delete("/articles/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteAbsentArticle() throws Exception {
        mockMvc.perform(delete("/articles/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readArticle() throws Exception {
        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void readAbsentArticle() throws Exception {
        mockMvc.perform(get("/articles/345"))
                .andExpect(status().isNotFound());
    }

    /**
     * Search by location using JSONB fields: location country & location city
     */
    @Test
    public void readArticlesByLocationWithCountryAndCity() throws Exception {
        var entity = buildArticle("Ivan", "HD", "FR", "Brest");
        var entity2 = buildArticle("Vasily", "HD", "BY", "Brest");
        var entity3 = buildArticle("Ivan-007", "4K", "BY", "Minsk");
        repository.saveAll(List.of(entity, entity2, entity3));

        mockMvc.perform(get("/articles")
                        .param("country", "BY")
                        .param("city", "Minsk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].title", is("Ivan-007")))
                .andExpect(jsonPath("$[0].summary", is("4K")))
                .andExpect(jsonPath("$[0].location.country", is("BY")))
                .andExpect(jsonPath("$[0].location.city", is("Minsk")));

        repository.deleteAll(List.of(entity, entity2, entity3));
    }

    /**
     * Search by location using JSONB fields: location without any fields populated
     */
    @Test
    public void readArticlesByLocationWhenBothCountryAndCityAreNull() throws Exception {
        var entity = buildArticle("Ivan", "HD", "FR", "Brest");
        var entity2 = buildArticle("Vasily", "HD", "BY", "Brest");
        var entity3 = buildArticle("Ivan-007", "4K", "BY", "Minsk");
        repository.saveAll(List.of(entity, entity2, entity3));

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(2)));

        repository.deleteAll(List.of(entity, entity2, entity3));
    }

    /**
     * Search by location using JSONB fields: location country
     */
    @Test
    public void readArticlesByLocationWithCountry() throws Exception {
        var entity = buildArticle("Ivan", "HD", "FR", "Brest");
        var entity2 = buildArticle("Vasily", "HD", "BY", "Brest");
        var entity3 = buildArticle("Ivan-007", "4K", "BY", "Minsk");
        repository.saveAll(List.of(entity, entity2, entity3));

        mockMvc.perform(get("/articles")
                        .param("country", "FR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].title", is("Ivan")))
                .andExpect(jsonPath("$[0].summary", is("HD")))
                .andExpect(jsonPath("$[0].location.country", is("FR")))
                .andExpect(jsonPath("$[0].location.city", is("Brest")));

        repository.deleteAll(List.of(entity, entity2, entity3));
    }

    /**
     * Search by location using JSONB fields: location city
     */
    @Test
    public void readArticlesByLocationWithCity() throws Exception {
        var entity = buildArticle("Ivan", "HD", "FR", "Brest");
        var entity2 = buildArticle("Vasily", "HD", "BY", "Brest");
        var entity3 = buildArticle("Ivan-007", "4K", "BY", "Minsk");
        repository.saveAll(List.of(entity, entity2, entity3));

        mockMvc.perform(get("/articles")
                        .param("city", "Brest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].title", is("Ivan")))
                .andExpect(jsonPath("$[0].summary", is("HD")))
                .andExpect(jsonPath("$[0].location.country", is("FR")))
                .andExpect(jsonPath("$[0].location.city", is("Brest")))
                .andExpect(jsonPath("$[1].title", is("Vasily")))
                .andExpect(jsonPath("$[1].summary", is("HD")))
                .andExpect(jsonPath("$[1].location.country", is("BY")))
                .andExpect(jsonPath("$[1].location.city", is("Brest")));

        repository.deleteAll(List.of(entity, entity2, entity3));
    }

    @Test
    public void readArticles() throws Exception {
        mockMvc.perform(get("/articles/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(10))))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.size", is(50)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(10)));
    }

    @Test
    public void readArticlesWithPageSizeLimit() throws Exception {
        mockMvc.perform(get("/articles/all")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.size", is(5)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.totalElements", greaterThan(5)));
    }

    @Test
    public void updateArticleTitle() throws Exception {
        var articleUpdateDto = ArticleUpdateDto.builder()
                .title("Some tittle value")
                .build();

        mockMvc.perform(patch("/articles/2")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleUpdateDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateArticleSummary() throws Exception {
        var articleUpdateDto = ArticleUpdateDto.builder()
                .summary("Some summary value")
                .build();

        mockMvc.perform(patch("/articles/2")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleUpdateDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateArticleText() throws Exception {
        var articleUpdateDto = new ArticleUpdateDto();
        articleUpdateDto.setText("Some text value");

        mockMvc.perform(patch("/articles/2")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleUpdateDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAbsentArticle() throws Exception {
        var articleUpdateDto = ArticleUpdateDto.builder()
                .title("q")
                .build();

        mockMvc.perform(patch("/articles/123")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleUpdateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateArticleWithEmptyTitle() throws Exception {
        var articleUpdateDto = ArticleUpdateDto.builder()
                .title("")
                .build();

        var exMsg = mockMvc.perform(patch("/articles/2")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleUpdateDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Title length must be between 1 and 100"));
    }

    @Test
    public void updateArticleWithTooLongTitle() throws Exception {
        var articleUpdateDto = ArticleUpdateDto.builder()
                .title(createStringWithLength(101))
                .build();

        var exMsg = mockMvc.perform(patch("/articles/2")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleUpdateDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Title length must be between 1 and 100"));
    }

    @Test
    public void updateArticleWithTooLongSummary() throws Exception {
        var articleUpdateDto = ArticleUpdateDto.builder()
                .summary(createStringWithLength(260))
                .build();

        var exMsg = mockMvc.perform(patch("/articles/2")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleUpdateDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Summary length shouldn't be greater than 255"));
    }

    @Test
    public void updateArticleWithEmptyText() throws Exception {
        var articleUpdateDto = ArticleUpdateDto.builder()
                .text("")
                .build();

        var exMsg = mockMvc.perform(patch("/articles/2")
                        .contentType(APPLICATION_JSON)
                        .content(json(articleUpdateDto)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertThat(exMsg, containsString("Text length should be 1 at least"));
    }

    @Test
    public void checkSwaggerUiLinkAvailability() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().isFound());
    }

    private String json(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }

    private String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < length; index++) {
            builder.append("a");
        }
        return builder.toString();
    }
}
