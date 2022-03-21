import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './special-info.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SpecialInfoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const specialInfoEntity = useAppSelector(state => state.specialInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="specialInfoDetailsHeading">
          <Translate contentKey="sbrConverterApp.specialInfo.detail.title">SpecialInfo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{specialInfoEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="sbrConverterApp.specialInfo.code">Code</Translate>
            </span>
          </dt>
          <dd>{specialInfoEntity.code}</dd>
          <dt>
            <span id="item">
              <Translate contentKey="sbrConverterApp.specialInfo.item">Item</Translate>
            </span>
          </dt>
          <dd>{specialInfoEntity.item}</dd>
          <dt>
            <span id="attribute">
              <Translate contentKey="sbrConverterApp.specialInfo.attribute">Attribute</Translate>
            </span>
          </dt>
          <dd>{specialInfoEntity.attribute}</dd>
          <dt>
            <span id="shortTerminalDesc">
              <Translate contentKey="sbrConverterApp.specialInfo.shortTerminalDesc">Short Terminal Desc</Translate>
            </span>
          </dt>
          <dd>{specialInfoEntity.shortTerminalDesc}</dd>
          <dt>
            <span id="longTerminalDesc">
              <Translate contentKey="sbrConverterApp.specialInfo.longTerminalDesc">Long Terminal Desc</Translate>
            </span>
          </dt>
          <dd>{specialInfoEntity.longTerminalDesc}</dd>
          <dt>
            <span id="displayText">
              <Translate contentKey="sbrConverterApp.specialInfo.displayText">Display Text</Translate>
            </span>
          </dt>
          <dd>{specialInfoEntity.displayText}</dd>
          <dt>
            <span id="ds003">
              <Translate contentKey="sbrConverterApp.specialInfo.ds003">Ds 003</Translate>
            </span>
          </dt>
          <dd>{specialInfoEntity.ds003}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="sbrConverterApp.specialInfo.language">Language</Translate>
            </span>
          </dt>
          <dd>{specialInfoEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/special-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/special-info/${specialInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SpecialInfoDetail;
