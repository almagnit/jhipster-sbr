import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './route.reducer';
import { IRoute } from 'app/shared/model/route.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RouteUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const routeEntity = useAppSelector(state => state.route.entity);
  const loading = useAppSelector(state => state.route.loading);
  const updating = useAppSelector(state => state.route.updating);
  const updateSuccess = useAppSelector(state => state.route.updateSuccess);
  const handleClose = () => {
    props.history.push('/route');
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
      ...routeEntity,
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
          ...routeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sbrConverterApp.route.home.createOrEditLabel" data-cy="RouteCreateUpdateHeading">
            <Translate contentKey="sbrConverterApp.route.home.createOrEditLabel">Create or edit a Route</Translate>
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
                  id="route-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('sbrConverterApp.route.ort')} id="route-ort" name="ort" data-cy="ort" type="text" />
              <ValidatedField
                label={translate('sbrConverterApp.route.entfernung')}
                id="route-entfernung"
                name="entfernung"
                data-cy="entfernung"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.haltegrund')}
                id="route-haltegrund"
                name="haltegrund"
                data-cy="haltegrund"
                type="text"
              />
              <ValidatedField label={translate('sbrConverterApp.route.gleis')} id="route-gleis" name="gleis" data-cy="gleis" type="text" />
              <ValidatedField
                label={translate('sbrConverterApp.route.ausstieg')}
                id="route-ausstieg"
                name="ausstieg"
                data-cy="ausstieg"
                type="text"
              />
              <ValidatedField label={translate('sbrConverterApp.route.gps')} id="route-gps" name="gps" data-cy="gps" type="text" />
              <ValidatedField
                label={translate('sbrConverterApp.route.ansage')}
                id="route-ansage"
                name="ansage"
                data-cy="ansage"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.endAnsage')}
                id="route-endAnsage"
                name="endAnsage"
                data-cy="endAnsage"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.startAnsage')}
                id="route-startAnsage"
                name="startAnsage"
                data-cy="startAnsage"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.viaAnsage')}
                id="route-viaAnsage"
                name="viaAnsage"
                data-cy="viaAnsage"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.stopAnsage')}
                id="route-stopAnsage"
                name="stopAnsage"
                data-cy="stopAnsage"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.stopAnsageMode')}
                id="route-stopAnsageMode"
                name="stopAnsageMode"
                data-cy="stopAnsageMode"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.zugziel')}
                id="route-zugziel"
                name="zugziel"
                data-cy="zugziel"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.entwerter1')}
                id="route-entwerter1"
                name="entwerter1"
                data-cy="entwerter1"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.entwerter2')}
                id="route-entwerter2"
                name="entwerter2"
                data-cy="entwerter2"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.zoneninfo')}
                id="route-zoneninfo"
                name="zoneninfo"
                data-cy="zoneninfo"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.automat10')}
                id="route-automat10"
                name="automat10"
                data-cy="automat10"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.attribute')}
                id="route-attribute"
                name="attribute"
                data-cy="attribute"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.sprache1')}
                id="route-sprache1"
                name="sprache1"
                data-cy="sprache1"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.sprache2')}
                id="route-sprache2"
                name="sprache2"
                data-cy="sprache2"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.sprache3')}
                id="route-sprache3"
                name="sprache3"
                data-cy="sprache3"
                type="text"
              />
              <ValidatedField label={translate('sbrConverterApp.route.kurs')} id="route-kurs" name="kurs" data-cy="kurs" type="text" />
              <ValidatedField
                label={translate('sbrConverterApp.route.zieltexte')}
                id="route-zieltexte"
                name="zieltexte"
                data-cy="zieltexte"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.liniennummer')}
                id="route-liniennummer"
                name="liniennummer"
                data-cy="liniennummer"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.gattung')}
                id="route-gattung"
                name="gattung"
                data-cy="gattung"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.lineMarker')}
                id="route-lineMarker"
                name="lineMarker"
                data-cy="lineMarker"
                type="text"
              />
              <ValidatedField label={translate('sbrConverterApp.route.ds010')} id="route-ds010" name="ds010" data-cy="ds010" type="text" />
              <ValidatedField
                label={translate('sbrConverterApp.route.linienansage')}
                id="route-linienansage"
                name="linienansage"
                data-cy="linienansage"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.spurkranz')}
                id="route-spurkranz"
                name="spurkranz"
                data-cy="spurkranz"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.skDauer')}
                id="route-skDauer"
                name="skDauer"
                data-cy="skDauer"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.skBoogie')}
                id="route-skBoogie"
                name="skBoogie"
                data-cy="skBoogie"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.prmTur')}
                id="route-prmTur"
                name="prmTur"
                data-cy="prmTur"
                type="text"
              />
              <ValidatedField
                label={translate('sbrConverterApp.route.tursperrung')}
                id="route-tursperrung"
                name="tursperrung"
                data-cy="tursperrung"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/route" replace color="info">
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

export default RouteUpdate;
