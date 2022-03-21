import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './ort.reducer';
import { IOrt } from 'app/shared/model/ort.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OrtUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ortEntity = useAppSelector(state => state.ort.entity);
  const loading = useAppSelector(state => state.ort.loading);
  const updating = useAppSelector(state => state.ort.updating);
  const updateSuccess = useAppSelector(state => state.ort.updateSuccess);
  const handleClose = () => {
    props.history.push('/ort');
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
      ...ortEntity,
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
          ...ortEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.ort.home.createOrEditLabel" data-cy="OrtCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.ort.home.createOrEditLabel">Create or edit a Ort</Translate>
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
                  id="ort-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('sbrConverterApp.ort.ibnr')} id="ort-ibnr" name="ibnr" data-cy="ibnr" type="text" />
              <ValidatedField label={translate('sbrConverterApp.ort.ds100')} id="ort-ds100" name="ds100" data-cy="ds100" type="text" />
              <ValidatedField label={translate('sbrConverterApp.ort.front')} id="ort-front" name="front" data-cy="front" type="text" />
              <ValidatedField label={translate('sbrConverterApp.ort.seite')} id="ort-seite" name="seite" data-cy="seite" type="text" />
              <ValidatedField label={translate('sbrConverterApp.ort.innen')} id="ort-innen" name="innen" data-cy="innen" type="text" />
              <ValidatedField label={translate('sbrConverterApp.ort.tft')} id="ort-tft" name="tft" data-cy="tft" type="text" />
              <ValidatedField
                label={translate('sbrConverterApp.ort.terminal')}
                id="ort-terminal"
                name="terminal"
                data-cy="terminal"
                type="text"
              />
              <ValidatedField label={translate('sbrConverterApp.ort.ds001c')} id="ort-ds001c" name="ds001c" data-cy="ds001c" type="text" />
              <ValidatedField label={translate('sbrConverterApp.ort.video')} id="ort-video" name="video" data-cy="video" type="text" />
              <ValidatedField label={translate('sbrConverterApp.ort.ds009')} id="ort-ds009" name="ds009" data-cy="ds009" type="text" />
              <ValidatedField label={translate('sbrConverterApp.ort.ds003')} id="ort-ds003" name="ds003" data-cy="ds003" type="text" />
              <ValidatedField
                label={translate('sbrConverterApp.ort.language')}
                id="ort-language"
                name="language"
                data-cy="language"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ort" replace color="info">
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

export default OrtUpdate;
