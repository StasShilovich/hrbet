package com.shilovich.hrbet.dao;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.io.Serializable;
import java.util.Optional;

public interface DaoCRUD<A, T extends Serializable> {
    Optional<A> create(A a) throws DaoException, UnsupportedOperationException;

    Optional<A> read(T id) throws DaoException, UnsupportedOperationException;

    Optional<A> update(A a) throws DaoException, UnsupportedOperationException;

    boolean delete(A a) throws DaoException, UnsupportedOperationException;
}
