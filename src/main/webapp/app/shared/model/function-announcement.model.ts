export interface IFunctionAnnouncement {
  id?: number;
  code?: string | null;
  audioFile?: string | null;
  beschreibung?: string | null;
  anmerkung?: string | null;
  language?: string | null;
}

export const defaultValue: Readonly<IFunctionAnnouncement> = {};
