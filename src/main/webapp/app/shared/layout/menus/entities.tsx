import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/route">
      <Translate contentKey="global.menu.entities.route" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/fahrten">
      <Translate contentKey="global.menu.entities.fahrten" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/announcement">
      <Translate contentKey="global.menu.entities.announcement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/special-announcement">
      <Translate contentKey="global.menu.entities.specialAnnouncement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/function-announcement">
      <Translate contentKey="global.menu.entities.functionAnnouncement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/function-text">
      <Translate contentKey="global.menu.entities.functionText" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/ort">
      <Translate contentKey="global.menu.entities.ort" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/ziel">
      <Translate contentKey="global.menu.entities.ziel" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/gps">
      <Translate contentKey="global.menu.entities.gps" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/vts">
      <Translate contentKey="global.menu.entities.vts" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sonderziele">
      <Translate contentKey="global.menu.entities.sonderziele" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/special-info">
      <Translate contentKey="global.menu.entities.specialInfo" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/lsa-turnout">
      <Translate contentKey="global.menu.entities.lsaTurnout" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
