package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public User findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }
}