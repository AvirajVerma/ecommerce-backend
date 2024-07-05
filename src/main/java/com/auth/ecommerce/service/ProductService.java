package com.auth.ecommerce.service;

import com.auth.ecommerce.configuration.JwtRequestFilter;
import com.auth.ecommerce.dao.CartDao;
import com.auth.ecommerce.dao.ProductDao;
import com.auth.ecommerce.dao.UserDao;
import com.auth.ecommerce.entity.Cart;
import com.auth.ecommerce.entity.Product;
import com.auth.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public List<Product> getAllProducts(int pageNumber, String searchKey, String filter) {
        Pageable pageable = PageRequest.of(pageNumber, 12);
        if (searchKey.equals("")) {
            if (filter.equals("lowToHigh")) {
                return productDao.findAllByOrderByProductDiscountedPriceAsc(pageable);
            } else if (filter.equals("highToLow")) {
                return productDao.findAllByOrderByProductDiscountedPriceDesc(pageable);
            } else {
                return productDao.findAll(pageable);
            }
        } else {
            if (filter.equals("lowToHigh")) {
                return productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCaseOrderByProductDiscountedPriceAsc(
                        searchKey, searchKey, pageable);
            } else if (filter.equals("highToLow")) {
                return productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCaseOrderByProductDiscountedPriceDesc(
                        searchKey, searchKey, pageable);
            } else {
                return productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                        searchKey, searchKey, pageable);
            }
        }
    }

    public Product getProductDetailsById(Integer productId){
        return productDao.findById(productId).get();
    }

    public void deleteProductDetails(Integer productId){
        productDao.deleteById(productId);
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId){
        if (isSingleProductCheckout && productId != 0) {

            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
        }
        else {
            String username = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(username).get();
            List<Cart> carts = cartDao.findByUser(user);

            return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());

        }
    }
}














