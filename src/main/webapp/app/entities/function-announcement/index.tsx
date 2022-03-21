import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FunctionAnnouncement from './function-announcement';
import FunctionAnnouncementDetail from './function-announcement-detail';
import FunctionAnnouncementUpdate from './function-announcement-update';
import FunctionAnnouncementDeleteDialog from './function-announcement-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FunctionAnnouncementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FunctionAnnouncementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FunctionAnnouncementDetail} />
      <ErrorBoundaryRoute path={match.url} component={FunctionAnnouncement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FunctionAnnouncementDeleteDialog} />
  </>
);

export default Routes;
