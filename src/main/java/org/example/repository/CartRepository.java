package org.example.repository;

import org.example.model.Cart;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CartRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Cart findByUserId(Long userId) {
        try {
            return entityManager.createQuery("SELECT c FROM Cart c WHERE c.user.id = :userId", Cart.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void save(Cart cart) {
        if (cart.getId() == null) {
            entityManager.persist(cart);
        } else {
            entityManager.merge(cart);
        }
    }
}