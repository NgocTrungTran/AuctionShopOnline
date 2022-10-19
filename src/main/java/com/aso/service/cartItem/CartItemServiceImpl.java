package com.aso.service.cartItem;


import com.aso.exception.AccountInputException;
import com.aso.exception.DataInputException;
import com.aso.model.Account;
import com.aso.model.Cart;
import com.aso.model.CartItem;
import com.aso.model.Product;
import com.aso.model.dto.CartDTO;
import com.aso.model.dto.CartItemDTO;
import com.aso.repository.CartItemRepository;
import com.aso.repository.ProductRepository;
import com.aso.service.account.AccountService;
import com.aso.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CartService cartService;

    @Autowired
    private AccountService accountService;

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
    public List<CartItemDTO> findAllCartItemsDTO(Long cartId) {
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
    public List<CartItemDTO> findCartItemDTOByAccountId(Long accountId) {
        return cartItemRepository.findCartItemDTOByAccountId(accountId);
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
    public Optional<CartItemDTO> getCartItemDTOById(Long id) {
        return cartItemRepository.getCartItemDTOById(id);
    }

    @Override
    public CartItem SaveReduce(CartItem cartItem) {
        return null;
    }

    @Override
    public CartItem saveIncreasing(Long accountId, CartItemDTO cartItemDTO) {
        Optional<Account> accountOptional = accountService.findById ( accountId );

        if ( !accountOptional.isPresent () ) {
            throw new AccountInputException ("Tài khoản không tồn tại");
        }

        Optional<Product> product = productRepository.findById(cartItemDTO.getProduct().getId());

        if ( !product.isPresent () ) {
            throw new DataInputException("Sản phẩm không tồn tại!");
        } else {
            cartItemDTO.setProduct ( product.get ().toProductDTO () );
        }

        Optional<CartDTO> cartDTOOptional = cartService.findCartDTOByIdAccountInfo ( accountId );

        if ( !cartDTOOptional.isPresent () ) {
            Cart cart = new Cart ();
            cart.setAccount ( accountOptional.get () );
            Cart newCart = cartService.save ( cart );

            cartItemDTO.setCart ( newCart.toCartDTO () );
        } else {
            cartItemDTO.setCart ( cartDTOOptional.get () );
        }

        Optional<CartItemDTO> cartItemDTOOptional = cartItemRepository.getCartItemDTOByProductAndAccount ( cartItemDTO.getCart ().getAccount ().getId (), cartItemDTO.getProduct ().getId () );
        if ( cartItemDTOOptional.isPresent () ) {
            cartItemDTO.setId ( cartItemDTOOptional.get ().getId () );
            cartItemDTO.setQuantity ( cartItemDTOOptional.get ().getQuantity () + cartItemDTO.getQuantity () );
        } else {
            cartItemDTO.setPrice ( product.get ().getPrice () );
        }
        cartItemDTO.setPrice ( product.get ().getPrice () );
        cartItemDTO.setAmountTransaction ( product.get ().getPrice ().multiply ( BigDecimal.valueOf ( cartItemDTO.getQuantity () ) ) );

        return cartItemRepository.save(cartItemDTO.toCartItem ());
    }
//    public CartItem SaveReduce(CartItem cartItem) {
//        if(new BigDecimal(1).equals(cartItem.getQuantity())){
//            throw new DataInputException("Số lượng không nhỏ hơn 1 Sản Phẩm");
//        }
//        cartItem.setQuantity(cartItem.getQuantity().subtract(BigDecimal.valueOf(1)));
//        cartItem.setQuantity(cartItem.getQuantity().subtract(cartItem.getPrice()));
//        return cartItemRepository.save(cartItem);
//    }

    @Override
    public Optional<CartItemDTO> getCartItemDTOByCode(String title , String code) {
        return null;
    }
    @Override
    public CartItem saveInDetail(CartItem cartItem) {
//        Optional<CartItemDTO> cartItem1 = cartItemRepository.getCartItemDTOByCode(cartItem.getTitle(), cartItem.getProduct().getTitle());
//        cartItem.setId(cartItem1.get().getId());
        return null;
    }

    @Override
    public CartItem saveOp(CartItem cartItem) {
        return null;
    }

//    public CartItem saveOp(CartItem cartItem) {
//        Optional<CartItemDTO> cartItem1 = cartItemRepository.getCartItemDTOByCode(cartItem.getTitle(), cartItem.getProduct().getTitle());
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
