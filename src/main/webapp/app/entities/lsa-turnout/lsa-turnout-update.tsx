import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './lsa-turnout.reducer';
import { ILSATurnout } from 'app/shared/model/lsa-turnout.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LSATurnoutUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const lSATurnoutEntity = useAppSelector(state => state.lSATurnout.entity);
  const loading = useAppSelector(state => state.lSATurnout.loading);
  const updating = useAppSelector(state => state.lSATurnout.updating);
  const updateSuccess = useAppSelector(state => state.lSATurnout.updateSuccess);
  const handleClose = () => {
    props.history.push('/lsa-turnout');
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
      ...lSATurnoutEntity,
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
          ...lSATurnoutEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.lSATurnout.home.createOrEditLabel" data-cy="LSATurnoutCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.lSATurnout.home.createOrEditLabel">Create or edit a LSATurnout</Translate>
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
                  id="lsa-turnout-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('sbrConverterApp.lSATurnout.station1')}
                id="lsa-turnout-station1"
                name="station1"
                data-cy="station1"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.lSATurnout.station2')}
                id="lsa-turnout-station2"
                name="station2"
                data-cy="station2"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.lSATurnout.lsaCode')}
                id="lsa-turnout-lsaCode"
                name="lsaCode"
                data-cy="lsaCode"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.lSATurnout.turnout')}
                id="lsa-turnout-turnout"
                name="turnout"
                data-cy="turnout"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lsa-turnout" replace color="info">
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

export default LSATurnoutUpdate;
