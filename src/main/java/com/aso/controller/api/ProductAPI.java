package com.aso.controller.api;

import com.aso.exception.AccountInputException;
import com.aso.exception.DataInputException;
import com.aso.exception.DataOutputException;
import com.aso.exception.ResourceNotFoundException;
import com.aso.model.*;
import com.aso.model.dto.*;
import com.aso.model.enums.AuctionType;
import com.aso.model.enums.ItemStatus;
import com.aso.repository.AuctionRepository;
import com.aso.repository.ProductRepository;
import com.aso.service.account.AccountService;
import com.aso.service.auction.AuctionService;
import com.aso.service.bid.BidService;
import com.aso.service.category.CategoryService;
import com.aso.service.product.ProductService;

import com.aso.service.productMedia.ProductMediaService;
import com.aso.utils.AppUtil;
import com.aso.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/products")
//@PreAuthorize("hasAnyAuthority('ADMIN')")
public class ProductAPI {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMediaService productMediaService;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private Validation validation;
    @Autowired
    private AppUtil appUtil;
    @Autowired
    private BidService bidService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> productDTOList = productService.findAllProductsDTO ();

        if ( productDTOList.isEmpty () ) {
            throw new DataOutputException ( "Không có dữ liệu" );
        }

        return new ResponseEntity<> ( productDTOList, HttpStatus.OK );
    }

    @GetMapping("/get-moderated-by-created-by/{createdBy}")
    public ResponseEntity<?> getProductsModerationByCreatedBy(@PathVariable String createdBy) {
        try {
            Optional<Account> accountOptional = accountService.getByEmail ( createdBy );

            if ( accountOptional.isEmpty () ) {
                throw new AccountInputException ( "Tài khoản không tồn tại" );
            }

            List<ProductDTO> productDTOList = productService.getProductsDTOModeratedByCreatedBy ( createdBy );

            if ( productDTOList.isEmpty () ) {
                throw new DataInputException ( "Không có dữ liệu" );
            }

            return new ResponseEntity<> ( productDTOList, HttpStatus.OK );
        } catch (Exception e) {
            throw new DataOutputException ( e.getMessage () );
        }

    }


    @GetMapping("/auctions")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllProductsAuctions() {
        List<ProductDTO> productDTOList = productService.findAllProductsDTOAuctions ();
        if ( productDTOList.isEmpty () ) {
            throw new DataOutputException ( "No data" );
        }
        return new ResponseEntity<> ( productDTOList, HttpStatus.OK );
    }

    @GetMapping("/the-shops")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllProductsTheShop() {
        List<ProductDTO> productDTOList = productService.findAllProductsDTOTheShop ();
        if ( productDTOList.isEmpty () ) {
            throw new DataOutputException ( "No data" );
        }

        return new ResponseEntity<> ( productDTOList, HttpStatus.OK );
    }

    @GetMapping("/moderation")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllProductsModeration() {
        List<ProductListDTO> productDTOList = productService.findAllProductListDTOModeration ();

        if ( productDTOList.isEmpty () ) {
            return new ResponseEntity<> ( productDTOList, HttpStatus.OK );
        }

        return new ResponseEntity<> ( productDTOList, HttpStatus.OK );
    }

    @GetMapping("/p")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<ProductDTO>> getAllBooks(Pageable pageable) {

        String email = appUtil.getPrincipalEmail();
        Page<ProductDTO> productDTOList = productService.findAllProducts(pageable);
//        if (productDTOList.isEmpty()) {
//            throw new DataOutputException("No data");
//        }
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/c")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<ProductDTO>> getAllProductsSort(Pageable pageable, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Page<ProductDTO> productDTOList = productService.findAllProducts ( pageable );
        if ( productDTOList.isEmpty () ) {
            throw new DataOutputException ( "No data" );
        }
        return new ResponseEntity<> ( productService.findAllProducts ( PageRequest.of (
                        pageNumber, pageSize,
                        sortDir.equalsIgnoreCase ( "asc" ) ? Sort.by ( sortBy ).ascending () : Sort.by ( sortBy ).descending ()
                )
        ), HttpStatus.OK );
    }

    // For searching
    @GetMapping("/p/{keyword}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<ProductDTO>> getAllBookss(Pageable pageable, @PathVariable("keyword") String keyword) {
        try {
            keyword = "%" + keyword + "%";

            Page<ProductDTO> productDTOList = productService.findAllProductss(pageable, keyword);
//            if (productDTOList.isEmpty()) {
//                throw new DataOutputException("Danh sách sản phẩm trống");
//            }
            return new ResponseEntity<>(productDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<> ( HttpStatus.BAD_REQUEST );
        }
    }

    //    @GetMapping("/trash")
////    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    public ResponseEntity<?> getAllProductsTrash(@PathVariable String productId) {
//        List<ProductDTO> products = productService.findAllProductsDTOTrash();
////        products.remove(productId);
//        if (products.isEmpty()) {
//            throw new DataOutputException("No data");
//        }
//        if (!validation.isIntValid(productId)) {
//            throw new DataInputException("Product ID invalid!");
//        }
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }
    @GetMapping("/trash")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllProductsTrash() {
        List<ProductDTO> products = productService.findAllProductsDTOTrash ();
        if ( products.isEmpty () ) {
            throw new DataOutputException ( "No data" );
        }
        return new ResponseEntity<> ( products, HttpStatus.OK );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {

        if ( !validation.isIntValid ( productId ) ) {
            throw new DataInputException ( "Product ID invalid!" );
        }
        Long product_id = Long.parseLong ( productId );
        Optional<Product> productOptional = productService.findById ( product_id );
        if ( productOptional.isEmpty () ) {
            throw new ResourceNotFoundException ( "Product invalid" );
        }
        return new ResponseEntity<> ( productOptional.get ().toProductDTO (), HttpStatus.OK );
    }

    @GetMapping("/find-by-slug/{slug}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getProductBySlug(@PathVariable String slug) {

        Optional<Product> productOptional = productService.findBySlug ( slug );

        if ( productOptional.isEmpty () ) {
            throw new ResourceNotFoundException ( "Sản phẩm không tồn tại" );
        }

        return new ResponseEntity<> ( productOptional.get ().toProductDTO (), HttpStatus.OK );
    }

//    @PostMapping("/create")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    public ResponseEntity<?> doAddProduct(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult  ) {
//        new ProductDTO().validate(productDTO, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return appUtil.mapErrorToResponse(bindingResult);
//        }
//
//        String slug = Validation.makeSlug(productDTO.getTitle());
//
//        productDTO.getCategory().setId (0L );
//        Category category = categoryService.save(productDTO.getCategory().toCategory());
//        productDTO.setCategory(category.toCategoryDTO());
//
//        try {
//            Product product = productDTO.toProduct();
//            product.setSlug(slug);
//            product.setCreatedBy (productDTO.getCreatedBy());
//            Product newProduct = productService.save(product);
//
//            return new ResponseEntity<>( newProduct.toProductDTO(), HttpStatus.CREATED);
//
//        } catch (DataIntegrityViolationException e) {
//            throw new DataInputException ("Product information is not valid, please check the information again");
//        }
//    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult) {

        String email = appUtil.getPrincipalEmail ();
        if ( bindingResult.hasErrors () ) {
            return appUtil.mapErrorToResponse ( bindingResult );
        }
        String checkPrice = String.valueOf ( new BigDecimal ( String.valueOf ( productDTO.getPrice () ) ) );
        if ( !checkPrice.toString ().matches ( "\"(^$|[0-9]*$)\"" ) ) {
            productDTO.setSlug ( Validation.makeSlug ( productDTO.getTitle () ) );
            productDTO.setId ( 0L );
            productDTO.setCreatedBy ( email );
            productDTO.toProduct ().setDeleted ( false );
            productDTO.setImages ( productDTO.getImages () );
            if ( !productDTO.getAction () ) {
                productDTO.setCountday ( null );
            }
            if ( productDTO.getAction () ) {
                productDTO.setAvailable ( 1L );
            }
            // Note: thêm category vô đây
            Product newProduct = productService.save ( productDTO.toProduct () );
            for (String p : productDTO.getImages ()) {
                ProductMedia productMedia = new ProductMedia ();
                productMedia.setId ( 0L );
                productMedia.setFileUrl ( p );
                productMediaService.save ( productMedia );
            }
            return new ResponseEntity<> ( newProduct.toProductDTO (), HttpStatus.CREATED );
        }
        throw new DataInputException ( "Tạo mới thất bại" );
    }

    @PutMapping("/edit/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doEdit(@PathVariable Long id, @Validated @RequestBody ProductDTO productDTO,
                                    BindingResult bindingResult) {
        String email = appUtil.getPrincipalEmail ();
        if ( bindingResult.hasErrors () ) {
            return appUtil.mapErrorToResponse ( bindingResult );
        }

        Optional<Product> p = productService.findById ( id );
        if ( p.isEmpty () ) {
            return new ResponseEntity<> ( "Không tồn tại sản phẩm", HttpStatus.NOT_FOUND );
        }

        try {
//            productDTO.setId(id);
            String slug = Validation.makeSlug(productDTO.getTitle());
            p.get().setUpdatedAt(new Date());
            p.get().setUpdatedBy(email);
            p.get().setAction(productDTO.getAction());
            p.get().setAvailable(productDTO.getAvailable());
            p.get().setImage(productDTO.getImage());
            p.get().setPrice(productDTO.getPrice());
            p.get().setSlug(slug);
            p.get().setTitle(productDTO.getTitle());
            p.get().setCategory(productDTO.toProduct().getCategory());
            p.get().setDescription(productDTO.getDescription());
            p.get().setCountday(productDTO.getCountday());
            if (productDTO.getAction()) {
                p.get().setCountday(null);
            }

            for (String pr : productDTO.getImages ()) {
                ProductMedia productMedia = new ProductMedia ();
                productMedia.setId ( 0L );
                productMedia.setFileUrl ( pr );
                productMediaService.save ( productMedia );
            }

            Product newProduct = productService.save ( p.get () );

            return new ResponseEntity<> ( newProduct.toProductDTO (), HttpStatus.OK );

        } catch (Exception e) {
            return new ResponseEntity<> ( "Server error", HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @PutMapping("/moderation/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doModeration(@PathVariable Long id) {
        String email = appUtil.getPrincipalEmail ();
        Optional<Product> p = productService.findById ( id );
        if ( p.isEmpty () ) {
            return new ResponseEntity<> ( "Không tồn tại sản phẩm", HttpStatus.NOT_FOUND );
        }
        try {
            p.get().setModeration(true);
            p.get().setUpdatedBy(email);
            Product newProduct = productService.save(p.get());

            // thêm tạo đấu giá ở đây && sưa lại tạo đấu giá

            AccountDTO accountDTO = accountService.findAccountByEmail(email);
            if (p.get().getAction()) {
                AuctionDTO auction = new AuctionDTO();
                auction.setId(0L);
                auction.setEmail(newProduct.getCreatedBy());
                auction.setCreatedAt(new Date());
                auction.setCreatedBy(newProduct.getCreatedBy());
                auction.setAccount(accountDTO);
                auction.setProduct(p.get().toProductDTO());
                auction.setAuctionType(AuctionType.BIDDING);
                auction.setItemStatus(ItemStatus.NEW);
                auction.setStartingPrice(p.get().getPrice());
                auction.setCurrentPrice(p.get().getPrice());
                auction.setAuctionStartTime(new Date());
                Date dt = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.DATE, Integer.parseInt(p.get().getCountday()));
//                c.add(Calendar.MINUTE, 5);
                dt = c.getTime ();
                auction.setAuctionEndTime ( dt );
                auction.setDaysToEndTime ( Integer.parseInt ( p.get ().getCountday () ) );
                Auction auc = auctionService.createAuction ( auction );
                Bid bid = new Bid ();
                bid.setCreatedBy ( newProduct.getCreatedBy() );
                bid.setAccount ( accountDTO.toAccount () );
                bid.setAuction ( auc );
                bid.setBidPrice ( p.get ().getPrice () );
                bid.setEmail (newProduct.getCreatedBy());
                bid.setEstimatePrice ( p.get ().getEstimatePrice () );
                bidService.save ( bid );
            }
            return new ResponseEntity<> ( newProduct.toProductDTO (), HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<> ( "Server error", HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @PutMapping("/delete-soft/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doDelete(@PathVariable Long id) {


        Optional<Product> optionalProduct = productService.findById ( id );

        if ( optionalProduct.isPresent () ) {
            productService.softDelete ( optionalProduct.get () );
            return new ResponseEntity<> ( "Delete success!", HttpStatus.OK );
        } else {
            return new ResponseEntity<> ( "Delete error!", HttpStatus.BAD_REQUEST );

        }

    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) throws IOException {

        Optional<Product> product = productService.findById ( id );

        if ( product.isPresent () ) {
            productService.delete ( product.get () );

            return new ResponseEntity<> ( HttpStatus.ACCEPTED );
        } else {
            throw new DataInputException ( "Invalid product information" );
        }
    }

    @GetMapping("/search/{title}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    // đã test ok (tìm kiếm theo tên title)
    public ResponseEntity<?> searchProductTitle(@PathVariable String title) {
        List<ProductDTO> productDTOList = productService.findAllBySearchTitle ( title );
        return new ResponseEntity<> ( productDTOList, HttpStatus.OK );
    }

    // viết slug chưa test
    @PutMapping("/update/{slug}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable String slug, @Validated ProductDTO productDTO, BindingResult bindingResult) {
        if ( bindingResult.hasErrors () )
            return appUtil.mapErrorToResponse ( bindingResult );

        Optional<Product> product = productService.findProductBySlug ( slug );

        if ( product.isEmpty () ) {
            throw new DataInputException ( "Invalid product id" );
        }

        productDTO.setId ( product.get ().getId () );

        try {
            productDTO.getCategory ().setTitle ( product.get ().getCategory ().getTitle () );
            productDTO.setId ( product.get ().getId () );
            productService.save ( productDTO.toProduct () );

            return new ResponseEntity<> ( productDTO, HttpStatus.OK );

        } catch (Exception e) {
            return new ResponseEntity<> ( "Server không xử lý được", HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @GetMapping("/product-status-available")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    private ResponseEntity<?> findAllProductAvailable() {
        try {
            List<ProductDTO> productDTOS = productService.findAllProductDTOByAvailable ( "Sản phẩm hiện đang còn hàng" );
            return new ResponseEntity<> ( productDTOS, HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<> ( HttpStatus.BAD_REQUEST );
        }
    }
}
