package com.example.test.wish.repo;

import com.example.test.wish.entity.Wish;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WishRepository extends CrudRepository<Wish, Integer> {
    public Long countBywishId(Integer wishId);

    Optional<Wish> findByWishId(Integer wishId);
}
