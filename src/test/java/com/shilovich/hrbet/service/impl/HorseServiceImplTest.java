package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.HorseDao;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.HorseService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.*;
import static org.testng.Assert.fail;

@SuppressStaticInitializationFor({"com.shilovich.hrbet.dao.DaoFactory", "com.shilovich.hrbet.dao.Dao"})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({DaoFactory.class})
public class HorseServiceImplTest extends PowerMockTestCase {

    @Mock
    private DaoFactory daoFactory;
    @Mock
    private HorseDao horseDao;

    private HorseService service;

    @BeforeMethod
    public void setUp() {
        mockStatic(DaoFactory.class);
        when(DaoFactory.getInstance()).thenReturn(daoFactory);
        when((HorseDao) daoFactory.getClass(Mockito.any())).thenReturn(horseDao);
        service = HorseServiceImpl.getInstance();
    }

    @DataProvider(name = "horseSet")
    public Object[][] createHorse() {
        Set<Horse> horseSet = new HashSet<>();
        horseSet.add(new Horse(1L, "Name", 6, "Jockey"));
        return new Object[][]{{horseSet}};
    }

    @Test(dataProvider = "horseSet")
    public void testShowByRacePositive(Set<Horse> expected) {
        try {
            when(horseDao.findByRace(Mockito.anyLong())).thenReturn(expected);
            Set<Horse> actual = service.showByRace("12");
            assertEquals(actual, expected);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "horseSet")
    public void testShowByRaceNegative(Set<Horse> expected) {
        try {
            when(horseDao.findByRace(Mockito.anyLong())).thenReturn(expected);
            Set<Horse> actual = service.showByRace("12w");
            assertNotEquals(actual, expected);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testShowByRaceException() throws DaoException, ServiceException {
        when(horseDao.findByRace(Mockito.anyLong())).thenThrow(DaoException.class);
        service.showByRace("12");
        fail("No exception was thrown!");
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindAllException() throws DaoException, ServiceException {
        when(horseDao.findAll()).thenThrow(DaoException.class);
        service.findAll();
        fail("No exception was thrown!");
    }
}