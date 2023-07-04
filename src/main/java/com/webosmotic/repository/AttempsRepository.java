package com.webosmotic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webosmotic.modal.Attemps;

@Repository
public interface AttempsRepository extends JpaRepository<Attemps, Integer> {

	Optional<Attemps> findAttempsByUsername(String username);

}
