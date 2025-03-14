package com.example.issue_tracker.infrastructure.database.repository.jpa;

import com.example.issue_tracker.infrastructure.database.entity.IssueEntity;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class IssueRepositoryTestImpl implements IssueRepository {
    private final Map<Long, IssueEntity> database = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public <S extends IssueEntity> S save(S entity) {
        if (entity.getIssueId() == null) {
            entity.setIssueId(idGenerator.getAndIncrement());
        }
        database.put(entity.getIssueId(), entity);
        return entity;
    }

    @Override
    public Optional<IssueEntity> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<IssueEntity> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void deleteAll() {
        database.clear();
    }

    @Override
    public List<IssueEntity> findAll(Sort sort) {
        return findAll();
    }

    @Override
    public Page<IssueEntity> findAll(Pageable pageable) {
        List<IssueEntity> issues = new ArrayList<>(database.values());
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), issues.size());

        List<IssueEntity> pageContent = issues.subList(start, end);
        return new PageImpl<>(pageContent, pageable, issues.size());
    }

    @Override
    public boolean existsById(Long id) {
        return database.containsKey(id);
    }

    @Override
    public long count() {
        return database.size();
    }

    @Override
    public void deleteById(Long id) {
        database.remove(id);
    }

    @Override
    public void delete(IssueEntity entity) {
        database.remove(entity.getIssueId());
    }

    @Override
    public List<IssueEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public <S extends IssueEntity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends IssueEntity> entities) {

    }

    @Override
    public void flush() {
    }

    @Override
    public <S extends IssueEntity> S saveAndFlush(S entity) {
        return save(entity);
    }

    @Override
    public <S extends IssueEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<IssueEntity> entities) {
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
    }

    @Override
    public void deleteAllInBatch() {
    }

    @Override
    public IssueEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public IssueEntity getById(Long aLong) {
        return null;
    }

    @Override
    public IssueEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends IssueEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends IssueEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends IssueEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends IssueEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends IssueEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends IssueEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends IssueEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
