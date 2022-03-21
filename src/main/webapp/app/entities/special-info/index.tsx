import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SpecialInfo from './special-info';
import SpecialInfoDetail from './special-info-detail';
import SpecialInfoUpdate from './special-info-update';
import SpecialInfoDeleteDialog from './special-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SpecialInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SpecialInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SpecialInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={SpecialInfo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SpecialInfoDeleteDialog} />
  </>
);

export default Routes;
