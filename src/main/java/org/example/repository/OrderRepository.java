package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.model.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Order order) {
        if (order.getId() == null) {
            entityManager.persist(order);
        } else {
            entityManager.merge(order);
        }
    }

    public List<Order> findByUserId(Long userId) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.user.id = :userId", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}