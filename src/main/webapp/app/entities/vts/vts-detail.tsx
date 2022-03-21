import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './vts.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VTSDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const vTSEntity = useAppSelector(state => state.vTS.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vTSDetailsHeading">
          <Translate contentKey="sbrConverterApp.vTS.detail.title">VTS</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vTSEntity.id}</dd>
          <dt>
            <span id="verkehrstage">
              <Translate contentKey="sbrConverterApp.vTS.verkehrstage">Verkehrstage</Translate>
            </span>
          </dt>
          <dd>{vTSEntity.verkehrstage}</dd>
          <dt>
            <span id="beschreibung">
              <Translate contentKey="sbrConverterApp.vTS.beschreibung">Beschreibung</Translate>
            </span>
          </dt>
          <dd>{vTSEntity.beschreibung}</dd>
          <dt>
            <span id="versionsnummer">
              <Translate contentKey="sbrConverterApp.vTS.versionsnummer">Versionsnummer</Translate>
            </span>
          </dt>
          <dd>{vTSEntity.versionsnummer}</dd>
          <dt>
            <span id="mandant">
              <Translate contentKey="sbrConverterApp.vTS.mandant">Mandant</Translate>
            </span>
          </dt>
          <dd>{vTSEntity.mandant}</dd>
          <dt>
            <span id="feiertage">
              <Translate contentKey="sbrConverterApp.vTS.feiertage">Feiertage</Translate>
            </span>
          </dt>
          <dd>{vTSEntity.feiertage}</dd>
          <dt>
            <span id="gueltigkeit">
              <Translate contentKey="sbrConverterApp.vTS.gueltigkeit">Gueltigkeit</Translate>
            </span>
          </dt>
          <dd>{vTSEntity.gueltigkeit}</dd>
        </dl>
        <Button tag={Link} to="/vts" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vts/${vTSEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VTSDetail;
