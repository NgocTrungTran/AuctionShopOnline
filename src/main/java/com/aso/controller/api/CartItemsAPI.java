package com.aso.controller.api;

import com.aso.exception.DataInputException;
import com.aso.exception.DataOutputException;
import com.aso.model.Account;
import com.aso.model.CartItem;
import com.aso.model.Product;
import com.aso.model.dto.CartItemDTO;
import com.aso.service.account.AccountService;
import com.aso.service.cart.CartService;
import com.aso.service.cartItem.CartItemService;
import com.aso.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemsAPI {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private AppUtil appUtils;

    @Autowired
    private CartService cartService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    // đã test ok
    public ResponseEntity<?> getCartByAccountId(@PathVariable Long accountId) {
        try {
            List<CartItemDTO> cartItemsDTO = cartItemService.findCartItemDTOByAccountId(accountId);
            return new ResponseEntity<>(cartItemsDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Không lấy được danh sách sản phảm trong giỏ hàng");
        }
    }

    @GetMapping("/id/{id}")
    // đã test ok
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        try {
            Optional<CartItemDTO> cartItemsDTO = cartItemService.getCartItemDTOById(id);
            return new ResponseEntity<>(cartItemsDTO.get(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Không lấy được cart item", HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/reduce/{cartItemId}")
    public ResponseEntity<?> reduceCartItem(@PathVariable Long cartItemId) {
        try {
            Optional<CartItem> cartItemOptional = cartItemService.findById ( cartItemId );
            if ( !cartItemOptional.isPresent () ) {
                throw new DataOutputException ( "Không tồn tại sản phẩm này trong giỏ hàng" );
            }

            int currentQuantity = cartItemOptional.get ().getQuantity ();
            boolean checkQuantity = (currentQuantity - 1) < 1;

            CartItem newCartItem = cartItemOptional.get ();

            if ( checkQuantity ) {
                return new ResponseEntity<>("Số lượng sản phẩm không thể nhỏ hơn 1", HttpStatus.NO_CONTENT);
            }

            newCartItem.setQuantity ( currentQuantity - 1 );
            newCartItem.setAmountTransaction ( newCartItem.getPrice ().multiply ( BigDecimal.valueOf ( newCartItem.getQuantity () ) ) );
            return new ResponseEntity<>(cartItemService.save ( newCartItem ).toCartItemListDTO () , HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>("Không thể giảm số lượng sản phẩm", HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/increasing/{cartItemId}")
    public ResponseEntity<?> increasingCartItem(@PathVariable Long cartItemId) {
        try {
            Optional<CartItem> cartItemOptional = cartItemService.findById ( cartItemId );
            if ( !cartItemOptional.isPresent () ) {
                throw new DataOutputException ( "Không tồn tại sản phẩm này trong giỏ hàng" );
            }
            CartItem newCartItem = cartItemOptional.get ();

            Product product = cartItemOptional.get ().getProduct ();

            int currentQuantity = cartItemOptional.get ().getQuantity ();
            boolean checkQuantity = (currentQuantity + 1) > product.getAvailable ();

            if ( checkQuantity ) {
                return new ResponseEntity<>("Số lượng cần mua không thể lớn hơn số lượng sản phẩm còn lại", HttpStatus.NO_CONTENT);
            }

            newCartItem.setQuantity ( currentQuantity + 1 );
            newCartItem.setAmountTransaction ( newCartItem.getPrice ().multiply ( BigDecimal.valueOf ( newCartItem.getQuantity () ) ) );
            return new ResponseEntity<>(cartItemService.save ( newCartItem ).toCartItemListDTO () , HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>("Không thể giảm số lượng sản phẩm", HttpStatus.NO_CONTENT);
        }
    }
    @PutMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId) {
        try {
            Optional<CartItem> cartItemOptional = cartItemService.findById ( cartItemId );
            if ( cartItemOptional.isEmpty () ) {
                throw new DataOutputException ( "Không tồn tại sản phẩm này trong giỏ hàng" );
            }
            CartItem newCartItem = cartItemOptional.get ();

            newCartItem.setDeleted ( true );
            cartItemService.save ( newCartItem );

            List<CartItemDTO> cartItemsDTO = cartItemService.findCartItemDTOByAccountId(newCartItem.getCart ().getAccount ().getId ());

            return new ResponseEntity<>(cartItemsDTO , HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>("Không thể xóa sản phẩm ra khỏi giỏ hàng", HttpStatus.NO_CONTENT);
        }
    }
    @PutMapping("/remove-list/{accountId}")
    public ResponseEntity<?> removeCartItems(@PathVariable Long accountId, @RequestBody List<CartItemDTO> cartItems) {
        try {
            Optional<Account> accountOptional = accountService.findById ( accountId );
            if ( accountOptional.isEmpty () ) {
                return new ResponseEntity<>("Tài khoản không tồn tại", HttpStatus.NO_CONTENT);
            }
            if ( cartItems.isEmpty () ) {
                return new ResponseEntity<>("Danh sách rỗng", HttpStatus.NO_CONTENT);
            }
            List<CartItemDTO> newCartItems = cartItemService.doRemoveCartItems ( accountId, cartItems );

            return new ResponseEntity<>( newCartItems, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>("Không thể thực hiện thao tác", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/create/{accountId}")
    public ResponseEntity<?> doCreateCart(@PathVariable("accountId") Long accountId, @RequestBody CartItemDTO cartItemsDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            cartItemService.doSaveCartItem ( accountId, cartItemsDTO );
            return new ResponseEntity<>("Thêm vào giỏ hàng thành công", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new DataInputException("không đủ số lượng sản phẩm để thêm vào giỏ hàng!!");
        }
    }
}
