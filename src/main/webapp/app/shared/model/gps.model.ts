export interface IGPS {
  id?: number;
  name?: string | null;
  gpsCode?: string | null;
  inneresFenster?: string | null;
  auBeresFenster?: string | null;
}

export const defaultValue: Readonly<IGPS> = {};
