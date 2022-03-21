import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './gps.reducer';
import { IGPS } from 'app/shared/model/gps.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const GPSUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const gPSEntity = useAppSelector(state => state.gPS.entity);
  const loading = useAppSelector(state => state.gPS.loading);
  const updating = useAppSelector(state => state.gPS.updating);
  const updateSuccess = useAppSelector(state => state.gPS.updateSuccess);
  const handleClose = () => {
    props.history.push('/gps');
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
      ...gPSEntity,
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
          ...gPSEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.gPS.home.createOrEditLabel" data-cy="GPSCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.gPS.home.createOrEditLabel">Create or edit a GPS</Translate>
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
                  id="gps-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('sbrConverterApp.gPS.name')} id="gps-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('sbrConverterApp.gPS.gpsCode')}
                id="gps-gpsCode"
                name="gpsCode"
                data-cy="gpsCode"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.gPS.inneresFenster')}
                id="gps-inneresFenster"
                name="inneresFenster"
                data-cy="inneresFenster"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.gPS.auBeresFenster')}
                id="gps-auBeresFenster"
                name="auBeresFenster"
                data-cy="auBeresFenster"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gps" replace color="info">
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

export default GPSUpdate;
