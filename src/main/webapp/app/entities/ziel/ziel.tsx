import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './ziel.reducer';
import { IZiel } from 'app/shared/model/ziel.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Ziel = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const zielList = useAppSelector(state => state.ziel.entities);
  const loading = useAppSelector(state => state.ziel.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="ziel-heading" data-cy="ZielHeading">
        <Translate contentKey="sbrConverterApp.ziel.home.title">Ziels</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="sbrConverterApp.ziel.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sbrConverterApp.ziel.home.createLabel">Create new Ziel</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {zielList && zielList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="sbrConverterApp.ziel.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ziel.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ziel.front">Front</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ziel.seite1">Seite 1</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ziel.seite2">Seite 2</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ziel.innen">Innen</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ziel.tft">Tft</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ziel.terminal">Terminal</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ziel.language">Language</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {zielList.map((ziel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${ziel.id}`} color="link" size="sm">
                      {ziel.id}
                    </Button>
                  </td>
                  <td>{ziel.code}</td>
                  <td>{ziel.front}</td>
                  <td>{ziel.seite1}</td>
                  <td>{ziel.seite2}</td>
                  <td>{ziel.innen}</td>
                  <td>{ziel.tft}</td>
                  <td>{ziel.terminal}</td>
                  <td>{ziel.language}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${ziel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ziel.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ziel.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="sbrConverterApp.ziel.home.notFound">No Ziels found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Ziel;
