package com.aso.model.dto;

import com.aso.model.Product;
import com.aso.model.ProductMedia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductMediaDTO {

    private Long id;

    private String fileUrl;

    private Long ts ;

    private ProductDTO product;

    public ProductMediaDTO(Long id, String fileUrl) {
        this.id = id;
        this.fileUrl = fileUrl;
    }

    public ProductMediaDTO(Long id, Long ts , String fileUrl) {
        this.id = id;
        this.ts = ts;
        this.fileUrl = fileUrl;
    }

    public ProductMediaDTO(Long id, String fileUrl, Long ts, Product product) {
        this.id = id;
        this.fileUrl = fileUrl;
        this.ts = ts;
        this.product = product.toProductDTO();
    }

    public ProductMedia toProductMedia() {
       return new ProductMedia()
               .setId(id)
               .setFileUrl(fileUrl)
               .setTs(ts)
               ;
   }
    public ProductMedia toProduct() {
        return new ProductMedia()
                .setId(id)
                .setFileUrl(fileUrl)
                .setProduct(product.toProduct())
                .setTs(ts)
                ;
    }
}
