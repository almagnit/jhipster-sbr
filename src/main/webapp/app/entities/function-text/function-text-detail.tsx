import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './function-text.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FunctionTextDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const functionTextEntity = useAppSelector(state => state.functionText.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="functionTextDetailsHeading">
          <Translate contentKey="sbrConverterApp.functionText.detail.title">FunctionText</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{functionTextEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="sbrConverterApp.functionText.code">Code</Translate>
            </span>
          </dt>
          <dd>{functionTextEntity.code}</dd>
          <dt>
            <span id="text">
              <Translate contentKey="sbrConverterApp.functionText.text">Text</Translate>
            </span>
          </dt>
          <dd>{functionTextEntity.text}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="sbrConverterApp.functionText.language">Language</Translate>
            </span>
          </dt>
          <dd>{functionTextEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/function-text" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/function-text/${functionTextEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FunctionTextDetail;
