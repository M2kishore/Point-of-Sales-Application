package com.increff.employee.service.util;

import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.util.DateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateUtilTest extends AbstractUnitTest {
    DateUtil dateUtil = new DateUtil();
    @Test
    public void testMillisecondToData(){
        long millisecond = 100000;
        Date actualDate = new Date(millisecond);
        Date functionDate = dateUtil.MillisecondToDate(millisecond);
        assertEquals(functionDate,actualDate);
    }
}
