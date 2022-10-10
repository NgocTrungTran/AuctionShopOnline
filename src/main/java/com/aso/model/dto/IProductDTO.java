package com.aso.model.dto;

import java.math.BigDecimal;

public interface IProductDTO {

    String getId();

    String getProductName();

    BigDecimal getPrice();

    int getQuantity();

    String getDescription();

    String getFileId();

    String getFileName();

    String getFileFolder();

    String getFileUrl();

    String getFileType();


}