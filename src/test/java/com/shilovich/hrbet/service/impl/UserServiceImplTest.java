package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.cache.Cache;
import com.shilovich.hrbet.cache.CacheFactory;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.UserService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.testng.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

@SuppressStaticInitializationFor({"com.shilovich.hrbet.dao.DaoFactory", "com.shilovich.hrbet.dao.Dao", "com.shilovich.hrbet.cache.CacheFactory"})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({DaoFactory.class, CacheFactory.class, UserDao.class, Cache.class})
public class UserServiceImplTest extends PowerMockTestCase {

    @Mock
    private DaoFactory daoFactory;
    @Mock
    private CacheFactory cacheFactory;
    @Mock
    private UserDao userDao;
    @Mock
    private Cache cache;

    private UserService service;
    private User auth;
    private User hashUser;

    @BeforeMethod
    public void setUp() {
        mockStatic(DaoFactory.class);
        when(DaoFactory.getInstance()).thenReturn(daoFactory);
        when((UserDao) daoFactory.getClass(Mockito.any())).thenReturn(userDao);
        service = UserServiceImpl.getInstance();
        mockStatic(CacheFactory.class);
        when(CacheFactory.getInstance()).thenReturn(cacheFactory);
        when((Cache) cacheFactory.getCache(Mockito.any())).thenReturn(cache);
        auth = new User("John", "Jonas", "Password1", "test@gmail.com");
        hashUser = new User("John", "Jonas",
                "$2y$12$toYae.S4dAAODIAgM1IfW.8bI9x4Olsq87DdLGQOrBkHXBp0IDV76", "test@gmail.com");
    }

    @Test
    public void testAuthorizationPositive() {
        try {
            when(userDao.authorization(auth)).thenReturn(hashUser);
            when(cache.isEmpty()).thenReturn(false);
            when(cache.getCache(Mockito.any())).thenReturn(new ArrayList<>());
            User actual = service.authorization(auth);
            assertEquals(actual, hashUser);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAuthorizationNegative() {
        try {
            when(userDao.authorization(auth)).thenReturn(null);
            User condition = service.authorization(auth);
            assertNull(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAuthorizationInvalidPassword() {
        try {
            User auth = new User("John2", "Jonas2", "Password", "test@gmail.com");
            when(userDao.authorization(auth)).thenReturn(hashUser);
            User condition = service.authorization(auth);
            assertNull(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAuthorizationInvalidEmail() {
        try {
            User auth = new User("John2", "Jonas2", "Password1", "test.gm");
            when(userDao.authorization(auth)).thenReturn(hashUser);
            User condition = service.authorization(auth);
            assertNull(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }


    @Test(expectedExceptions = ServiceException.class)
    public void testAuthorizationException() throws DaoException, ServiceException {
        when(userDao.authorization(auth)).thenThrow(DaoException.class);
        service.authorization(auth);
        fail("No exception was thrown!");
    }

    @Test
    public void testRegistrationPositive() {
        try {
            when(userDao.read("test@gmail.com")).thenReturn(Optional.empty());
            when(cache.isEmpty()).thenReturn(true);
            when(userDao.create(hashUser)).thenReturn(Optional.of(hashUser));
            Map<String, String> condition = service.registration(auth);
            assertNull(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRegistrationUserExistInDatabase() {
        try {
            when(userDao.read("test@gmail.com")).thenReturn(Optional.of(new User()));
            Map<String, String> actual = service.registration(auth);
            Map<String, String> expected = Map.of("name", "John", "surname", "Jonas",
                    "password", "Password1", "email", "");
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRegistrationException() throws DaoException, ServiceException {
        when(userDao.read("test@gmail.com")).thenThrow(DaoException.class);
        service.registration(auth);
        fail("No exception was thrown!");
    }

    @Test
    public void testFindAllPositive() {
        try {
            List<User> expected = new ArrayList<>();
            when(userDao.findAll(2, 0)).thenReturn(expected);
            List<User> actual = service.findAll("0");
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllInvalidPage() {
        try {
            List<User> expected = new ArrayList<>();
            when(userDao.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(expected);
            List<User> actual = service.findAll("adawd");
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindAllException() throws DaoException, ServiceException {
        when(userDao.findAll(Mockito.anyInt(), Mockito.anyInt())).thenThrow(DaoException.class);
        service.findAll("0");
        fail("No exception was thrown!");
    }

    @Test
    public void testUsersPageCountPositive() {
        try {
            when(cache.isEmpty()).thenReturn(true);
            when(userDao.count()).thenReturn(5L);
            long expected = 3L;
            long actual = service.getUsersPagesCount();
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUsersPageCountNegative() {
        try {
            when(cache.isEmpty()).thenReturn(true);
            when(userDao.count()).thenReturn(5L);
            long expected = 2L;
            long actual = service.getUsersPagesCount();
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUsersPageCountException() throws DaoException, ServiceException {
        when(cache.isEmpty()).thenReturn(true);
        when(userDao.count()).thenThrow(DaoException.class);
        service.getUsersPagesCount();
        fail("No exception was thrown!");
    }

    @Test
    public void testUpdateCashPositive() {
        try {
            when(userDao.updateCash(Mockito.any(), Mockito.anyLong())).thenReturn(true);
            boolean condition = service.updateCash(new BigDecimal("1"), 1L);
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateCashNegative() {
        try {
            when(userDao.updateCash(Mockito.any(), Mockito.anyLong())).thenReturn(false);
            boolean condition = service.updateCash(new BigDecimal("1"), 1L);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateCashException() throws DaoException, ServiceException {
        when(userDao.updateCash(Mockito.any(), Mockito.anyLong())).thenThrow(DaoException.class);
        service.updateCash(new BigDecimal("1"), 1L);
        fail("No exception was thrown!");
    }

    @Test
    public void testBanPositive() {
        try {
            when(userDao.delete(Mockito.anyString())).thenReturn(true);
            boolean condition = service.ban("11");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBanNegative() {
        try {
            when(userDao.delete(Mockito.anyString())).thenReturn(false);
            boolean condition = service.ban("11");
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testBanException() throws DaoException, ServiceException {
        when(userDao.delete(Mockito.anyString())).thenThrow(DaoException.class);
        service.ban("11");
        fail("No exception was thrown!");
    }
}