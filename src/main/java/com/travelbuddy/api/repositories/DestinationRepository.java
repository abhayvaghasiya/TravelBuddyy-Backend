package com.travelbuddy.api.repositories;

import com.travelbuddy.api.models.AgeGroup;
import com.travelbuddy.api.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    @Query("SELECT d FROM Destination d JOIN d.suitableAgeGroups ag WHERE ag = :ageGroup AND d.typicalCost <= :budget")
    List<Destination> findByAgeGroupAndMaxBudget(@Param("ageGroup") AgeGroup ageGroup, @Param("budget") BigDecimal budget);
    
    @Query("SELECT d FROM Destination d JOIN d.suitableAgeGroups ag WHERE ag = :ageGroup")
    List<Destination> findByAgeGroup(@Param("ageGroup") AgeGroup ageGroup);
    
    List<Destination> findByTypicalCostLessThanEqual(BigDecimal budget);
} 