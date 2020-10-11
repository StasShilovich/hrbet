package com.shilovich.hrbet.dao;


import com.shilovich.hrbet.dao.exception.DaoException;

import java.io.Serializable;

public interface DaoCRUD<A, T extends Serializable> {
    A create(A a) throws DaoException;

    A read(T id) throws DaoException;

    A update(A a) throws DaoException;

    boolean delete(A a) throws DaoException;
}
