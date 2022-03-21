import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './route.reducer';
import { IRoute } from 'app/shared/model/route.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Route = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const routeList = useAppSelector(state => state.route.entities);
  const loading = useAppSelector(state => state.route.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="route-heading" data-cy="RouteHeading">
        <Translate contentKey="sbrConverterApp.route.home.title">Routes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="sbrConverterApp.route.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sbrConverterApp.route.home.createLabel">Create new Route</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {routeList && routeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="sbrConverterApp.route.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.ort">Ort</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.entfernung">Entfernung</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.haltegrund">Haltegrund</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.gleis">Gleis</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.ausstieg">Ausstieg</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.gps">Gps</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.ansage">Ansage</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.endAnsage">End Ansage</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.startAnsage">Start Ansage</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.viaAnsage">Via Ansage</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.stopAnsage">Stop Ansage</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.stopAnsageMode">Stop Ansage Mode</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.zugziel">Zugziel</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.entwerter1">Entwerter 1</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.entwerter2">Entwerter 2</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.zoneninfo">Zoneninfo</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.automat10">Automat 10</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.attribute">Attribute</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.sprache1">Sprache 1</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.sprache2">Sprache 2</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.sprache3">Sprache 3</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.kurs">Kurs</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.zieltexte">Zieltexte</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.liniennummer">Liniennummer</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.gattung">Gattung</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.lineMarker">Line Marker</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.ds010">Ds 010</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.linienansage">Linienansage</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.spurkranz">Spurkranz</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.skDauer">Sk Dauer</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.skBoogie">Sk Boogie</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.prmTur">Prm Tur</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.route.tursperrung">Tursperrung</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {routeList.map((route, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${route.id}`} color="link" size="sm">
                      {route.id}
                    </Button>
                  </td>
                  <td>{route.ort}</td>
                  <td>{route.entfernung}</td>
                  <td>{route.haltegrund}</td>
                  <td>{route.gleis}</td>
                  <td>{route.ausstieg}</td>
                  <td>{route.gps}</td>
                  <td>{route.ansage}</td>
                  <td>{route.endAnsage}</td>
                  <td>{route.startAnsage}</td>
                  <td>{route.viaAnsage}</td>
                  <td>{route.stopAnsage}</td>
                  <td>{route.stopAnsageMode}</td>
                  <td>{route.zugziel}</td>
                  <td>{route.entwerter1}</td>
                  <td>{route.entwerter2}</td>
                  <td>{route.zoneninfo}</td>
                  <td>{route.automat10}</td>
                  <td>{route.attribute}</td>
                  <td>{route.sprache1}</td>
                  <td>{route.sprache2}</td>
                  <td>{route.sprache3}</td>
                  <td>{route.kurs}</td>
                  <td>{route.zieltexte}</td>
                  <td>{route.liniennummer}</td>
                  <td>{route.gattung}</td>
                  <td>{route.lineMarker}</td>
                  <td>{route.ds010}</td>
                  <td>{route.linienansage}</td>
                  <td>{route.spurkranz}</td>
                  <td>{route.skDauer}</td>
                  <td>{route.skBoogie}</td>
                  <td>{route.prmTur}</td>
                  <td>{route.tursperrung}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${route.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${route.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${route.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="sbrConverterApp.route.home.notFound">No Routes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Route;
