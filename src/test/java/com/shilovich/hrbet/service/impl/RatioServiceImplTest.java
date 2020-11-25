package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RatioDao;
import com.shilovich.hrbet.dao.pool.ConnectionManager;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.HorseService;
import com.shilovich.hrbet.service.RatioService;
import com.shilovich.hrbet.service.ServiceFactory;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({DaoFactory.class, HorseService.class, ServiceFactory.class})
public class RatioServiceImplTest extends PowerMockTestCase {

    //    @Mock
//    private ServiceFactory serviceFactory;
    @Mock
    private DaoFactory daoFactory;
    @Mock
    private RatioDao ratioDao;
//    @Mock
//    private HorseService horseService;

    private RatioService service;

    @BeforeMethod
    public void setUp() {
        mockStatic(DaoFactory.class);
        when(DaoFactory.getInstance()).thenReturn(daoFactory);
        when((RatioDao) daoFactory.getClass(RatioDao.class)).thenReturn(ratioDao);
        service = RatioServiceImpl.getInstance();
//        mockStatic(ServiceFactory.class);
//        when(ServiceFactory.getInstance()).thenReturn(serviceFactory);
//        when((HorseService) ServiceFactory.getInstance().getClass(HorseService.class)).thenReturn(horseService);
    }


    @Test
    public void testFindRatioPositive() {
        try {
            List<Ratio> expected = new ArrayList<>();
            when(ratioDao.findRatio(Mockito.anyLong())).thenReturn(expected);
            List<Ratio> actual = service.findRatio("1");
            assertEquals(actual, expected);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindRatioNegative() {
        try {
            when(ratioDao.findRatio(Mockito.anyLong())).thenReturn(null);
            List<Ratio> condition = service.findRatio("1");
            assertNull(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindRatioException() throws ServiceException, DaoException {
        when(ratioDao.findRatio(Mockito.anyLong())).thenThrow(DaoException.class);
        service.findRatio("1");
        fail("No exception was thrown!");
    }

    @Test
    public void testAddRatiosPositive() {
        try {
            when(ratioDao.setRatios(Mockito.anySet())).thenReturn(true);
            boolean condition = service.addRatios(new HashMap<>());
            assertTrue(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    // TODO: 25.11.2020 addRatios() can create some test
    @Test
    public void testAddRatiosNegative() {
        try {
            when(ratioDao.setRatios(Mockito.anySet())).thenReturn(false);
            boolean condition = service.addRatios(new HashMap<>());
            assertFalse(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddRatiosException() throws ServiceException, DaoException {
        when(ratioDao.setRatios(Mockito.anySet())).thenThrow(DaoException.class);
        service.addRatios(new HashMap<>());
        fail("No exception was thrown!");
    }
}