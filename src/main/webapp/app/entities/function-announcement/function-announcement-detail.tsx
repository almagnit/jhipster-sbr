import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './function-announcement.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FunctionAnnouncementDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const functionAnnouncementEntity = useAppSelector(state => state.functionAnnouncement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="functionAnnouncementDetailsHeading">
          <Translate contentKey="sbrConverterApp.functionAnnouncement.detail.title">FunctionAnnouncement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{functionAnnouncementEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="sbrConverterApp.functionAnnouncement.code">Code</Translate>
            </span>
          </dt>
          <dd>{functionAnnouncementEntity.code}</dd>
          <dt>
            <span id="audioFile">
              <Translate contentKey="sbrConverterApp.functionAnnouncement.audioFile">Audio File</Translate>
            </span>
          </dt>
          <dd>{functionAnnouncementEntity.audioFile}</dd>
          <dt>
            <span id="beschreibung">
              <Translate contentKey="sbrConverterApp.functionAnnouncement.beschreibung">Beschreibung</Translate>
            </span>
          </dt>
          <dd>{functionAnnouncementEntity.beschreibung}</dd>
          <dt>
            <span id="anmerkung">
              <Translate contentKey="sbrConverterApp.functionAnnouncement.anmerkung">Anmerkung</Translate>
            </span>
          </dt>
          <dd>{functionAnnouncementEntity.anmerkung}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="sbrConverterApp.functionAnnouncement.language">Language</Translate>
            </span>
          </dt>
          <dd>{functionAnnouncementEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/function-announcement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/function-announcement/${functionAnnouncementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FunctionAnnouncementDetail;
