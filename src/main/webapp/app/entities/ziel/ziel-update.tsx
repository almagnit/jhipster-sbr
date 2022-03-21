import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './ziel.reducer';
import { IZiel } from 'app/shared/model/ziel.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ZielUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const zielEntity = useAppSelector(state => state.ziel.entity);
  const loading = useAppSelector(state => state.ziel.loading);
  const updating = useAppSelector(state => state.ziel.updating);
  const updateSuccess = useAppSelector(state => state.ziel.updateSuccess);
  const handleClose = () => {
    props.history.push('/ziel');
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
      ...zielEntity,
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
          ...zielEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.ziel.home.createOrEditLabel" data-cy="ZielCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.ziel.home.createOrEditLabel">Create or edit a Ziel</Translate>
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
                  id="ziel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('sbrConverterApp.ziel.code')} id="ziel-code" name="code" data-cy="code" type="text" />
              <ValidatedField label={translate('sbrConverterApp.ziel.front')} id="ziel-front" name="front" data-cy="front" type="text" />
              <ValidatedField
                label={translate('sbrConverterApp.ziel.seite1')}
                id="ziel-seite1"
                name="seite1"
                data-cy="seite1"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.ziel.seite2')}
                id="ziel-seite2"
                name="seite2"
                data-cy="seite2"
                type="text"
              />
              <ValidatedField label={translate('sbrConverterApp.ziel.innen')} id="ziel-innen" name="innen" data-cy="innen" type="text" />
              <ValidatedField label={translate('sbrConverterApp.ziel.tft')} id="ziel-tft" name="tft" data-cy="tft" type="text" />
              <ValidatedField
                label={translate('sbrConverterApp.ziel.terminal')}
                id="ziel-terminal"
                name="terminal"
                data-cy="terminal"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.ziel.language')}
                id="ziel-language"
                name="language"
                data-cy="language"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ziel" replace color="info">
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

export default ZielUpdate;
