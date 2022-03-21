import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Ziel from './ziel';
import ZielDetail from './ziel-detail';
import ZielUpdate from './ziel-update';
import ZielDeleteDialog from './ziel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ZielUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ZielUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ZielDetail} />
      <ErrorBoundaryRoute path={match.url} component={Ziel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ZielDeleteDialog} />
  </>
);

export default Routes;
