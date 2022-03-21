export interface ISpecialAnnouncement {
  id?: number;
  code?: string | null;
  item?: string | null;
  ausgabeOrt?: string | null;
  kurz?: string | null;
  language?: string | null;
  ansagedatei?: string | null;
  klartext?: string | null;
}

export const defaultValue: Readonly<ISpecialAnnouncement> = {};
