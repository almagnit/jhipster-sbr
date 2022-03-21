import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './vts.reducer';
import { IVTS } from 'app/shared/model/vts.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VTS = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const vTSList = useAppSelector(state => state.vTS.entities);
  const loading = useAppSelector(state => state.vTS.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="vts-heading" data-cy="VTSHeading">
        <Translate contentKey="sbrConverterApp.vTS.home.title">VTS</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="sbrConverterApp.vTS.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sbrConverterApp.vTS.home.createLabel">Create new VTS</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vTSList && vTSList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="sbrConverterApp.vTS.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.vTS.verkehrstage">Verkehrstage</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.vTS.beschreibung">Beschreibung</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.vTS.versionsnummer">Versionsnummer</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.vTS.mandant">Mandant</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.vTS.feiertage">Feiertage</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.vTS.gueltigkeit">Gueltigkeit</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vTSList.map((vTS, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${vTS.id}`} color="link" size="sm">
                      {vTS.id}
                    </Button>
                  </td>
                  <td>{vTS.verkehrstage}</td>
                  <td>{vTS.beschreibung}</td>
                  <td>{vTS.versionsnummer}</td>
                  <td>{vTS.mandant}</td>
                  <td>{vTS.feiertage}</td>
                  <td>{vTS.gueltigkeit}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${vTS.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vTS.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vTS.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="sbrConverterApp.vTS.home.notFound">No VTS found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default VTS;
