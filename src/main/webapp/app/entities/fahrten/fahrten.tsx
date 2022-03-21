import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './fahrten.reducer';
import { IFahrten } from 'app/shared/model/fahrten.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Fahrten = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const fahrtenList = useAppSelector(state => state.fahrten.entities);
  const loading = useAppSelector(state => state.fahrten.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="fahrten-heading" data-cy="FahrtenHeading">
        <Translate contentKey="sbrConverterApp.fahrten.home.title">Fahrtens</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="sbrConverterApp.fahrten.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sbrConverterApp.fahrten.home.createLabel">Create new Fahrten</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {fahrtenList && fahrtenList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="sbrConverterApp.fahrten.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.fahrten.titel">Titel</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.fahrten.zugnummer">Zugnummer</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.fahrten.znrBeschreibung">Znr Beschreibung</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.fahrten.variante">Variante</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.fahrten.tagesart">Tagesart</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.fahrten.umlauf">Umlauf</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.fahrten.umlaufindex">Umlaufindex</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fahrtenList.map((fahrten, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${fahrten.id}`} color="link" size="sm">
                      {fahrten.id}
                    </Button>
                  </td>
                  <td>{fahrten.titel}</td>
                  <td>{fahrten.zugnummer}</td>
                  <td>{fahrten.znrBeschreibung}</td>
                  <td>{fahrten.variante}</td>
                  <td>{fahrten.tagesart}</td>
                  <td>{fahrten.umlauf}</td>
                  <td>{fahrten.umlaufindex}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${fahrten.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${fahrten.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${fahrten.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="sbrConverterApp.fahrten.home.notFound">No Fahrtens found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Fahrten;
