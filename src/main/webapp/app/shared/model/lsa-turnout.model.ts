export interface ILSATurnout {
  id?: number;
  station1?: string | null;
  station2?: string | null;
  lsaCode?: string | null;
  turnout?: string | null;
}

export const defaultValue: Readonly<ILSATurnout> = {};
