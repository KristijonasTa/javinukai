package lt.javinukai.javinukai.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import lt.javinukai.javinukai.entity.Category;
import lt.javinukai.javinukai.enums.PhotoSubmissionType;
import lt.javinukai.javinukai.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

//    @Test
//    public void createCategoryReturnsCategory() {
//
//        final UUID id1 = UUID.randomUUID();
//        final Category category1 = Category.builder()
//                .id(id1)
//                .categoryName("test category name 1")
//                .description("test category description 1")
//                .totalSubmissions(66)
//                .type(PhotoSubmissionType.COLLECTION)
//                .build();
//
//        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
//        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category1);
//
//        final CategoryCreationResponse creationResponse = categoryService
//                .createCategory(CategoryMapper.categoryToCategoryDTO(category1));
//
//        Assertions.assertThat(creationResponse).isNotNull();
////        Assertions.assertThat(creationResponse)..getId()).isNotNull();
//    }



//    @Test
//    public void createCategoryReturnsNull() {
//
//        CategoryDTO categoryDTO = CategoryDTO.builder()
//                .categoryName("test category name")
//                .description("testCategory description")
//                .totalSubmissions(66)
//                .build();
//        Category category = CategoryMapper.categoryDTOToCategory(categoryDTO);
//        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));
//
//        final Category createdCategory = categoryService.createCategory(categoryDTO);
//
//        Assertions.assertThat(createdCategory).isNull();
//    }

    @Test
    public void retrieveAllCategoriesReturnsPage_Success() {

        final UUID id1 = UUID.randomUUID();
        final Category category1 = Category.builder()
                .id(id1)
                .name("test category name 1")
                .description("test category description 1")
                .maxSubmissions(66)
                .type(PhotoSubmissionType.COLLECTION)
                .build();

        final UUID id2 = UUID.randomUUID();
        final Category category2 = Category.builder()
                .id(id2)
                .name("test category name 2")
                .description("test category description 2")
                .maxSubmissions(66)
                .type(PhotoSubmissionType.COLLECTION)
                .build();

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);

        Page<Category> page = new PageImpl<>(categoryList);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Category> result = categoryService.retrieveAllCategories(Pageable.unpaged(), null);

        assertEquals(categoryList.size(), result.getTotalElements());
        assertEquals(categoryList, result.getContent());
        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void retrieveAllCategoriesReturnsPage_Fail() {

        final UUID id1 = UUID.randomUUID();
        final Category category1 = Category.builder()
                .id(id1)
                .name("test category name 1")
                .description("test category description 1")
                .maxSubmissions(66)
                .type(PhotoSubmissionType.COLLECTION)
                .build();

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);

        Page<Category> page = new PageImpl<>(categoryList);

        when(categoryRepository.findByName(anyString(), any(Pageable.class))).thenReturn(page);

        Page<Category> result = categoryService.retrieveAllCategories(Pageable.unpaged(), "test category name 1");

        assertEquals(categoryList.size(), result.getTotalElements());
        assertEquals(categoryList, result.getContent());
        verify(categoryRepository, times(1)).findByName(anyString(), any(Pageable.class));
    }

//    @Test
//    public void retrieveCategoryReturnsCategory() {
//
//        final UUID id = UUID.randomUUID();
//        final Category category = new Category(id,
//                "test category name",
//                "testCategory description",
//                66,
//                null, null, null, null);
//
//        when(categoryRepository.findById(id)).thenReturn(Optional.ofNullable(category));
//        final Category createdCategory = categoryService.retrieveCategory(id);
//
//        Assertions.assertThat(createdCategory).isNotNull();
//        Assertions.assertThat(createdCategory.getId()).isNotNull();
//    }

    @Test
    public void retrieveCategoryReturnsNotFound() {

        UUID idWrong = UUID.randomUUID();

        when(categoryRepository.findById(idWrong)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.retrieveCategory(idWrong);
        });

        Assertions.assertThat(exception.getMessage())
                .isEqualTo("Category was not found with ID: " + idWrong);
    }

//    @Test
//    public void updateCategoryReturnsCategory() {
//
//        final CategoryDTO categoryDTO = CategoryDTO.builder()
//                .categoryName("test category name")
//                .description("testCategory description")
//                .totalSubmissions(66)
//                .build();
//
//        final UUID id = UUID.randomUUID();
//        final Category category = new Category(id,
//                categoryDTO.getCategoryName(),
//                categoryDTO.getDescription(),
//                categoryDTO.getTotalSubmissions(),
//                null, null, null, null);
//
//        when(categoryRepository.findById(id)).thenReturn(Optional.ofNullable(category));
//        when(categoryRepository.save(category)).thenReturn(category);
//        final Category updatedCategory = categoryService.updateCategory(id, categoryDTO);
//
//        Assertions.assertThat(updatedCategory).isNotNull();
//        Assertions.assertThat(updatedCategory.getId()).isNotNull();
//    }

    @Test
    public void deleteCategorySuccess() {

        final UUID id = UUID.randomUUID();
        final Category category = Category.builder()
                .id(id)
                .name("test category name")
                .description("test category description")
                .maxSubmissions(66)
                .type(PhotoSubmissionType.COLLECTION)
                .build();

        when(categoryRepository.existsById(id)).thenReturn(true);

        assertAll(() -> categoryService.deleteCategory(id));
    }

    @Test
    public void deleteCategoryFail() {

        final UUID id = UUID.randomUUID();
        final Category category = Category.builder()
                .id(id)
                .name("test category name")
                .description("test category description")
                .maxSubmissions(66)
                .type(PhotoSubmissionType.COLLECTION)
                .build();

        when(categoryRepository.existsById(id)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.deleteCategory(id);
        });

        Assertions.assertThat(exception.getMessage()).
                isEqualTo("Category was not found with ID: " + id);
    }
}