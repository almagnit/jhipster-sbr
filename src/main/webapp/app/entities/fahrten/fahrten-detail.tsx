import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './fahrten.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FahrtenDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const fahrtenEntity = useAppSelector(state => state.fahrten.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fahrtenDetailsHeading">
          <Translate contentKey="sbrConverterApp.fahrten.detail.title">Fahrten</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fahrtenEntity.id}</dd>
          <dt>
            <span id="titel">
              <Translate contentKey="sbrConverterApp.fahrten.titel">Titel</Translate>
            </span>
          </dt>
          <dd>{fahrtenEntity.titel}</dd>
          <dt>
            <span id="zugnummer">
              <Translate contentKey="sbrConverterApp.fahrten.zugnummer">Zugnummer</Translate>
            </span>
          </dt>
          <dd>{fahrtenEntity.zugnummer}</dd>
          <dt>
            <span id="znrBeschreibung">
              <Translate contentKey="sbrConverterApp.fahrten.znrBeschreibung">Znr Beschreibung</Translate>
            </span>
          </dt>
          <dd>{fahrtenEntity.znrBeschreibung}</dd>
          <dt>
            <span id="variante">
              <Translate contentKey="sbrConverterApp.fahrten.variante">Variante</Translate>
            </span>
          </dt>
          <dd>{fahrtenEntity.variante}</dd>
          <dt>
            <span id="tagesart">
              <Translate contentKey="sbrConverterApp.fahrten.tagesart">Tagesart</Translate>
            </span>
          </dt>
          <dd>{fahrtenEntity.tagesart}</dd>
          <dt>
            <span id="umlauf">
              <Translate contentKey="sbrConverterApp.fahrten.umlauf">Umlauf</Translate>
            </span>
          </dt>
          <dd>{fahrtenEntity.umlauf}</dd>
          <dt>
            <span id="umlaufindex">
              <Translate contentKey="sbrConverterApp.fahrten.umlaufindex">Umlaufindex</Translate>
            </span>
          </dt>
          <dd>{fahrtenEntity.umlaufindex}</dd>
        </dl>
        <Button tag={Link} to="/fahrten" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fahrten/${fahrtenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FahrtenDetail;
