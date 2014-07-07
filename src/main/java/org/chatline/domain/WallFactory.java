package org.chatline.domain;

import javax.inject.Inject;

import org.chatline.service.repository.OwnerRepository;

public class WallFactory {
	
    private TimeLineFactory timeLineFactory;

	@Inject
	private OwnerRepository ownerRepository;

	public WallFactory(TimeLineFactory timeLineFactory) {
		super();
		this.timeLineFactory = timeLineFactory;
	}

	public Wall createWall(String wallOwner) {
		Owner owner = ownerRepository.findOne(wallOwner);
		if (owner == null) {
			owner = new Owner(wallOwner);
			ownerRepository.save(owner);
			owner = ownerRepository.findOne(wallOwner);
		}
		return new Wall(timeLineFactory, ownerRepository, owner);
	}
}
