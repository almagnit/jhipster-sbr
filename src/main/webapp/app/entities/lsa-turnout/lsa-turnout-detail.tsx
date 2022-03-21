import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './lsa-turnout.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LSATurnoutDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const lSATurnoutEntity = useAppSelector(state => state.lSATurnout.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lSATurnoutDetailsHeading">
          <Translate contentKey="sbrConverterApp.lSATurnout.detail.title">LSATurnout</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lSATurnoutEntity.id}</dd>
          <dt>
            <span id="station1">
              <Translate contentKey="sbrConverterApp.lSATurnout.station1">Station 1</Translate>
            </span>
          </dt>
          <dd>{lSATurnoutEntity.station1}</dd>
          <dt>
            <span id="station2">
              <Translate contentKey="sbrConverterApp.lSATurnout.station2">Station 2</Translate>
            </span>
          </dt>
          <dd>{lSATurnoutEntity.station2}</dd>
          <dt>
            <span id="lsaCode">
              <Translate contentKey="sbrConverterApp.lSATurnout.lsaCode">Lsa Code</Translate>
            </span>
          </dt>
          <dd>{lSATurnoutEntity.lsaCode}</dd>
          <dt>
            <span id="turnout">
              <Translate contentKey="sbrConverterApp.lSATurnout.turnout">Turnout</Translate>
            </span>
          </dt>
          <dd>{lSATurnoutEntity.turnout}</dd>
        </dl>
        <Button tag={Link} to="/lsa-turnout" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lsa-turnout/${lSATurnoutEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LSATurnoutDetail;
