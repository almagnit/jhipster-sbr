export interface IFahrten {
  id?: number;
  titel?: string | null;
  zugnummer?: string | null;
  znrBeschreibung?: string | null;
  variante?: string | null;
  tagesart?: string | null;
  umlauf?: string | null;
  umlaufindex?: string | null;
}

export const defaultValue: Readonly<IFahrten> = {};
