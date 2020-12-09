package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.cache.Cache;
import com.shilovich.hrbet.cache.CacheFactory;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RaceDao;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.RaceService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.testng.Assert.*;

@SuppressStaticInitializationFor({"com.shilovich.hrbet.dao.DaoFactory", "com.shilovich.hrbet.dao.Dao", "com.shilovich.hrbet.cache.CacheFactory"})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({DaoFactory.class, CacheFactory.class, RaceDao.class, Cache.class})
public class RaceServiceImplTest extends PowerMockTestCase {

    @Mock
    private DaoFactory daoFactory;
    @Mock
    private CacheFactory cacheFactory;
    @Mock
    private RaceDao raceDao;
    @Mock
    private Cache cache;

    private RaceService service;


    @BeforeMethod
    public void setUp() {
        mockStatic(DaoFactory.class);
        when(DaoFactory.getInstance()).thenReturn(daoFactory);
        when((RaceDao) daoFactory.getClass(Mockito.any())).thenReturn(raceDao);
        service = RaceServiceImpl.getInstance();
        mockStatic(CacheFactory.class);
        when(CacheFactory.getInstance()).thenReturn(cacheFactory);
        when((Cache) cacheFactory.getCache(Mockito.any())).thenReturn(cache);
    }

    @Test
    public void testShowAllActivePositive() {
        try {
            List<Race> expected = new ArrayList<>();
            when(raceDao.findActive(Mockito.anyInt(), Mockito.anyInt())).thenReturn(expected);
            List<Race> actual = service.showAllActive("2");
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testShowAllActiveNegative() {
        try {
            List<Race> expected = new ArrayList<>();
            expected.add(new Race());
            when(raceDao.findActive(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<>());
            List<Race> actual = service.showAllActive("2");
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testShowAllActiveException() throws DaoException, ServiceException {
        when(raceDao.findActive(Mockito.anyInt(), Mockito.anyInt())).thenThrow(DaoException.class);
        service.showAllActive("2");
        fail("No exception was thrown!");
    }

    @Test
    public void testShowActivePositive() {
        try {
            List<Race> expected = new ArrayList<>();
            when(raceDao.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(expected);
            List<Race> actual = service.showAll("2");
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testShowActiveNegative() {
        try {
            List<Race> expected = new ArrayList<>();
            expected.add(new Race());
            when(raceDao.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<>());
            List<Race> actual = service.showAll("2");
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testShowActiveException() throws DaoException, ServiceException {
        when(raceDao.findAll(Mockito.anyInt(), Mockito.anyInt())).thenThrow(DaoException.class);
        service.showAll("2");
        fail("No exception was thrown!");
    }

    @Test
    public void testPageNumberActivePositive() {
        try {
            int actual = 3;
            when(cache.containsKey(Mockito.anyString())).thenReturn(false);
            when(raceDao.countActual()).thenReturn(13L);
            doNothing().when(cache).addCache(Mockito.anyString(), Mockito.any());
            int expected = service.pageNumberActive();
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testPageNumberActiveNegative() {
        try {
            int actual = 2;
            when(cache.containsKey(Mockito.anyString())).thenReturn(false);
            when(raceDao.countActual()).thenReturn(13L);
            doNothing().when(cache).addCache(Mockito.anyString(), Mockito.any());
            int expected = service.pageNumberActive();
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testPageNumberActiveException() throws DaoException, ServiceException {
        when(cache.containsKey(Mockito.anyString())).thenReturn(false);
        when(raceDao.countActual()).thenThrow(DaoException.class);
        service.pageNumberActive();
        fail("No exception was thrown!");
    }

    @Test
    public void testPageNumberAllPositive() {
        try {
            int actual = 3;
            when(cache.containsKey(Mockito.anyString())).thenReturn(false);
            when(raceDao.countAll()).thenReturn(13L);
            doNothing().when(cache).addCache(Mockito.anyString(), Mockito.any());
            int expected = service.pageNumberAll();
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testPageNumberAllNegative() {
        try {
            int actual = 2;
            when(cache.containsKey(Mockito.anyString())).thenReturn(false);
            when(raceDao.countAll()).thenReturn(13L);
            doNothing().when(cache).addCache(Mockito.anyString(), Mockito.any());
            int expected = service.pageNumberAll();
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testPageNumberAllException() throws DaoException, ServiceException {
        when(cache.containsKey(Mockito.anyString())).thenReturn(false);
        when(raceDao.countAll()).thenThrow(DaoException.class);
        service.pageNumberAll();
        fail("No exception was thrown!");
    }

    @Test
    public void testAddRaceNegative() {
        try {
            when(raceDao.create(Mockito.any(Race.class))).thenReturn(Optional.empty());
            when(raceDao.addHorse(Mockito.anyLong(), Mockito.anySet())).thenReturn(false);
            boolean condition = service.addRace(new HashSet<>(), "Location", "2020-11-27T16-40");
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAddRacePositive() {
        try {
            when(raceDao.create(Mockito.any(Race.class))).thenReturn(Optional.of(new Race()));
            when(raceDao.addHorse(Mockito.any(), Mockito.anySet())).thenReturn(true);
            when(cache.containsKey(Mockito.anyString())).thenReturn(false);
            when(raceDao.countActual()).thenReturn(1L);
            when(raceDao.countAll()).thenReturn(1L);
            doNothing().when(cache).addCache(Mockito.anyString(), Mockito.any());
            boolean condition = service.addRace(new HashSet<>(), "Location", "2020-11-27T16:40");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAddRaceInvalidParameter() {
        try {
            boolean condition = service.addRace(new HashSet<>(), "Location", "2020-11-27T16-40");
            assertFalse(condition);
        } catch (ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddRaceException() throws DaoException, ServiceException {
        when(raceDao.create(Mockito.any(Race.class))).thenReturn(Optional.of(new Race()));
        when(raceDao.addHorse(Mockito.any(), Mockito.anySet())).thenThrow(DaoException.class);
        service.addRace(new HashSet<>(), "Location", "2020-11-27T16:40");
        fail("No exception was thrown!");
    }


    @Test
    public void testDeleteInvalidParameter() {
        try {
            boolean condition = service.delete("1l");
            assertFalse(condition);
        } catch (ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindInfoPositive() {
        try {
            Race expected = new Race(1L, "Location", LocalDateTime.parse("2020-11-27T16:40"));
            when(raceDao.read(Mockito.anyLong())).thenReturn(Optional.of(expected));
            Race actual = service.findInfo("1");
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindInfoNegative() {
        try {
            Race expected = new Race(1L, "Location", LocalDateTime.parse("2020-11-27T16:40"));
            when(raceDao.read(Mockito.anyLong())).thenReturn(Optional.empty());
            Race actual = service.findInfo("1");
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindInfoException() throws DaoException, ServiceException {
        when(raceDao.read(Mockito.anyLong())).thenThrow(DaoException.class);
        service.findInfo("1");
        fail("No exception was thrown!");
    }
}