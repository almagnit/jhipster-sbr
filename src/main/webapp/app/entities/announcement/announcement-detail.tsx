import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './announcement.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnnouncementDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const announcementEntity = useAppSelector(state => state.announcement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="announcementDetailsHeading">
          <Translate contentKey="sbrConverterApp.announcement.detail.title">Announcement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{announcementEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="sbrConverterApp.announcement.code">Code</Translate>
            </span>
          </dt>
          <dd>{announcementEntity.code}</dd>
          <dt>
            <span id="ansage">
              <Translate contentKey="sbrConverterApp.announcement.ansage">Ansage</Translate>
            </span>
          </dt>
          <dd>{announcementEntity.ansage}</dd>
          <dt>
            <span id="item">
              <Translate contentKey="sbrConverterApp.announcement.item">Item</Translate>
            </span>
          </dt>
          <dd>{announcementEntity.item}</dd>
          <dt>
            <span id="announcementfile">
              <Translate contentKey="sbrConverterApp.announcement.announcementfile">Announcementfile</Translate>
            </span>
          </dt>
          <dd>{announcementEntity.announcementfile}</dd>
          <dt>
            <span id="klartext">
              <Translate contentKey="sbrConverterApp.announcement.klartext">Klartext</Translate>
            </span>
          </dt>
          <dd>{announcementEntity.klartext}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="sbrConverterApp.announcement.language">Language</Translate>
            </span>
          </dt>
          <dd>{announcementEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/announcement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/announcement/${announcementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnnouncementDetail;
