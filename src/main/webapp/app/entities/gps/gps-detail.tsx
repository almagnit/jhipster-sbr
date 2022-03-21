import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './gps.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const GPSDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const gPSEntity = useAppSelector(state => state.gPS.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gPSDetailsHeading">
          <Translate contentKey="sbrConverterApp.gPS.detail.title">GPS</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{gPSEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="sbrConverterApp.gPS.name">Name</Translate>
            </span>
          </dt>
          <dd>{gPSEntity.name}</dd>
          <dt>
            <span id="gpsCode">
              <Translate contentKey="sbrConverterApp.gPS.gpsCode">Gps Code</Translate>
            </span>
          </dt>
          <dd>{gPSEntity.gpsCode}</dd>
          <dt>
            <span id="inneresFenster">
              <Translate contentKey="sbrConverterApp.gPS.inneresFenster">Inneres Fenster</Translate>
            </span>
          </dt>
          <dd>{gPSEntity.inneresFenster}</dd>
          <dt>
            <span id="auBeresFenster">
              <Translate contentKey="sbrConverterApp.gPS.auBeresFenster">Au Beres Fenster</Translate>
            </span>
          </dt>
          <dd>{gPSEntity.auBeresFenster}</dd>
        </dl>
        <Button tag={Link} to="/gps" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gps/${gPSEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GPSDetail;
