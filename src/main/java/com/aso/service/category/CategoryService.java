package com.aso.service.category;


import com.aso.model.Category;
import com.aso.model.dto.CategoryDTO;
import com.aso.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface CategoryService extends IGeneralService<Category> {
    List<CategoryDTO> findAllCategoryDTO();
    Optional<CategoryDTO> findCategoryDTOById(Long id);

    void deleteCategory( Long id);

    Boolean existsCategoryByTitle(String title);
}
