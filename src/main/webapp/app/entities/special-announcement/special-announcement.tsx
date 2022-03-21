import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './special-announcement.reducer';
import { ISpecialAnnouncement } from 'app/shared/model/special-announcement.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SpecialAnnouncement = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const specialAnnouncementList = useAppSelector(state => state.specialAnnouncement.entities);
  const loading = useAppSelector(state => state.specialAnnouncement.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="special-announcement-heading" data-cy="SpecialAnnouncementHeading">
        <Translate contentKey="sbrConverterApp.specialAnnouncement.home.title">Special Announcements</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="sbrConverterApp.specialAnnouncement.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sbrConverterApp.specialAnnouncement.home.createLabel">Create new Special Announcement</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {specialAnnouncementList && specialAnnouncementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="sbrConverterApp.specialAnnouncement.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialAnnouncement.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialAnnouncement.item">Item</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialAnnouncement.ausgabeOrt">Ausgabe Ort</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialAnnouncement.kurz">Kurz</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialAnnouncement.language">Language</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialAnnouncement.ansagedatei">Ansagedatei</Translate>
                </th>
                <th>
                  <Translate contentKey="sbrConverterApp.specialAnnouncement.klartext">Klartext</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {specialAnnouncementList.map((specialAnnouncement, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${specialAnnouncement.id}`} color="link" size="sm">
                      {specialAnnouncement.id}
                    </Button>
                  </td>
                  <td>{specialAnnouncement.code}</td>
                  <td>{specialAnnouncement.item}</td>
                  <td>{specialAnnouncement.ausgabeOrt}</td>
                  <td>{specialAnnouncement.kurz}</td>
                  <td>{specialAnnouncement.language}</td>
                  <td>{specialAnnouncement.ansagedatei}</td>
                  <td>{specialAnnouncement.klartext}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${specialAnnouncement.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${specialAnnouncement.id}/edit`}
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
                        to={`${match.url}/${specialAnnouncement.id}/delete`}
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
              <Translate contentKey="sbrConverterApp.specialAnnouncement.home.notFound">No Special Announcements found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SpecialAnnouncement;
