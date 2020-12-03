package com.example.demo.dto;

import com.example.demo.dto.enumeration.PostRange;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.List;

public class TopPostRequest {
    @ApiModelProperty(value = "Промежуток, за который необходимо получить список записей сообществ", required = true)
    private PostRange range;
    @ApiModelProperty(value = "Список интересующих жанров", required = true)
    private List<String> genres;

    public PostRange getRange() {
        return range;
    }

    public void setRange(PostRange range) {
        this.range = range;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
