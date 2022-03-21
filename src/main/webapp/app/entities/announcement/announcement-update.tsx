import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './announcement.reducer';
import { IAnnouncement } from 'app/shared/model/announcement.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnnouncementUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const announcementEntity = useAppSelector(state => state.announcement.entity);
  const loading = useAppSelector(state => state.announcement.loading);
  const updating = useAppSelector(state => state.announcement.updating);
  const updateSuccess = useAppSelector(state => state.announcement.updateSuccess);
  const handleClose = () => {
    props.history.push('/announcement');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...announcementEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...announcementEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.announcement.home.createOrEditLabel" data-cy="AnnouncementCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.announcement.home.createOrEditLabel">Create or edit a Announcement</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="announcement-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('sbrConverterApp.announcement.code')}
                id="announcement-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.announcement.ansage')}
                id="announcement-ansage"
                name="ansage"
                data-cy="ansage"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.announcement.item')}
                id="announcement-item"
                name="item"
                data-cy="item"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.announcement.announcementfile')}
                id="announcement-announcementfile"
                name="announcementfile"
                data-cy="announcementfile"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.announcement.klartext')}
                id="announcement-klartext"
                name="klartext"
                data-cy="klartext"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.announcement.language')}
                id="announcement-language"
                name="language"
                data-cy="language"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/announcement" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AnnouncementUpdate;
