package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RatioDao;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.HorseService;
import com.shilovich.hrbet.service.RatioService;
import com.shilovich.hrbet.service.ServiceFactory;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.util.*;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@SuppressStaticInitializationFor({"com.shilovich.hrbet.dao.DaoFactory", "com.shilovich.hrbet.dao.Dao", "com.shilovich.hrbet.service.ServiceFactory"})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({DaoFactory.class, HorseService.class, ServiceFactory.class})
public class RatioServiceImplTest extends PowerMockTestCase {

    @Mock
    private ServiceFactory serviceFactory;
    @Mock
    private DaoFactory daoFactory;
    @Mock
    private RatioDao ratioDao;
    @Mock
    private HorseService horseService;

    private RatioService service;

    @BeforeMethod
    public void setUp() {
        mockStatic(DaoFactory.class);
        when(DaoFactory.getInstance()).thenReturn(daoFactory);
        when((RatioDao) daoFactory.getClass(Mockito.any())).thenReturn(ratioDao);
        service = RatioServiceImpl.getInstance();
        mockStatic(ServiceFactory.class);
        when(ServiceFactory.getInstance()).thenReturn(serviceFactory);
        when((HorseService) serviceFactory.getClass(Mockito.any())).thenReturn(horseService);
    }

    @DataProvider(name = "validMap")
    public Object[][] createValidMap() {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("12|8|win", "2.35");
        parameterMap.put("7|9|show", "5.89");
        return new Object[][]{{parameterMap}};
    }

    @DataProvider(name = "invalidMap")
    public Object[][] createInvalidMap() {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("12|8|win", "2.35");
        parameterMap.put("7|9|shows", "5.89");
        return new Object[][]{{parameterMap}};
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

    @Test(dataProvider = "validMap")
    public void testAddRatiosValidMap(Map<String, String> parameterMap) {
        try {
            when(ratioDao.setRatios(Mockito.anySet())).thenReturn(true);
            Set<Horse> horses = new HashSet<>();
            horses.add(new Horse(1L, "Horse", 5, "Jockey"));
            when(horseService.showByRace(Mockito.anyString())).thenReturn(horses);
            boolean condition = service.addRatios(parameterMap);
            assertTrue(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "validMap")
    public void testAddRatiosNegative(Map<String, String> parameterMap) {
        try {
            when(ratioDao.setRatios(Mockito.anySet())).thenReturn(false);
            Set<Horse> horses = new HashSet<>();
            horses.add(new Horse(1L, "Horse", 5, "Jockey"));
            when(horseService.showByRace(Mockito.anyString())).thenReturn(horses);
            boolean condition = service.addRatios(parameterMap);
            assertFalse(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "invalidMap")
    public void testAddRatiosInvalidMap(Map<String, String> parameterMap) {
        try {
            when(ratioDao.setRatios(Mockito.anySet())).thenReturn(true);
            Set<Horse> horses = new HashSet<>();
            horses.add(new Horse(1L, "Horse", 5, "Jockey"));
            when(horseService.showByRace(Mockito.anyString())).thenReturn(horses);
            boolean condition = service.addRatios(parameterMap);
            assertFalse(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "validMap")
    public void testAddRatiosInvalidRatioCount(Map<String, String> parameterMap) {
        try {
            when(ratioDao.setRatios(Mockito.anySet())).thenReturn(true);
            when(horseService.showByRace(Mockito.anyString())).thenReturn(new HashSet<>());
            boolean condition = service.addRatios(parameterMap);
            assertFalse(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class, dataProvider = "validMap")
    public void testAddRatiosException(Map<String, String> parameterMap) throws ServiceException, DaoException {
        when(ratioDao.setRatios(Mockito.anySet())).thenThrow(DaoException.class);
        Set<Horse> horses = new HashSet<>();
        horses.add(new Horse(1L, "Horse", 5, "Jockey"));
        when(horseService.showByRace(Mockito.anyString())).thenReturn(horses);
        service.addRatios(parameterMap);
        fail("No exception was thrown!");
    }
}