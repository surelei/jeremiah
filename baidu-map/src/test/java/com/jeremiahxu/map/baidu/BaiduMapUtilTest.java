package com.jeremiahxu.map.baidu;

import junit.framework.Assert;

import org.junit.Test;

public class BaiduMapUtilTest {

	@Test
	public void testGetCoords() {
		String address = "上海市四平路773号";
		Coords coords = BaiduMapUtil.getCoordsByAddress(address);
		Assert.assertEquals("经度不正确！", 121.50446, coords.getLng());
		Assert.assertEquals("维度不正确！", 31.278334, coords.getLat());
	}

}
