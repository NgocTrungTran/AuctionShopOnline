package com.aso.service.cartItem;


import com.aso.model.Cart;
import com.aso.model.CartItem;
import com.aso.model.Product;
import com.aso.model.dto.CartItemListDTO;
import com.aso.repository.CartItemRepository;
import com.aso.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CartItem> findAll() {
        return null;
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public Optional<CartItem> findByProductId(Long productId) {
        return cartItemRepository.findByProductId(productId);
    }

    @Override
    public List<CartItem> findAllByCart(Cart cart) {
        return cartItemRepository.findAllByCart(cart);
    }

    @Override
    public BigDecimal getSumAmountByCartId(Long cartId) {
        return cartItemRepository.getSumAmountByCartId(cartId);
    }

    @Override
    public List<CartItemListDTO> findAllCartItemsDTO(Long cartId) {
        return cartItemRepository.findAllCartItemsDTO(cartId);
    }

    @Override
    public CartItem getById(Long id) {
        return null;
    }

    @Override
    public void softDelete(CartItem cartItem) {

    }
    @Override
    public List<CartItemListDTO> findCartItemDTOById(String title) {
        return cartItemRepository.findCartItemDTOById(title);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void delete(Product id) {

    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

//    @Override
//    public void delete(Long cartItem) {
//        cartItemRepository.delete(cartItem);
//    }

    @Override
    public Optional<CartItemListDTO> getCartItemDTOById(Long id) {
        return cartItemRepository.getCartItemDTOById(id);
    }

    @Override
    public CartItem SaveIncreasing(CartItem cartItem) {
        return null;
    }

    @Override
    public CartItem SaveReduce(CartItem cartItem) {
        return null;
    }

//    @Override
//    public CartItem SaveIncreasing(CartItem cartItem) {
//        if(new BigDecimal(3).equals(cartItem.getQuantity())){
//            throw new DataInputException("Số lượng không lớn hơn 3 Sản phẩm!");
//        }
//        Optional<Product> productDTO = productRepository.findById(cartItem.getProduct().getId());
//
//        cartItem.setQuantity(cartItem.getQuantity().add(BigDecimal.valueOf(1)));
//        cartItem.setQuantity(cartItem.getQuantity().add(cartItem.getPrice()));
//        if(productDTO.get().getAvailable().compareTo((long) cartItem.getQuantity()) < 0){
//            throw new DataInputException("Đã hết hàng!");
//        }
//        return cartItemRepository.save(cartItem);
//    }
//    @Override
//    public CartItem SaveReduce(CartItem cartItem) {
//        if(new BigDecimal(1).equals(cartItem.getQuantity())){
//            throw new DataInputException("Số lượng không nhỏ hơn 1 Sản Phẩm");
//        }
//        cartItem.setQuantity(cartItem.getQuantity().subtract(BigDecimal.valueOf(1)));
//        cartItem.setQuantity(cartItem.getQuantity().subtract(cartItem.getPrice()));
//        return cartItemRepository.save(cartItem);
//    }

    @Override
    public Optional<CartItemListDTO> getCartItemDTOByCode(String userName , String code) {
        return cartItemRepository.getCartItemDTOByCode(userName,code);
    }
    @Override
    public CartItem saveInDetail(CartItem cartItem) {
        Optional<CartItemListDTO> cartItem1 = cartItemRepository.getCartItemDTOByCode(cartItem.getTitle(), cartItem.getProduct().getTitle());
        cartItem.setId(cartItem1.get().getId());
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem saveOp(CartItem cartItem) {
        return null;
    }

//    public CartItem saveOp(CartItem cartItem) {
//        Optional<CartItemListDTO> cartItem1 = cartItemRepository.getCartItemDTOByCode(cartItem.getTitle(), cartItem.getProduct().getTitle());
//        Optional<ProductDTO> productDTO = productRepository.findProductDTOById(cartItem.getProduct().getId());
//        if(productDTO.get().getAvailable().compareTo(BigDecimal.ZERO) < 0){
//            productDTO.get().setAvailable("Đã Hết Hàng");
//            productRepository.save(productDTO.get().toProduct());
//        }
//        cartItem.setId(cartItem1.get().getId());
//        cartItem.setQuantity(new BigDecimal(String.valueOf(cartItem1.get().getQuantity().add(BigDecimal.valueOf(1)))));
//        cartItem.setGrandTotal(new BigDecimal(String.valueOf(cartItem.getQuantity().multiply(cartItem.getPrice()))));
//        if(productDTO.get().getQuantity().compareTo(cartItem.getQuantity()) < 0){
//            throw new DataInputException("Đã hết hàng!");
//        }
//        return cartItemRepository.save(cartItem);
//    }

}
