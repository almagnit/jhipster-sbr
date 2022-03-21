import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FunctionText from './function-text';
import FunctionTextDetail from './function-text-detail';
import FunctionTextUpdate from './function-text-update';
import FunctionTextDeleteDialog from './function-text-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FunctionTextUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FunctionTextUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FunctionTextDetail} />
      <ErrorBoundaryRoute path={match.url} component={FunctionText} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FunctionTextDeleteDialog} />
  </>
);

export default Routes;
