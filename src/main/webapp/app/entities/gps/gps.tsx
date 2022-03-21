import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './gps.reducer';
import { IGPS } from 'app/shared/model/gps.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const GPS = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const gPSList = useAppSelector(state => state.gPS.entities);
  const loading = useAppSelector(state => state.gPS.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="gps-heading" data-cy="GPSHeading">
        <Translate contentKey="sbrConverterApp.gPS.home.title">GPS</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="sbrConverterApp.gPS.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sbrConverterApp.gPS.home.createLabel">Create new GPS</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {gPSList && gPSList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="sbrConverterApp.gPS.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.gPS.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.gPS.gpsCode">Gps Code</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.gPS.inneresFenster">Inneres Fenster</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.gPS.auBeresFenster">Au Beres Fenster</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gPSList.map((gPS, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${gPS.id}`} color="link" size="sm">
                      {gPS.id}
                    </Button>
                  </td>
                  <td>{gPS.name}</td>
                  <td>{gPS.gpsCode}</td>
                  <td>{gPS.inneresFenster}</td>
                  <td>{gPS.auBeresFenster}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${gPS.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${gPS.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${gPS.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="sbrConverterApp.gPS.home.notFound">No GPS found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default GPS;
