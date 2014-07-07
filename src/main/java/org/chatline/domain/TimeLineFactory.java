package org.chatline.domain;

import javax.inject.Inject;

import org.chatline.service.repository.OwnerRepository;
import org.chatline.service.repository.PostingRepository;

public class TimeLineFactory {

	@Inject
	private PostingRepository postingRepository;
	@Inject
	private OwnerRepository ownerRepository;

	public TimeLineFactory() {
		super();
	}

//	@Bean(autowire = Autowire.BY_TYPE)
//	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public TimeLine timeLine(String user) {
		Owner owner = ownerRepository.findOne(user);
		if (owner == null) {
			owner = new Owner(user);
			ownerRepository.save(owner);
			owner = ownerRepository.findOne(user);
		}
		return new TimeLine(postingRepository, owner);
	}

	public TimeLine createTimeLine(String user) {
		TimeLine timeLine = timeLine(user);
		System.out.println("For user = " + user + ", timeline = " + timeLine
				+ ", with user = " + timeLine.getUser());
		return timeLine;
	}

}