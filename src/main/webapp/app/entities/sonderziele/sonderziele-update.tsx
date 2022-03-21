import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './sonderziele.reducer';
import { ISonderziele } from 'app/shared/model/sonderziele.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SonderzieleUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const sonderzieleEntity = useAppSelector(state => state.sonderziele.entity);
  const loading = useAppSelector(state => state.sonderziele.loading);
  const updating = useAppSelector(state => state.sonderziele.updating);
  const updateSuccess = useAppSelector(state => state.sonderziele.updateSuccess);
  const handleClose = () => {
    props.history.push('/sonderziele');
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
      ...sonderzieleEntity,
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
          ...sonderzieleEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.sonderziele.home.createOrEditLabel" data-cy="SonderzieleCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.sonderziele.home.createOrEditLabel">Create or edit a Sonderziele</Translate>
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
                  id="sonderziele-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('sbrConverterApp.sonderziele.zugnummer')}
                id="sonderziele-zugnummer"
                name="zugnummer"
                data-cy="zugnummer"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.sonderziele.front')}
                id="sonderziele-front"
                name="front"
                data-cy="front"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.sonderziele.seite1')}
                id="sonderziele-seite1"
                name="seite1"
                data-cy="seite1"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.sonderziele.seite2')}
                id="sonderziele-seite2"
                name="seite2"
                data-cy="seite2"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.sonderziele.innen')}
                id="sonderziele-innen"
                name="innen"
                data-cy="innen"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.sonderziele.tft')}
                id="sonderziele-tft"
                name="tft"
                data-cy="tft"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.sonderziele.terminal')}
                id="sonderziele-terminal"
                name="terminal"
                data-cy="terminal"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sonderziele" replace color="info">
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

export default SonderzieleUpdate;
