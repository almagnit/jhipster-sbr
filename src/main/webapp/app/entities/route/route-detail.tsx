import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './route.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RouteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const routeEntity = useAppSelector(state => state.route.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="routeDetailsHeading">
          <Translate contentKey="sbrConverterApp.route.detail.title">Route</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{routeEntity.id}</dd>
          <dt>
            <span id="ort">
              <Translate contentKey="sbrConverterApp.route.ort">Ort</Translate>
            </span>
          </dt>
          <dd>{routeEntity.ort}</dd>
          <dt>
            <span id="entfernung">
              <Translate contentKey="sbrConverterApp.route.entfernung">Entfernung</Translate>
            </span>
          </dt>
          <dd>{routeEntity.entfernung}</dd>
          <dt>
            <span id="haltegrund">
              <Translate contentKey="sbrConverterApp.route.haltegrund">Haltegrund</Translate>
            </span>
          </dt>
          <dd>{routeEntity.haltegrund}</dd>
          <dt>
            <span id="gleis">
              <Translate contentKey="sbrConverterApp.route.gleis">Gleis</Translate>
            </span>
          </dt>
          <dd>{routeEntity.gleis}</dd>
          <dt>
            <span id="ausstieg">
              <Translate contentKey="sbrConverterApp.route.ausstieg">Ausstieg</Translate>
            </span>
          </dt>
          <dd>{routeEntity.ausstieg}</dd>
          <dt>
            <span id="gps">
              <Translate contentKey="sbrConverterApp.route.gps">Gps</Translate>
            </span>
          </dt>
          <dd>{routeEntity.gps}</dd>
          <dt>
            <span id="ansage">
              <Translate contentKey="sbrConverterApp.route.ansage">Ansage</Translate>
            </span>
          </dt>
          <dd>{routeEntity.ansage}</dd>
          <dt>
            <span id="endAnsage">
              <Translate contentKey="sbrConverterApp.route.endAnsage">End Ansage</Translate>
            </span>
          </dt>
          <dd>{routeEntity.endAnsage}</dd>
          <dt>
            <span id="startAnsage">
              <Translate contentKey="sbrConverterApp.route.startAnsage">Start Ansage</Translate>
            </span>
          </dt>
          <dd>{routeEntity.startAnsage}</dd>
          <dt>
            <span id="viaAnsage">
              <Translate contentKey="sbrConverterApp.route.viaAnsage">Via Ansage</Translate>
            </span>
          </dt>
          <dd>{routeEntity.viaAnsage}</dd>
          <dt>
            <span id="stopAnsage">
              <Translate contentKey="sbrConverterApp.route.stopAnsage">Stop Ansage</Translate>
            </span>
          </dt>
          <dd>{routeEntity.stopAnsage}</dd>
          <dt>
            <span id="stopAnsageMode">
              <Translate contentKey="sbrConverterApp.route.stopAnsageMode">Stop Ansage Mode</Translate>
            </span>
          </dt>
          <dd>{routeEntity.stopAnsageMode}</dd>
          <dt>
            <span id="zugziel">
              <Translate contentKey="sbrConverterApp.route.zugziel">Zugziel</Translate>
            </span>
          </dt>
          <dd>{routeEntity.zugziel}</dd>
          <dt>
            <span id="entwerter1">
              <Translate contentKey="sbrConverterApp.route.entwerter1">Entwerter 1</Translate>
            </span>
          </dt>
          <dd>{routeEntity.entwerter1}</dd>
          <dt>
            <span id="entwerter2">
              <Translate contentKey="sbrConverterApp.route.entwerter2">Entwerter 2</Translate>
            </span>
          </dt>
          <dd>{routeEntity.entwerter2}</dd>
          <dt>
            <span id="zoneninfo">
              <Translate contentKey="sbrConverterApp.route.zoneninfo">Zoneninfo</Translate>
            </span>
          </dt>
          <dd>{routeEntity.zoneninfo}</dd>
          <dt>
            <span id="automat10">
              <Translate contentKey="sbrConverterApp.route.automat10">Automat 10</Translate>
            </span>
          </dt>
          <dd>{routeEntity.automat10}</dd>
          <dt>
            <span id="attribute">
              <Translate contentKey="sbrConverterApp.route.attribute">Attribute</Translate>
            </span>
          </dt>
          <dd>{routeEntity.attribute}</dd>
          <dt>
            <span id="sprache1">
              <Translate contentKey="sbrConverterApp.route.sprache1">Sprache 1</Translate>
            </span>
          </dt>
          <dd>{routeEntity.sprache1}</dd>
          <dt>
            <span id="sprache2">
              <Translate contentKey="sbrConverterApp.route.sprache2">Sprache 2</Translate>
            </span>
          </dt>
          <dd>{routeEntity.sprache2}</dd>
          <dt>
            <span id="sprache3">
              <Translate contentKey="sbrConverterApp.route.sprache3">Sprache 3</Translate>
            </span>
          </dt>
          <dd>{routeEntity.sprache3}</dd>
          <dt>
            <span id="kurs">
              <Translate contentKey="sbrConverterApp.route.kurs">Kurs</Translate>
            </span>
          </dt>
          <dd>{routeEntity.kurs}</dd>
          <dt>
            <span id="zieltexte">
              <Translate contentKey="sbrConverterApp.route.zieltexte">Zieltexte</Translate>
            </span>
          </dt>
          <dd>{routeEntity.zieltexte}</dd>
          <dt>
            <span id="liniennummer">
              <Translate contentKey="sbrConverterApp.route.liniennummer">Liniennummer</Translate>
            </span>
          </dt>
          <dd>{routeEntity.liniennummer}</dd>
          <dt>
            <span id="gattung">
              <Translate contentKey="sbrConverterApp.route.gattung">Gattung</Translate>
            </span>
          </dt>
          <dd>{routeEntity.gattung}</dd>
          <dt>
            <span id="lineMarker">
              <Translate contentKey="sbrConverterApp.route.lineMarker">Line Marker</Translate>
            </span>
          </dt>
          <dd>{routeEntity.lineMarker}</dd>
          <dt>
            <span id="ds010">
              <Translate contentKey="sbrConverterApp.route.ds010">Ds 010</Translate>
            </span>
          </dt>
          <dd>{routeEntity.ds010}</dd>
          <dt>
            <span id="linienansage">
              <Translate contentKey="sbrConverterApp.route.linienansage">Linienansage</Translate>
            </span>
          </dt>
          <dd>{routeEntity.linienansage}</dd>
          <dt>
            <span id="spurkranz">
              <Translate contentKey="sbrConverterApp.route.spurkranz">Spurkranz</Translate>
            </span>
          </dt>
          <dd>{routeEntity.spurkranz}</dd>
          <dt>
            <span id="skDauer">
              <Translate contentKey="sbrConverterApp.route.skDauer">Sk Dauer</Translate>
            </span>
          </dt>
          <dd>{routeEntity.skDauer}</dd>
          <dt>
            <span id="skBoogie">
              <Translate contentKey="sbrConverterApp.route.skBoogie">Sk Boogie</Translate>
            </span>
          </dt>
          <dd>{routeEntity.skBoogie}</dd>
          <dt>
            <span id="prmTur">
              <Translate contentKey="sbrConverterApp.route.prmTur">Prm Tur</Translate>
            </span>
          </dt>
          <dd>{routeEntity.prmTur}</dd>
          <dt>
            <span id="tursperrung">
              <Translate contentKey="sbrConverterApp.route.tursperrung">Tursperrung</Translate>
            </span>
          </dt>
          <dd>{routeEntity.tursperrung}</dd>
        </dl>
        <Button tag={Link} to="/route" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/route/${routeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RouteDetail;
