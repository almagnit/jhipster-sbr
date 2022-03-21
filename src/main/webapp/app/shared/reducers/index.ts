import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import route from 'app/entities/route/route.reducer';
// prettier-ignore
import fahrten from 'app/entities/fahrten/fahrten.reducer';
// prettier-ignore
import announcement from 'app/entities/announcement/announcement.reducer';
// prettier-ignore
import specialAnnouncement from 'app/entities/special-announcement/special-announcement.reducer';
// prettier-ignore
import functionAnnouncement from 'app/entities/function-announcement/function-announcement.reducer';
// prettier-ignore
import functionText from 'app/entities/function-text/function-text.reducer';
// prettier-ignore
import ort from 'app/entities/ort/ort.reducer';
// prettier-ignore
import ziel from 'app/entities/ziel/ziel.reducer';
// prettier-ignore
import gPS from 'app/entities/gps/gps.reducer';
// prettier-ignore
import vTS from 'app/entities/vts/vts.reducer';
// prettier-ignore
import sonderziele from 'app/entities/sonderziele/sonderziele.reducer';
// prettier-ignore
import specialInfo from 'app/entities/special-info/special-info.reducer';
// prettier-ignore
import lSATurnout from 'app/entities/lsa-turnout/lsa-turnout.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  route,
  fahrten,
  announcement,
  specialAnnouncement,
  functionAnnouncement,
  functionText,
  ort,
  ziel,
  gPS,
  vTS,
  sonderziele,
  specialInfo,
  lSATurnout,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
