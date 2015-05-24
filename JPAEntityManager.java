package com.uenpjpa1;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JPAEntityManager {
    private static final EntityManagerFactory emfInstance =
        Persistence.createEntityManagerFactory("transactions-optional");

    private JPAEntityManager() {}

    public static EntityManagerFactory get() {
        return emfInstance;
    }
}