import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './fahrten.reducer';
import { IFahrten } from 'app/shared/model/fahrten.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FahrtenUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const fahrtenEntity = useAppSelector(state => state.fahrten.entity);
  const loading = useAppSelector(state => state.fahrten.loading);
  const updating = useAppSelector(state => state.fahrten.updating);
  const updateSuccess = useAppSelector(state => state.fahrten.updateSuccess);
  const handleClose = () => {
    props.history.push('/fahrten');
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
      ...fahrtenEntity,
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
          ...fahrtenEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.fahrten.home.createOrEditLabel" data-cy="FahrtenCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.fahrten.home.createOrEditLabel">Create or edit a Fahrten</Translate>
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
                  id="fahrten-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('sbrConverterApp.fahrten.titel')}
                id="fahrten-titel"
                name="titel"
                data-cy="titel"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.fahrten.zugnummer')}
                id="fahrten-zugnummer"
                name="zugnummer"
                data-cy="zugnummer"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.fahrten.znrBeschreibung')}
                id="fahrten-znrBeschreibung"
                name="znrBeschreibung"
                data-cy="znrBeschreibung"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.fahrten.variante')}
                id="fahrten-variante"
                name="variante"
                data-cy="variante"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.fahrten.tagesart')}
                id="fahrten-tagesart"
                name="tagesart"
                data-cy="tagesart"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.fahrten.umlauf')}
                id="fahrten-umlauf"
                name="umlauf"
                data-cy="umlauf"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.fahrten.umlaufindex')}
                id="fahrten-umlaufindex"
                name="umlaufindex"
                data-cy="umlaufindex"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fahrten" replace color="info">
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

export default FahrtenUpdate;
