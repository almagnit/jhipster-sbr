import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LSATurnout from './lsa-turnout';
import LSATurnoutDetail from './lsa-turnout-detail';
import LSATurnoutUpdate from './lsa-turnout-update';
import LSATurnoutDeleteDialog from './lsa-turnout-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LSATurnoutUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LSATurnoutUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LSATurnoutDetail} />
      <ErrorBoundaryRoute path={match.url} component={LSATurnout} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LSATurnoutDeleteDialog} />
  </>
);

export default Routes;
