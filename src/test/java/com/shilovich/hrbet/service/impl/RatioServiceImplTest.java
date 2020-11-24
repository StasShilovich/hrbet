package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RatioDao;
import com.shilovich.hrbet.dao.impl.RatioDaoImpl;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.RatioService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.PowerMockUtils;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import javax.annotation.security.RunAs;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test(groups = {"static",""})
public class RatioServiceImplTest {


    private DaoFactory daoFactory;

    @Mock
    private RatioDao ratioDao;


    @BeforeMethod
    public void setUp() {

        Mockito.when((RatioDao) DaoFactory.getInstance().getClass(RatioDao.class)).thenReturn(ratioDao);
    }

    @Test
    public void testFindRatio() {
        try {
            List<Ratio> expected = new ArrayList<>();
            Mockito.when(ratioDao.findRatio(1L)).thenReturn(expected);
            RatioService service = RatioServiceImpl.getInstance();
            List<Ratio> actual = service.findRatio("1");
            assertEquals(actual, expected);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }

    }
}
