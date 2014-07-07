package org.chatline.service.repository;

import java.util.List;

import org.chatline.domain.Owner;
import org.chatline.domain.PostEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<PostEvent, String> {
	
	List<PostEvent> findByOwner(Owner owner);

	List<PostEvent> findByOwnerOrderByDateTimeDesc(Owner owner);
	List<PostEvent> findByOwnerOrderByDateTimeAsc(Owner owner);
}
