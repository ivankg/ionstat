package org.strmln.ionstat.dao;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.dao.model.TestCase;
import org.strmln.ionstat.dao.model.TestSuite;
import org.strmln.ionstat.model.Device;

import junit.framework.Assert;

@TestSuite("device")
public class DeviceDaoTests extends AbstractDaoTest {

	@Autowired
	private DeviceDao _deviceDao;

	private DeviceDao getDeviceDao() {
		return _deviceDao;
	}

	@Test
	@TestCase("findDevicesByRoomId")
	public void testFindDevicesByRoomId() {
		List<Device> devices = getDeviceDao().findDevicesByRoomId(Long.valueOf(1));

		Assert.assertEquals(2, devices.size());

		Iterator<Device> iterator = devices.iterator();
		while (iterator.hasNext()) {
			Long deviceId = iterator.next().getDeviceId();

			if (deviceId.equals(Long.valueOf(1)) || deviceId.equals(Long.valueOf(2))) {
				iterator.remove();
			}
		}

		Assert.assertTrue(devices.isEmpty());
	}
}
