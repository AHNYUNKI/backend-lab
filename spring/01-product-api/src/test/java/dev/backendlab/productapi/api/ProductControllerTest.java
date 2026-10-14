package dev.backendlab.productapi.api;

import dev.backendlab.productapi.api.request.ProductCreateRequest;
import dev.backendlab.productapi.application.ProductService;
import dev.backendlab.productapi.application.response.ProductResponse;
import dev.backendlab.productapi.exception.BusinessException;
import dev.backendlab.productapi.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockitoBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void 상품_등록_API() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.of(1L, "test", 1000, 10);

        given(productService.createProduct(any(ProductCreateRequest.class)))
                .willReturn(ProductResponse.of(1L, "test", 1000, 10));
        //when //then
        mockMvc.perform(post("/v1/products")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("test"))
                .andExpect(jsonPath("$.data.price").value(1000))
                .andExpect(jsonPath("$.data.stock").value(10));;
    }

    @Test
    void Service가_BusinessException을_던지면_HTTP_409와_중복_메시지를_반환한다() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.of(2L, "test", 1000, 10);

        given(productService.createProduct(any(ProductCreateRequest.class)))
                .willThrow(new BusinessException(ErrorCode.ALREADY_EXISTS_PRODUCT_NAME));

        mockMvc.perform(post("/v1/products")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_EXISTS_PRODUCT_NAME.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_EXISTS_PRODUCT_NAME.getMessage()));
    }

}