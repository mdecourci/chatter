/**
 * 
 */
package org.chatline.command;

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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

}
