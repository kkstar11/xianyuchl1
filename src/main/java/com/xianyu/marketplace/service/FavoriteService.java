package com.xianyu.marketplace.service;

import com.xianyu.marketplace.entity.Favorite;
import com.xianyu.marketplace.entity.Product;
import com.xianyu.marketplace.entity.User;
import com.xianyu.marketplace.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;
    
    public Favorite addFavorite(User user, Product product) {
        // Check if already favorited
        Optional<Favorite> existing = favoriteRepository.findByUserAndProduct(user, product);
        if (existing.isPresent()) {
            return existing.get();
        }
        
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);
        return favoriteRepository.save(favorite);
    }
    
    public void removeFavorite(User user, Product product) {
        favoriteRepository.deleteByUserAndProduct(user, product);
    }
    
    public List<Favorite> getUserFavorites(User user) {
        return favoriteRepository.findByUser(user);
    }
    
    public boolean isFavorited(User user, Product product) {
        return favoriteRepository.findByUserAndProduct(user, product).isPresent();
    }
}
