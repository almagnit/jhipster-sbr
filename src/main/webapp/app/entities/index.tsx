import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Route from './route';
import Fahrten from './fahrten';
import Announcement from './announcement';
import SpecialAnnouncement from './special-announcement';
import FunctionAnnouncement from './function-announcement';
import FunctionText from './function-text';
import Ort from './ort';
import Ziel from './ziel';
import GPS from './gps';
import VTS from './vts';
import Sonderziele from './sonderziele';
import SpecialInfo from './special-info';
import LSATurnout from './lsa-turnout';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}route`} component={Route} />
      <ErrorBoundaryRoute path={`${match.url}fahrten`} component={Fahrten} />
      <ErrorBoundaryRoute path={`${match.url}announcement`} component={Announcement} />
      <ErrorBoundaryRoute path={`${match.url}special-announcement`} component={SpecialAnnouncement} />
      <ErrorBoundaryRoute path={`${match.url}function-announcement`} component={FunctionAnnouncement} />
      <ErrorBoundaryRoute path={`${match.url}function-text`} component={FunctionText} />
      <ErrorBoundaryRoute path={`${match.url}ort`} component={Ort} />
      <ErrorBoundaryRoute path={`${match.url}ziel`} component={Ziel} />
      <ErrorBoundaryRoute path={`${match.url}gps`} component={GPS} />
      <ErrorBoundaryRoute path={`${match.url}vts`} component={VTS} />
      <ErrorBoundaryRoute path={`${match.url}sonderziele`} component={Sonderziele} />
      <ErrorBoundaryRoute path={`${match.url}special-info`} component={SpecialInfo} />
      <ErrorBoundaryRoute path={`${match.url}lsa-turnout`} component={LSATurnout} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
