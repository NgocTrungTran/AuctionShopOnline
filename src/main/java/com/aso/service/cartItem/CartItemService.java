package com.aso.service.cartItem;

import com.aso.model.Cart;
import com.aso.model.CartItem;
import com.aso.model.dto.CartItemListDTO;
import com.aso.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartItemService extends IGeneralService<CartItem> {

    Optional<CartItem> findByProductId(Long productId);

    List<CartItem> findAllByCart(Cart cart);

    BigDecimal getSumAmountByCartId(Long cartId);

    List<CartItemListDTO> findAllCartItemsDTO(@Param("cartId") Long cartId);
//
//    void delete(Long cartItem);

    List<CartItemListDTO> findCartItemDTOById(String title);

Optional<CartItemListDTO> getCartItemDTOById(Long id);

    CartItem SaveIncreasing(CartItem cartItem);
    CartItem SaveReduce(CartItem cartItem);

    Optional<CartItemListDTO> getCartItemDTOByCode(String userName, String code);

    CartItem saveInDetail(CartItem cartItem);

    CartItem saveOp(CartItem cartItem);
}
