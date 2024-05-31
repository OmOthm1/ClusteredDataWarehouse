package com.progresssoft.clustereddatawarehouse.repository;


import com.progresssoft.clustereddatawarehouse.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {
    /**
     * Checks if a deal with the specified unique ID exists.
     *
     * @param dealUniqueId the unique ID of the deal
     * @return true if a deal with the specified unique ID exists, false otherwise
     */
    boolean existsByDealUniqueId(String dealUniqueId);
}
