import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './special-info.reducer';
import { ISpecialInfo } from 'app/shared/model/special-info.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SpecialInfo = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const specialInfoList = useAppSelector(state => state.specialInfo.entities);
  const loading = useAppSelector(state => state.specialInfo.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="special-info-heading" data-cy="SpecialInfoHeading">
        <Translate contentKey="sbrConverterApp.specialInfo.home.title">Special Infos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="sbrConverterApp.specialInfo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sbrConverterApp.specialInfo.home.createLabel">Create new Special Info</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {specialInfoList && specialInfoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="sbrConverterApp.specialInfo.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialInfo.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialInfo.item">Item</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialInfo.attribute">Attribute</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialInfo.shortTerminalDesc">Short Terminal Desc</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialInfo.longTerminalDesc">Long Terminal Desc</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialInfo.displayText">Display Text</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialInfo.ds003">Ds 003</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialInfo.language">Language</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {specialInfoList.map((specialInfo, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${specialInfo.id}`} color="link" size="sm">
                      {specialInfo.id}
                    </Button>
                  </td>
                  <td>{specialInfo.code}</td>
                  <td>{specialInfo.item}</td>
                  <td>{specialInfo.attribute}</td>
                  <td>{specialInfo.shortTerminalDesc}</td>
                  <td>{specialInfo.longTerminalDesc}</td>
                  <td>{specialInfo.displayText}</td>
                  <td>{specialInfo.ds003}</td>
                  <td>{specialInfo.language}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${specialInfo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${specialInfo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${specialInfo.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="sbrConverterApp.specialInfo.home.notFound">No Special Infos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SpecialInfo;
