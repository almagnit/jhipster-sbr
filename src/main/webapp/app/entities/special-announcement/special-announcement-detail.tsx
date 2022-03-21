import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './special-announcement.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SpecialAnnouncementDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const specialAnnouncementEntity = useAppSelector(state => state.specialAnnouncement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="specialAnnouncementDetailsHeading">
          <Translate contentKey="sbrConverterApp.specialAnnouncement.detail.title">SpecialAnnouncement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{specialAnnouncementEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="sbrConverterApp.specialAnnouncement.code">Code</Translate>
            </span>
          </dt>
          <dd>{specialAnnouncementEntity.code}</dd>
          <dt>
            <span id="item">
              <Translate contentKey="sbrConverterApp.specialAnnouncement.item">Item</Translate>
            </span>
          </dt>
          <dd>{specialAnnouncementEntity.item}</dd>
          <dt>
            <span id="ausgabeOrt">
              <Translate contentKey="sbrConverterApp.specialAnnouncement.ausgabeOrt">Ausgabe Ort</Translate>
            </span>
          </dt>
          <dd>{specialAnnouncementEntity.ausgabeOrt}</dd>
          <dt>
            <span id="kurz">
              <Translate contentKey="sbrConverterApp.specialAnnouncement.kurz">Kurz</Translate>
            </span>
          </dt>
          <dd>{specialAnnouncementEntity.kurz}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="sbrConverterApp.specialAnnouncement.language">Language</Translate>
            </span>
          </dt>
          <dd>{specialAnnouncementEntity.language}</dd>
          <dt>
            <span id="ansagedatei">
              <Translate contentKey="sbrConverterApp.specialAnnouncement.ansagedatei">Ansagedatei</Translate>
            </span>
          </dt>
          <dd>{specialAnnouncementEntity.ansagedatei}</dd>
          <dt>
            <span id="klartext">
              <Translate contentKey="sbrConverterApp.specialAnnouncement.klartext">Klartext</Translate>
            </span>
          </dt>
          <dd>{specialAnnouncementEntity.klartext}</dd>
        </dl>
        <Button tag={Link} to="/special-announcement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/special-announcement/${specialAnnouncementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SpecialAnnouncementDetail;
