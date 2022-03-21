import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Ort from './ort';
import OrtDetail from './ort-detail';
import OrtUpdate from './ort-update';
import OrtDeleteDialog from './ort-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrtUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrtUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrtDetail} />
      <ErrorBoundaryRoute path={match.url} component={Ort} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OrtDeleteDialog} />
  </>
);

export default Routes;
