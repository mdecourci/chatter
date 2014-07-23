package org.chatline.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.chatline.builder.ViewBuilder;
import org.chatline.service.repository.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * API for the wall service
 * @author michaeldecourci
 */
public class Wall {
	
    private static final Logger LOG = LoggerFactory.getLogger(Wall.class);

    private TimeLineFactory timeLineFactory;
    private OwnerRepository ownerRepository;
    private Owner owner;

	public Wall(TimeLineFactory timeLineFactory, OwnerRepository ownerRepository, Owner owner) {
		this.timeLineFactory = timeLineFactory;
		this.ownerRepository = ownerRepository;
		this.owner = owner;
	}

	public void follow(String followingUser) {
		LOG.debug("follow() : followingUser={}", followingUser);
		Assert.notNull(owner, "owner is mandatory");
		Assert.hasLength(followingUser, "followingUser is mandatory");
		
		this.owner = ownerRepository.findOne(owner.getName());
		if (this.owner.getFollowers() == null || this.owner.getFollowers().isEmpty()) {
			this.owner.setFollowers(new HashSet<Owner>());
		}
		// check follower persisted
		Owner followingOwner = ownerRepository.findOne(followingUser);
		if (followingOwner == null) {
			followingOwner = new Owner(followingUser);
			ownerRepository.save(followingOwner);
			followingOwner = ownerRepository.findOne(followingUser);
		}
		this.owner.getFollowers().add(followingOwner);
		ownerRepository.save(this.owner);
		this.owner = ownerRepository.findOne(owner.getName());
	}

	public String getPosts() {
		LOG.debug("getPosts()");
		LocalDateTime now = LocalDateTime.now();  // get the time of the request
		this.owner = ownerRepository.findOne(owner.getName());

		// aggregate the list of postings
		TimeLine timeLine = timeLineFactory.createTimeLine(this.owner.getName());
		List<PostEvent> aagregatePostEvents = new ArrayList<>(timeLine.getUserPostings());
		for (Iterator<Owner> iterator =  this.owner.getFollowers().iterator(); iterator.hasNext();) {
			Owner followedOwner = iterator.next();
			timeLine = timeLineFactory.createTimeLine(followedOwner.getName());
			aagregatePostEvents.addAll(timeLine.getUserPostings());
		}
		return ViewBuilder.buildWall(now, aagregatePostEvents);
	}

}
