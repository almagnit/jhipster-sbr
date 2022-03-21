import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './ort.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OrtDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ortEntity = useAppSelector(state => state.ort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ortDetailsHeading">
          <Translate contentKey="sbrConverterApp.ort.detail.title">Ort</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ortEntity.id}</dd>
          <dt>
            <span id="ibnr">
              <Translate contentKey="sbrConverterApp.ort.ibnr">Ibnr</Translate>
            </span>
          </dt>
          <dd>{ortEntity.ibnr}</dd>
          <dt>
            <span id="ds100">
              <Translate contentKey="sbrConverterApp.ort.ds100">Ds 100</Translate>
            </span>
          </dt>
          <dd>{ortEntity.ds100}</dd>
          <dt>
            <span id="front">
              <Translate contentKey="sbrConverterApp.ort.front">Front</Translate>
            </span>
          </dt>
          <dd>{ortEntity.front}</dd>
          <dt>
            <span id="seite">
              <Translate contentKey="sbrConverterApp.ort.seite">Seite</Translate>
            </span>
          </dt>
          <dd>{ortEntity.seite}</dd>
          <dt>
            <span id="innen">
              <Translate contentKey="sbrConverterApp.ort.innen">Innen</Translate>
            </span>
          </dt>
          <dd>{ortEntity.innen}</dd>
          <dt>
            <span id="tft">
              <Translate contentKey="sbrConverterApp.ort.tft">Tft</Translate>
            </span>
          </dt>
          <dd>{ortEntity.tft}</dd>
          <dt>
            <span id="terminal">
              <Translate contentKey="sbrConverterApp.ort.terminal">Terminal</Translate>
            </span>
          </dt>
          <dd>{ortEntity.terminal}</dd>
          <dt>
            <span id="ds001c">
              <Translate contentKey="sbrConverterApp.ort.ds001c">Ds 001 C</Translate>
            </span>
          </dt>
          <dd>{ortEntity.ds001c}</dd>
          <dt>
            <span id="video">
              <Translate contentKey="sbrConverterApp.ort.video">Video</Translate>
            </span>
          </dt>
          <dd>{ortEntity.video}</dd>
          <dt>
            <span id="ds009">
              <Translate contentKey="sbrConverterApp.ort.ds009">Ds 009</Translate>
            </span>
          </dt>
          <dd>{ortEntity.ds009}</dd>
          <dt>
            <span id="ds003">
              <Translate contentKey="sbrConverterApp.ort.ds003">Ds 003</Translate>
            </span>
          </dt>
          <dd>{ortEntity.ds003}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="sbrConverterApp.ort.language">Language</Translate>
            </span>
          </dt>
          <dd>{ortEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/ort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ort/${ortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrtDetail;
