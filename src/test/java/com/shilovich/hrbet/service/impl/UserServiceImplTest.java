package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RatioDao;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.HorseService;
import com.shilovich.hrbet.service.RatioService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.UserService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({DaoFactory.class})
public class UserServiceImplTest extends PowerMockTestCase {

    @Mock
    private DaoFactory daoFactory;
    @Mock
    private UserDao userDao;

    private UserService service;

    @BeforeMethod
    public void setUp() {
        mockStatic(DaoFactory.class);
        when(DaoFactory.getInstance()).thenReturn(daoFactory);
        when((UserDao) daoFactory.getClass(RatioDao.class)).thenReturn(userDao);
        service = UserServiceImpl.getInstance();
    }

    @Test
    public void testAuthorizationPositive() {
        try {
            User auth = new User("John", "Jonas", "Password1", "test@gmail.com");
            User expected = new User();
            expected.setPassword("$2y$12$toYae.S4dAAODIAgM1IfW.8bI9x4Olsq87DdLGQOrBkHXBp0IDV76");
            when(userDao.authorization(auth)).thenReturn(expected);
            User actual = service.authorization(auth);
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAuthorizationNegative() {

    }

    @Test
    public void testAuthorizationException() {

    }

}
