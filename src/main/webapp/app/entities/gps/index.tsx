import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GPS from './gps';
import GPSDetail from './gps-detail';
import GPSUpdate from './gps-update';
import GPSDeleteDialog from './gps-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GPSUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GPSUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GPSDetail} />
      <ErrorBoundaryRoute path={match.url} component={GPS} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GPSDeleteDialog} />
  </>
);

export default Routes;
