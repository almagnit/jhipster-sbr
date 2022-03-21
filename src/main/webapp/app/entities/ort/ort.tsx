import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './ort.reducer';
import { IOrt } from 'app/shared/model/ort.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Ort = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const ortList = useAppSelector(state => state.ort.entities);
  const loading = useAppSelector(state => state.ort.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="ort-heading" data-cy="OrtHeading">
        <Translate contentKey="sbrConverterApp.ort.home.title">Orts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="sbrConverterApp.ort.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sbrConverterApp.ort.home.createLabel">Create new Ort</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ortList && ortList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.ibnr">Ibnr</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.ds100">Ds 100</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.front">Front</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.seite">Seite</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.innen">Innen</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.tft">Tft</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.terminal">Terminal</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.ds001c">Ds 001 C</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.video">Video</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.ds009">Ds 009</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.ds003">Ds 003</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.ort.language">Language</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ortList.map((ort, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${ort.id}`} color="link" size="sm">
                      {ort.id}
                    </Button>
                  </td>
                  <td>{ort.ibnr}</td>
                  <td>{ort.ds100}</td>
                  <td>{ort.front}</td>
                  <td>{ort.seite}</td>
                  <td>{ort.innen}</td>
                  <td>{ort.tft}</td>
                  <td>{ort.terminal}</td>
                  <td>{ort.ds001c}</td>
                  <td>{ort.video}</td>
                  <td>{ort.ds009}</td>
                  <td>{ort.ds003}</td>
                  <td>{ort.language}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${ort.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ort.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ort.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="sbrConverterApp.ort.home.notFound">No Orts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Ort;
