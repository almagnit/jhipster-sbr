import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './function-announcement.reducer';
import { IFunctionAnnouncement } from 'app/shared/model/function-announcement.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FunctionAnnouncement = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const functionAnnouncementList = useAppSelector(state => state.functionAnnouncement.entities);
  const loading = useAppSelector(state => state.functionAnnouncement.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="function-announcement-heading" data-cy="FunctionAnnouncementHeading">
        <Translate contentKey="sbrConverterApp.functionAnnouncement.home.title">Function Announcements</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="sbrConverterApp.functionAnnouncement.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sbrConverterApp.functionAnnouncement.home.createLabel">Create new Function Announcement</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {functionAnnouncementList && functionAnnouncementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="sbrConverterApp.functionAnnouncement.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.functionAnnouncement.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.functionAnnouncement.audioFile">Audio File</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.functionAnnouncement.beschreibung">Beschreibung</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.functionAnnouncement.anmerkung">Anmerkung</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.functionAnnouncement.language">Language</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {functionAnnouncementList.map((functionAnnouncement, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${functionAnnouncement.id}`} color="link" size="sm">
                      {functionAnnouncement.id}
                    </Button>
                  </td>
                  <td>{functionAnnouncement.code}</td>
                  <td>{functionAnnouncement.audioFile}</td>
                  <td>{functionAnnouncement.beschreibung}</td>
                  <td>{functionAnnouncement.anmerkung}</td>
                  <td>{functionAnnouncement.language}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${functionAnnouncement.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${functionAnnouncement.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${functionAnnouncement.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="sbrConverterApp.functionAnnouncement.home.notFound">No Function Announcements found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FunctionAnnouncement;
