package com.increff.employee.service.util;

import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.StringUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringUtilTest extends AbstractUnitTest {
    StringUtil stringUtil = new StringUtil();
    @Test
    public void testIsEmpty() throws ApiException {
        assertTrue(stringUtil.isEmpty(""));
    }
    @Test
    public void testToLowerCase() throws ApiException{
        assertEquals(stringUtil.toLowerCase(" Nick "),"nick");
    }
}
