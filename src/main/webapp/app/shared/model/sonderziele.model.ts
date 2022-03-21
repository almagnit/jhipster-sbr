export interface ISonderziele {
  id?: number;
  zugnummer?: string | null;
  front?: string | null;
  seite1?: string | null;
  seite2?: string | null;
  innen?: string | null;
  tft?: string | null;
  terminal?: string | null;
}

export const defaultValue: Readonly<ISonderziele> = {};
