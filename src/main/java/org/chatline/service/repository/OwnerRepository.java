package org.chatline.service.repository;

import java.util.Optional;

import org.chatline.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, String> {
	Optional<Owner> findByName(String name);
}
