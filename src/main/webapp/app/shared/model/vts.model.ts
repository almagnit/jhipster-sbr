export interface IVTS {
  id?: number;
  verkehrstage?: string | null;
  beschreibung?: string | null;
  versionsnummer?: string | null;
  mandant?: string | null;
  feiertage?: string | null;
  gueltigkeit?: string | null;
}

export const defaultValue: Readonly<IVTS> = {};
