import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './vts.reducer';
import { IVTS } from 'app/shared/model/vts.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VTSUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const vTSEntity = useAppSelector(state => state.vTS.entity);
  const loading = useAppSelector(state => state.vTS.loading);
  const updating = useAppSelector(state => state.vTS.updating);
  const updateSuccess = useAppSelector(state => state.vTS.updateSuccess);
  const handleClose = () => {
    props.history.push('/vts');
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
      ...vTSEntity,
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
          ...vTSEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.vTS.home.createOrEditLabel" data-cy="VTSCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.vTS.home.createOrEditLabel">Create or edit a VTS</Translate>
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
                  id="vts-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('sbrConverterApp.vTS.verkehrstage')}
                id="vts-verkehrstage"
                name="verkehrstage"
                data-cy="verkehrstage"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.vTS.beschreibung')}
                id="vts-beschreibung"
                name="beschreibung"
                data-cy="beschreibung"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.vTS.versionsnummer')}
                id="vts-versionsnummer"
                name="versionsnummer"
                data-cy="versionsnummer"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.vTS.mandant')}
                id="vts-mandant"
                name="mandant"
                data-cy="mandant"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.vTS.feiertage')}
                id="vts-feiertage"
                name="feiertage"
                data-cy="feiertage"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.vTS.gueltigkeit')}
                id="vts-gueltigkeit"
                name="gueltigkeit"
                data-cy="gueltigkeit"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vts" replace color="info">
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

export default VTSUpdate;
