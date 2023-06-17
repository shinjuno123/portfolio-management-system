package com.amazing.juno.pmsrest.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


public abstract class BasicJpaMethods<T> {

    protected final EntityManager entityManager;

    private final Class<T> typeParameterClass;

    private final String DATABASE_NAME;

    private final String DATABASE_ALIAS;



    protected BasicJpaMethods(EntityManager entityManager, Class<T> typeParameterClass,
                              String DATABASE_NAME, String DATABASE_ALIAS) {
        this.entityManager = entityManager;
        this.typeParameterClass = typeParameterClass;
        this.DATABASE_ALIAS = DATABASE_ALIAS;
        this.DATABASE_NAME = DATABASE_NAME;
    }

    public Optional<T> findById(UUID id){
        TypedQuery<T> typedQuery = entityManager.createQuery(
                String.format("SELECT %s from %s %s WHERE %s.id=:id",DATABASE_ALIAS, DATABASE_NAME, DATABASE_ALIAS, DATABASE_ALIAS),
                typeParameterClass);
        typedQuery.setParameter("id", id);

        List<T> oneData = typedQuery.getResultList();

        if(oneData.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(oneData.get(0));
    }

    @Transactional
    public Optional<UUID> deleteById(UUID id) {
        Optional<T> savedRelevantSiteOptional = findById(id);
        AtomicReference<Optional<UUID>> atomicReference = new AtomicReference<>();

        savedRelevantSiteOptional.ifPresentOrElse(
                (savedRelevantSite) -> {
                    entityManager.remove(savedRelevantSite);
                    atomicReference.set(
                            Optional.of(id)
                    );
                },
                () -> atomicReference.set(Optional.empty())
        );

        return atomicReference.get();
    }

    @Transactional
    public T save(T data){
        entityManager.persist(data);

        return data;
    }

    @Transactional
    public Optional<T> update(T data, UUID id) {
        Optional<T> savedDataOptional = findById(id);
        AtomicReference<Optional<T>> atomicReference = new AtomicReference<>();

        savedDataOptional.ifPresentOrElse(
                (savedData) -> {
                    T updatedData = entityManager.merge(data);
                    atomicReference.set(
                            Optional.of(updatedData)
                    );
                },
                () -> atomicReference.set(Optional.empty())
        );

        return atomicReference.get();
    }

    @Transactional
    public long count() {
        Query query = entityManager.createQuery("SELECT count(*) FROM " + DATABASE_NAME);

        return (long) query.getSingleResult();
    }

}
