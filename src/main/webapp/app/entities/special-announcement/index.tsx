import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SpecialAnnouncement from './special-announcement';
import SpecialAnnouncementDetail from './special-announcement-detail';
import SpecialAnnouncementUpdate from './special-announcement-update';
import SpecialAnnouncementDeleteDialog from './special-announcement-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SpecialAnnouncementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SpecialAnnouncementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SpecialAnnouncementDetail} />
      <ErrorBoundaryRoute path={match.url} component={SpecialAnnouncement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SpecialAnnouncementDeleteDialog} />
  </>
);

export default Routes;
