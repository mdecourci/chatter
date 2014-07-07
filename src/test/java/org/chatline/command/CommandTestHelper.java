/**
 * 
 */
package org.chatline.command;

import org.chatline.domain.Wall;
import org.chatline.domain.WallFactory;
import org.chatline.service.TimeLineService;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Commands test helper
 * @author michaeldecourci
 *
 */
public abstract class CommandTestHelper {

	@Mock
	protected TimeLineService mockTimeLineService;
	@Mock
	protected WallFactory mockWallFactory;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

}
