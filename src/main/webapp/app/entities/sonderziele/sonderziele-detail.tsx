import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './sonderziele.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SonderzieleDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const sonderzieleEntity = useAppSelector(state => state.sonderziele.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sonderzieleDetailsHeading">
          <Translate contentKey="sbrConverterApp.sonderziele.detail.title">Sonderziele</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sonderzieleEntity.id}</dd>
          <dt>
            <span id="zugnummer">
              <Translate contentKey="sbrConverterApp.sonderziele.zugnummer">Zugnummer</Translate>
            </span>
          </dt>
          <dd>{sonderzieleEntity.zugnummer}</dd>
          <dt>
            <span id="front">
              <Translate contentKey="sbrConverterApp.sonderziele.front">Front</Translate>
            </span>
          </dt>
          <dd>{sonderzieleEntity.front}</dd>
          <dt>
            <span id="seite1">
              <Translate contentKey="sbrConverterApp.sonderziele.seite1">Seite 1</Translate>
            </span>
          </dt>
          <dd>{sonderzieleEntity.seite1}</dd>
          <dt>
            <span id="seite2">
              <Translate contentKey="sbrConverterApp.sonderziele.seite2">Seite 2</Translate>
            </span>
          </dt>
          <dd>{sonderzieleEntity.seite2}</dd>
          <dt>
            <span id="innen">
              <Translate contentKey="sbrConverterApp.sonderziele.innen">Innen</Translate>
            </span>
          </dt>
          <dd>{sonderzieleEntity.innen}</dd>
          <dt>
            <span id="tft">
              <Translate contentKey="sbrConverterApp.sonderziele.tft">Tft</Translate>
            </span>
          </dt>
          <dd>{sonderzieleEntity.tft}</dd>
          <dt>
            <span id="terminal">
              <Translate contentKey="sbrConverterApp.sonderziele.terminal">Terminal</Translate>
            </span>
          </dt>
          <dd>{sonderzieleEntity.terminal}</dd>
        </dl>
        <Button tag={Link} to="/sonderziele" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sonderziele/${sonderzieleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SonderzieleDetail;
