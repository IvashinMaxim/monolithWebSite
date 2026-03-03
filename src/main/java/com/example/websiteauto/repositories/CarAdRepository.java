package com.example.websiteauto.repositories;

import com.example.websiteauto.entity.CarAd;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CarAdRepository extends JpaRepository<CarAd, Long>, JpaSpecificationExecutor<CarAd>, CarAdRepositoryCustom {

    List<CarAd> findByAuthorId(Long authorId);

    @Query("SELECT DISTINCT ca FROM CarAd ca " +
           "LEFT JOIN FETCH ca.car " +
           "LEFT JOIN FETCH ca.author " +
           "LEFT join fetch ca.images " +
           "WHERE ca.id IN :ids")
    List<CarAd> findAllByIdsWithRelations(@Param("ids") List<Long> ids);

    @EntityGraph(attributePaths = {"car", "author"})
    @Query("SELECT ca FROM CarAd ca " +
           "LEFT JOIN FETCH ca.car " +
           "WHERE ca.id IN :ids")
    List<CarAd> findAllByIdsForList(@Param("ids") List<Long> ids);
}
