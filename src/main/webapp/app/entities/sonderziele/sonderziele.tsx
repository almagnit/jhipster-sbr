import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './sonderziele.reducer';
import { ISonderziele } from 'app/shared/model/sonderziele.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Sonderziele = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const sonderzieleList = useAppSelector(state => state.sonderziele.entities);
  const loading = useAppSelector(state => state.sonderziele.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="sonderziele-heading" data-cy="SonderzieleHeading">
        <Translate contentKey="sbrConverterApp.sonderziele.home.title">Sonderzieles</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="sbrConverterApp.sonderziele.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sbrConverterApp.sonderziele.home.createLabel">Create new Sonderziele</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sonderzieleList && sonderzieleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="sbrConverterApp.sonderziele.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.sonderziele.zugnummer">Zugnummer</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.sonderziele.front">Front</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.sonderziele.seite1">Seite 1</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.sonderziele.seite2">Seite 2</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.sonderziele.innen">Innen</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.sonderziele.tft">Tft</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.sonderziele.terminal">Terminal</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sonderzieleList.map((sonderziele, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${sonderziele.id}`} color="link" size="sm">
                      {sonderziele.id}
                    </Button>
                  </td>
                  <td>{sonderziele.zugnummer}</td>
                  <td>{sonderziele.front}</td>
                  <td>{sonderziele.seite1}</td>
                  <td>{sonderziele.seite2}</td>
                  <td>{sonderziele.innen}</td>
                  <td>{sonderziele.tft}</td>
                  <td>{sonderziele.terminal}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${sonderziele.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${sonderziele.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${sonderziele.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="sbrConverterApp.sonderziele.home.notFound">No Sonderzieles found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Sonderziele;
