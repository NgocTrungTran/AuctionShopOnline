package com.aso.model.dto;

import com.aso.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CategoryDTO {
    private Long id;

    private String title;

    private String slug;

    public Category toCategory() {
        return new Category ()
                .setId ( id )
                .setTitle ( title )
                .setSlug ( slug );
    }
}
