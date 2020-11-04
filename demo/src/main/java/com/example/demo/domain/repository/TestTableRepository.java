package com.example.demo.domain.repository;

import com.example.demo.domain.TestTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TestTableRepository extends JpaRepository<TestTable, UUID> {
    @Query("select tt from TestTable tt where tt.name = :name")
    List<TestTable> getByName(final String name);
}
