import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Sonderziele from './sonderziele';
import SonderzieleDetail from './sonderziele-detail';
import SonderzieleUpdate from './sonderziele-update';
import SonderzieleDeleteDialog from './sonderziele-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SonderzieleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SonderzieleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SonderzieleDetail} />
      <ErrorBoundaryRoute path={match.url} component={Sonderziele} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SonderzieleDeleteDialog} />
  </>
);

export default Routes;
