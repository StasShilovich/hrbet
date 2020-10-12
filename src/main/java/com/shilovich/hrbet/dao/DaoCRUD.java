package com.shilovich.hrbet.dao;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.io.Serializable;

public interface DaoCRUD<A, T extends Serializable> {
    A create(A a) throws DaoException, UnsupportedOperationException;

    A read(T id) throws DaoException, UnsupportedOperationException;

    A update(A a) throws DaoException, UnsupportedOperationException;

    boolean delete(A a) throws DaoException, UnsupportedOperationException;
}
