package by.andd3dfx.templateapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ArticleDto {

    @Null(message = "Article id shouldn't be present")
    @ApiModelProperty(notes = "The database generated article ID")
    private Long id;

    @NotNull(message = "Title should be populated")
    @Size(min = 1, max = 100, message = "Title length must be between 1 and 100")
    @ApiModelProperty(notes = "Article's title", required = true)
    private String title;

    @Size(max = 255, message = "Summary length shouldn't be greater than 255")
    @ApiModelProperty(notes = "Article's summary")
    private String summary;

    @NotNull(message = "Text should be populated")
    @Size(min = 1, message = "Text length should be 1 at least")
    @ApiModelProperty(notes = "Article's text", required = true)
    private String text;

    @NotNull(message = "Author should be populated")
    @ApiModelProperty(notes = "Article's author", required = true)
    private String author;

    @ApiModelProperty(notes = "Article's location")
    private LocationDto location;

    @Null(message = "DateCreated shouldn't be populated")
    @ApiModelProperty(notes = "Date & time of article creation")
    private LocalDateTime dateCreated;

    @Null(message = "DateUpdated shouldn't be populated")
    @ApiModelProperty(notes = "Date & time of article update")
    private LocalDateTime dateUpdated;
}
