export interface IOrt {
  id?: number;
  ibnr?: string | null;
  ds100?: string | null;
  front?: string | null;
  seite?: string | null;
  innen?: string | null;
  tft?: string | null;
  terminal?: string | null;
  ds001c?: string | null;
  video?: string | null;
  ds009?: string | null;
  ds003?: string | null;
  language?: string | null;
}

export const defaultValue: Readonly<IOrt> = {};
