package org.strmln.ionstat.task.handler;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.strmln.ionstat.task.handler.groovy.annotations.TestCase;
import org.strmln.ionstat.task.handler.groovy.annotations.TestSuite;
import org.strmln.ionstat.task.handler.groovy.annotations.TestUser;
import org.strmln.ionstat.task.handler.model.command.AddNewDeviceCommand;

@TestSuite("device")
public class AddDeviceTaskHandlerTest extends AbstractTaskHandlerTest {

	@TestUser(policy = "POLICY_ADD_DEVICE")
	@TestCase("addNewDevice")
	@Test
	public void testAddDeviceProcessTask() {
		TaskHandler taskHandler = getTaskManager().getTaskHandler("addNewDevice");

		AddNewDeviceCommand command = getModelObject("addNewDeviceCommand", AddNewDeviceCommand.class);

		taskHandler.processTask(command, null);

		Mockito.verify(getDeviceService()).addNewDevice(Matchers.eq(Long.valueOf(1)), Matchers.eq("name1"),
				Matchers.eq("manufacturer1"), Matchers.eq("model1"), Matchers.eq("serialNumber1"),
				Matchers.eq("deviceUsage1"));
	}

}
