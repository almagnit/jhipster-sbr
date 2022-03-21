import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VTS from './vts';
import VTSDetail from './vts-detail';
import VTSUpdate from './vts-update';
import VTSDeleteDialog from './vts-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VTSUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VTSUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VTSDetail} />
      <ErrorBoundaryRoute path={match.url} component={VTS} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VTSDeleteDialog} />
  </>
);

export default Routes;
