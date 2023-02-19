package Lesson5;

import Lesson5.api.CategoryService;
import Lesson5.dto.GetCategoryResponse;
import Lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetCategoryTest {
    static CategoryService categoryService;
    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }
        @SneakyThrows
        @Test
        void getCategoryById() {
            Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();

            assertThat(response.isSuccessful(), CoreMatchers.is(true));
            assertThat(response.body().getId(), equalTo(1));
            assertThat(response.body().getTitle(), equalTo("Food"));
            response.body().getProducts().forEach(product ->
                    assertThat(product.getCategoryTitle(), equalTo("Food")));
        }

}
