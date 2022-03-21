export interface IAnnouncement {
  id?: number;
  code?: string | null;
  ansage?: string | null;
  item?: string | null;
  announcementfile?: string | null;
  klartext?: string | null;
  language?: string | null;
}

export const defaultValue: Readonly<IAnnouncement> = {};
