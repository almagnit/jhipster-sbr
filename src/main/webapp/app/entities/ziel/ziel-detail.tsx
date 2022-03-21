import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './ziel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ZielDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const zielEntity = useAppSelector(state => state.ziel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="zielDetailsHeading">
          <Translate contentKey="sbrConverterApp.ziel.detail.title">Ziel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{zielEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="sbrConverterApp.ziel.code">Code</Translate>
            </span>
          </dt>
          <dd>{zielEntity.code}</dd>
          <dt>
            <span id="front">
              <Translate contentKey="sbrConverterApp.ziel.front">Front</Translate>
            </span>
          </dt>
          <dd>{zielEntity.front}</dd>
          <dt>
            <span id="seite1">
              <Translate contentKey="sbrConverterApp.ziel.seite1">Seite 1</Translate>
            </span>
          </dt>
          <dd>{zielEntity.seite1}</dd>
          <dt>
            <span id="seite2">
              <Translate contentKey="sbrConverterApp.ziel.seite2">Seite 2</Translate>
            </span>
          </dt>
          <dd>{zielEntity.seite2}</dd>
          <dt>
            <span id="innen">
              <Translate contentKey="sbrConverterApp.ziel.innen">Innen</Translate>
            </span>
          </dt>
          <dd>{zielEntity.innen}</dd>
          <dt>
            <span id="tft">
              <Translate contentKey="sbrConverterApp.ziel.tft">Tft</Translate>
            </span>
          </dt>
          <dd>{zielEntity.tft}</dd>
          <dt>
            <span id="terminal">
              <Translate contentKey="sbrConverterApp.ziel.terminal">Terminal</Translate>
            </span>
          </dt>
          <dd>{zielEntity.terminal}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="sbrConverterApp.ziel.language">Language</Translate>
            </span>
          </dt>
          <dd>{zielEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/ziel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ziel/${zielEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ZielDetail;
