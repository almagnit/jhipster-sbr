import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Fahrten from './fahrten';
import FahrtenDetail from './fahrten-detail';
import FahrtenUpdate from './fahrten-update';
import FahrtenDeleteDialog from './fahrten-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FahrtenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FahrtenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FahrtenDetail} />
      <ErrorBoundaryRoute path={match.url} component={Fahrten} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FahrtenDeleteDialog} />
  </>
);

export default Routes;
