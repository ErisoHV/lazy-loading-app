package com.lazyloading.repository;

import org.springframework.data.repository.CrudRepository;

import com.lazyloading.domain.LazyLoading;

/**
 * DAO fot LazyLoading Entity
 * @author Erika
 *
 */
public interface LazyLoadingRepository extends CrudRepository<LazyLoading, Long> {

}
