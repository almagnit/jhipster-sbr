import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './function-announcement.reducer';
import { IFunctionAnnouncement } from 'app/shared/model/function-announcement.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FunctionAnnouncementUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const functionAnnouncementEntity = useAppSelector(state => state.functionAnnouncement.entity);
  const loading = useAppSelector(state => state.functionAnnouncement.loading);
  const updating = useAppSelector(state => state.functionAnnouncement.updating);
  const updateSuccess = useAppSelector(state => state.functionAnnouncement.updateSuccess);
  const handleClose = () => {
    props.history.push('/function-announcement');
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
      ...functionAnnouncementEntity,
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
          ...functionAnnouncementEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.functionAnnouncement.home.createOrEditLabel" data-cy="FunctionAnnouncementCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.functionAnnouncement.home.createOrEditLabel">
              Create or edit a FunctionAnnouncement
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
                  id="function-announcement-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('sbrConverterApp.functionAnnouncement.code')}
                id="function-announcement-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.functionAnnouncement.audioFile')}
                id="function-announcement-audioFile"
                name="audioFile"
                data-cy="audioFile"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.functionAnnouncement.beschreibung')}
                id="function-announcement-beschreibung"
                name="beschreibung"
                data-cy="beschreibung"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.functionAnnouncement.anmerkung')}
                id="function-announcement-anmerkung"
                name="anmerkung"
                data-cy="anmerkung"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.functionAnnouncement.language')}
                id="function-announcement-language"
                name="language"
                data-cy="language"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/function-announcement" replace color="info">
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

export default FunctionAnnouncementUpdate;
