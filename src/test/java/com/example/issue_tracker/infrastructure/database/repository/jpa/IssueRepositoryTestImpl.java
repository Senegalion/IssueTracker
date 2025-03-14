package com.example.issue_tracker.infrastructure.database.repository.jpa;

import com.example.issue_tracker.infrastructure.database.entity.IssueEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class IssueRepositoryTestImpl implements IssueRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends IssueEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends IssueEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<IssueEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

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

    @Override
    public <S extends IssueEntity> S save(S entity) {
        return null;
    }

    @Override
    public <S extends IssueEntity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<IssueEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<IssueEntity> findAll() {
        return null;
    }

    @Override
    public List<IssueEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(IssueEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends IssueEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<IssueEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<IssueEntity> findAll(Pageable pageable) {
        return null;
    }
}
