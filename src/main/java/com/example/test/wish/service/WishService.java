package com.example.test.wish.service;

import com.example.test.wish.WishNotFoundException;
import com.example.test.wish.entity.Wish;
import com.example.test.wish.repo.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishService {
    @Autowired private WishRepository repo;

    public List<Wish> listAll() {
        return (List<Wish>) repo.findAll();
    }

    public void save(Wish wish) {
        repo.save(wish);
    }

    public Wish get(Integer wishId) throws WishNotFoundException {
        Optional<Wish> result = repo.findByWishId(wishId);
        if (result.isPresent()) {
            return result.get();
        }
        throw new WishNotFoundException("Could not find any wish with ID " + wishId);
    }

    public void delete(Integer wishId) throws WishNotFoundException {
        Long count = repo.countBywishId(wishId);
        if (count == null || count == 0) {
            throw new WishNotFoundException("Could not find any wish with ID " + wishId);
        }
        repo.deleteById(wishId);
    }
}
