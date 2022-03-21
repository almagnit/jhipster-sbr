import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './special-announcement.reducer';
import { ISpecialAnnouncement } from 'app/shared/model/special-announcement.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SpecialAnnouncementUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const specialAnnouncementEntity = useAppSelector(state => state.specialAnnouncement.entity);
  const loading = useAppSelector(state => state.specialAnnouncement.loading);
  const updating = useAppSelector(state => state.specialAnnouncement.updating);
  const updateSuccess = useAppSelector(state => state.specialAnnouncement.updateSuccess);
  const handleClose = () => {
    props.history.push('/special-announcement');
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
      ...specialAnnouncementEntity,
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
          ...specialAnnouncementEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.specialAnnouncement.home.createOrEditLabel" data-cy="SpecialAnnouncementCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.specialAnnouncement.home.createOrEditLabel">
              Create or edit a SpecialAnnouncement
            </Translate>
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
                  id="special-announcement-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('sbrConverterApp.specialAnnouncement.code')}
                id="special-announcement-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialAnnouncement.item')}
                id="special-announcement-item"
                name="item"
                data-cy="item"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialAnnouncement.ausgabeOrt')}
                id="special-announcement-ausgabeOrt"
                name="ausgabeOrt"
                data-cy="ausgabeOrt"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialAnnouncement.kurz')}
                id="special-announcement-kurz"
                name="kurz"
                data-cy="kurz"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialAnnouncement.language')}
                id="special-announcement-language"
                name="language"
                data-cy="language"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialAnnouncement.ansagedatei')}
                id="special-announcement-ansagedatei"
                name="ansagedatei"
                data-cy="ansagedatei"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialAnnouncement.klartext')}
                id="special-announcement-klartext"
                name="klartext"
                data-cy="klartext"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/special-announcement" replace color="info">
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

export default SpecialAnnouncementUpdate;
