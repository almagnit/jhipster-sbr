import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './special-info.reducer';
import { ISpecialInfo } from 'app/shared/model/special-info.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SpecialInfoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const specialInfoEntity = useAppSelector(state => state.specialInfo.entity);
  const loading = useAppSelector(state => state.specialInfo.loading);
  const updating = useAppSelector(state => state.specialInfo.updating);
  const updateSuccess = useAppSelector(state => state.specialInfo.updateSuccess);
  const handleClose = () => {
    props.history.push('/special-info');
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
      ...specialInfoEntity,
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
          ...specialInfoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.specialInfo.home.createOrEditLabel" data-cy="SpecialInfoCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.specialInfo.home.createOrEditLabel">Create or edit a SpecialInfo</Translate>
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
                  id="special-info-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('sbrConverterApp.specialInfo.code')}
                id="special-info-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialInfo.item')}
                id="special-info-item"
                name="item"
                data-cy="item"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialInfo.attribute')}
                id="special-info-attribute"
                name="attribute"
                data-cy="attribute"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialInfo.shortTerminalDesc')}
                id="special-info-shortTerminalDesc"
                name="shortTerminalDesc"
                data-cy="shortTerminalDesc"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialInfo.longTerminalDesc')}
                id="special-info-longTerminalDesc"
                name="longTerminalDesc"
                data-cy="longTerminalDesc"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialInfo.displayText')}
                id="special-info-displayText"
                name="displayText"
                data-cy="displayText"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialInfo.ds003')}
                id="special-info-ds003"
                name="ds003"
                data-cy="ds003"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.specialInfo.language')}
                id="special-info-language"
                name="language"
                data-cy="language"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/special-info" replace color="info">
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

export default SpecialInfoUpdate;
