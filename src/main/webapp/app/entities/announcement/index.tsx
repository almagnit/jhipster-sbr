import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Announcement from './announcement';
import AnnouncementDetail from './announcement-detail';
import AnnouncementUpdate from './announcement-update';
import AnnouncementDeleteDialog from './announcement-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AnnouncementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AnnouncementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AnnouncementDetail} />
      <ErrorBoundaryRoute path={match.url} component={Announcement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AnnouncementDeleteDialog} />
  </>
);

export default Routes;
